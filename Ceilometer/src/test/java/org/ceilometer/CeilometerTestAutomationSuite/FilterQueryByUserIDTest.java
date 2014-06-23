package org.ceilometer.CeilometerTestAutomationSuite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import junit.framework.Assert;

import org.ceilometer.CeilometerTestAutomationSuite.MeterListFilterVerification.ValueNotFoundException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

public class FilterQueryByUserIDTest {
	static TestDataDriver tdd;
	MeterListFilterVerification mlv; 
	MeterListVerification mlver; 
	MeterList ml;
	String authToken;
	List<String> tokensList;
	MeterListFilterQuery mlq;
	public List<String> meterValues;
	public List<String> userIdValues;
  
  @BeforeClass
  public void beforeClass() throws IOException, ValueNotFoundException, ParseException {
	  tdd=new TestDataDriver();
	  mlv=new MeterListFilterVerification();
	  ml=new MeterList();
	  mlq=new MeterListFilterQuery();
	  tokensList=new ArrayList<String>();
	  mlver=new MeterListVerification();
	  
	  List<String[]>lines=tdd.getTestData();
	  for(String[] sa:lines){
	  authToken=MeterList.postToken(sa[0], sa[1], sa[2]);
	  tokensList.add(authToken);
	  }
	  
  }

  @AfterClass
  public void afterClass() {
  }
  @Test
  public void userIdFilterQueryResponseAssertionTest() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  //System.out.println(meterValues);
		  
		  List<String> userIdValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "user_id");
		  //System.out.println(userIdValues);
		  
		  int user_id_counter=0;
		  for(String meter:meterValues){
			  String query=mlq.genQuery("user_id", "eq", userIdValues.get(user_id_counter++));
			  //System.out.println("Query: "+query);
			  String finalResourcePath="/v2/meters/"+meter+query;
			  //System.out.println(finalResourcePath);
			  //System.out.println(ml.getMetersResponseList(at, finalResourcePath));
			  int num=ml.getMetersStatus(at, finalResourcePath);
			  Assert.assertEquals(num,200);
			  
			  
		  }
		  
		 
	  }
	  
	System.out.println("Success: userIdFilterQueryResponseAssertionTest");  
  }
  @Test(dependsOnMethods={"userIdFilterQueryResponseAssertionTest"})
  public void userIdFilterQueryResponseAllJSONfieldsExist() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  
		  List<String> userIdValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "user_id");
		  
		  int user_id_counter=0;
		  for(String meter:meterValues){
			  String query=mlq.genQuery("user_id", "eq", userIdValues.get(user_id_counter++));
			  
			  String finalResourcePath="/v2/meters/"+meter+query;
			  //System.out.println("*****???"+finalResourcePath);
			  Assert.assertTrue(mlver.allJSONfieldsExist(at, finalResourcePath));
			  
			  
		  }
		  
		 
	  }
	  System.out.println("Success: userIdFilterQueryResponseAllJSONfieldsExist");  
  }
  
  @Test(dependsOnMethods={"userIdFilterQueryResponseAssertionTest"})
  public void userIdFilterQueryVerifyuserIdValue() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  
		  List<String> userIdValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "user_id");
		  
		  int user_id_counter=0;
		  for(String meter:meterValues){
			  String user_id_val=userIdValues.get(user_id_counter);
			  String query=mlq.genQuery("user_id", "eq", userIdValues.get(user_id_counter));
			  
			  String finalResourcePath="/v2/meters/"+meter+query;
			  //System.out.println(ml.getMetersResponseList(at, finalResourcePath));
			  List<String> user_id_vals=ml.getJSONFieldValues(at,finalResourcePath,"user_id");
			  //System.out.println(user_id_vals);
			  Assert.assertTrue(user_id_vals.contains(userIdValues.get(user_id_counter)));
			  user_id_counter++;
			  
			  
			  
		  }
		  
		 
	  }
	  
	  
	  System.out.println("Success: userIdFilterQueryVerifyuserIdValue");    
	  
  }
  
  
  

}

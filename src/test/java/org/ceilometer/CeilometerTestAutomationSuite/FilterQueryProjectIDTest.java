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

public class FilterQueryProjectIDTest {
	static TestDataDriver tdd;
	MeterListFilterVerification mlv; 
	MeterListVerification mlver; 
	MeterList ml;
	String authToken;
	List<String> tokensList;
	MeterListFilterQuery mlq;
	public List<String> meterValues;
	public List<String> projectIdValues;
  
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
  @Test(dependsOnGroups={"v2-meters-basic"})
  public void projectIdFilterQueryTestResponseAssertionTest() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  
		  List<String> projectIdValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "project_id");
		  
		  int proj_id_counter=0;
		  for(String meter:meterValues){
			  String query=mlq.genQuery("metadata.project_id", "eq", projectIdValues.get(proj_id_counter++));
			  
			  String finalResourcePath="/v2/meters/"+meter+query;
			  //System.out.println(ml.getMetersResponseList(at, finalResourcePath));
			  int num=ml.getMetersStatus(at, finalResourcePath);
			  Assert.assertEquals(num,200);
			  
			  
		  }
		  
		 
	  }
	  
	System.out.println("Success: proejctIdFilterQueryResponseAllJSONfieldsExist");  
  }
  @Test(dependsOnMethods={"projectIdFilterQueryTestResponseAssertionTest"})
  public void projectIdFilterQueryResponseAllJSONfieldsExist() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  
		  List<String> projectIdValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "project_id");
		  
		  int proj_id_counter=0;
		  for(String meter:meterValues){
			  String query=mlq.genQuery("metadata.project_id", "eq", projectIdValues.get(proj_id_counter++));
			  
			  String finalResourcePath="/v2/meters/"+meter+query;
			 // System.out.println("*****"+finalResourcePath);
			  Assert.assertTrue(mlver.allJSONfieldsExist(at, finalResourcePath));
			  
			  
		  }
		  
		 
	  }
	  System.out.println("Success: projectIdFilterQueryTestResponseAssertionTest");  
  }
  
  @Test(dependsOnMethods={"projectIdFilterQueryTestResponseAssertionTest"})
  public void projectIdFilterQueryVerifyProjectIdValue() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  
		  List<String> projectIdValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "project_id");
		  
		  int proj_id_counter=0;
		  for(String meter:meterValues){
			  String project_id_val=projectIdValues.get(proj_id_counter);
			  String query=mlq.genQuery("metadata.project_id", "eq", projectIdValues.get(proj_id_counter));
			  
			  String finalResourcePath="/v2/meters/"+meter+query;
			  //System.out.println(ml.getMetersResponseList(at, finalResourcePath));
			  List<String> project_id_vals=ml.getJSONFieldValues(at,finalResourcePath,"project_id");
			  //System.out.println(project_id_vals);
			  Assert.assertTrue(project_id_vals.contains(projectIdValues.get(proj_id_counter)));
			  proj_id_counter++;
			  
			  
			  
		  }
		  
		 
	  }
	  
	  
	  System.out.println("Success: projectIdFilterQueryVerifyProjectIdValue");    
	  
  }
  

}

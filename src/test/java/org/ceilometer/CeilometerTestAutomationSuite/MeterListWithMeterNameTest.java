package org.ceilometer.CeilometerTestAutomationSuite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.ceilometer.CeilometerTestAutomationSuite.MeterListFilterVerification.ValueNotFoundException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MeterListWithMeterNameTest {
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
  @Test(dependsOnGroups={"v2-meters-basic"})
  public void meterListWithMeterNameResponseAssertionTest() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  ////System.out.println(meterValues)
		  for(String meter:meterValues){
			  
			  String finalResourcePath="/v2/meters/"+meter;
			 
			  int num=ml.getMetersStatus(at, finalResourcePath);
			  Assert.assertEquals(num,200);
			  
			  
		  }
		  
		 
	  }
	  
	//System.out.println("Success: meterListWithMeterNameResponseAssertionTest");  
  }
  @Test(dependsOnMethods={"meterListWithMeterNameResponseAssertionTest"})
  public void meterListWithMeterNameAllJSONFieldsExistTest() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  ////System.out.println(meterValues)
		  for(String meter:meterValues){
			  
			  String finalResourcePath="/v2/meters/"+meter;
			 
			  Assert.assertTrue(mlver.allJSONfieldsExist(at, finalResourcePath));
			  
			  
			  
		  }
		  
		 
	  }
	  
	//System.out.println("Success: meterListWithMeterNameallFiledsExistTest");  
  }
  @Test(dependsOnMethods={"meterListWithMeterNameResponseAssertionTest"})
  public void meterListWithMeterNameVerifyMeterNameTest() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  
		  for(String meter:meterValues){
			  ////System.out.println(meter);
			  
			  String finalResourcePath="/v2/meters/"+meter;
			  //System.out.println(ml.getMetersResponseList(at, finalResourcePath));
			  List<String> metersList=ml.getJSONFieldValues(at, finalResourcePath, "counter_name");
			  ////System.out.println(metersList);
			  Assert.assertTrue(metersList.contains(meter));
			  
			 
			  
			  
			  
		  }
		  
		 
	  }
	  
	//System.out.println("Success: meterListWithMeterNameVerifyMeterNameTest");  
  }

	
}

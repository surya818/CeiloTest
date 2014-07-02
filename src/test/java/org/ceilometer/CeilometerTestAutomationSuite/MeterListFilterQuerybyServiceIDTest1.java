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

public class MeterListFilterQuerybyServiceIDTest1 {
	static TestDataDriver tdd;
	MeterListFilterVerification mlv; 
	MeterListVerification mlver; 
	MeterList ml;
	MeterListFilterQuerybyServiceID mlfs;
	String authToken;
	List<String> tokensList;
	MeterListFilterQuery mlq;
	public List<String> meterValues;
	public List<String> serviceIdValues;
  
  @BeforeClass
  public void beforeClass() throws IOException, ValueNotFoundException, ParseException {
	  tdd=new TestDataDriver();
	  mlv=new MeterListFilterVerification();
	  ml=new MeterList();
	  mlq=new MeterListFilterQuery();
	  tokensList=new ArrayList<String>();
	  mlver=new MeterListVerification();
	  mlfs=new MeterListFilterQuerybyServiceID();
	  
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
  public void serviceIdFilterQueryResponseAssertionTest() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  
		  
		  
		  MeterListFilterQuerybyServiceID mlfs=new MeterListFilterQuerybyServiceID();
		  List<String> outer_service=mlfs.getServicesList(at, "/v2/meters/");
		  
		  
		  int service_id_counter=0;
		  for(String meter:meterValues){
			  String query=mlq.genQuery("metadata.service", "eq", outer_service.get(service_id_counter++));
			  
			  String finalResourcePath="/v2/meters/"+meter+query;
			  
			  int num=ml.getMetersStatus(at, finalResourcePath);
			  Assert.assertEquals(num,200);
			  
			  
		  }
		  
		 
	  }
	  
	//System.out.println("Success: serviceIdFilterQueryResponseAssertionTest");  
  }
  
  @Test(dependsOnGroups={"v2-meters-basic"})
  public void serviceIdFilterQueryResponseAllJSONfieldsExist() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		 // //System.out.println(meterValues.size());
		  
		  
		  
		  
		  List<String> outer_service=mlfs.getServicesList(at, "/v2/meters/");
		  
		  
		  int service_id_counter=0;
		  for(String meter:meterValues){
			  String query=mlq.genQuery("metadata.service", "eq", outer_service.get(service_id_counter++));
			
			  String finalResourcePath="/v2/meters/"+meter+query;
			  
			  Assert.assertTrue(mlver.allJSONfieldsExist(at, finalResourcePath));
			  
			  
		  }
		  
		 
	  }
	  
	//System.out.println("Success: serviceIdFilterQueryResponseAllJSONfieldsExist");  
  }
  
  @Test(dependsOnGroups={"v2-meters-basic"})
  public void serviceIdFilterQueryResponseVerifyserviceIdValue() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		 // //System.out.println(meterValues.size());
		  
		  
		  
		  MeterListFilterQuerybyServiceID mlfs=new MeterListFilterQuerybyServiceID();
		  List<String> outer_service=mlfs.getServicesList(at, "/v2/meters/");
		  
		  
		  int service_id_counter=0;
		  for(String meter:meterValues){
			  String query=mlq.genQuery("metadata.service", "eq", outer_service.get(service_id_counter));
			
			  String finalResourcePath="/v2/meters/"+meter+query;
			  List<String> service_id_vals=mlfs.getServicesList(at, finalResourcePath);
			  //System.out.println(service_id_vals);
			  Assert.assertTrue(service_id_vals.contains(outer_service.get(service_id_counter++)));
			  
			  
		  }
		  
		 
	  }
	  
	//System.out.println("Success: serviceIdFilterQueryResponseAllJSONfieldsExist");  
  }
  
  /*
  @Test
  public void serviceIdFilterQueryResponseAllJSONfieldsExist() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  
		  List<String> serviceIdValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "service_id");
		  
		  int service_id_counter=0;
		  for(String meter:meterValues){
			  String query=mlq.genQuery("service_id", "eq", serviceIdValues.get(service_id_counter++));
			  
			  String finalResourcePath="/v2/meters/"+meter+query;
			  ////System.out.println("*****???"+finalResourcePath);
			  Assert.assertTrue(mlver.allJSONfieldsExist(at, finalResourcePath));
			  
			  
		  }
		  
		 
	  }
	  //System.out.println("Success: serviceIdFilterQueryResponseAllJSONfieldsExist");  
  }
  
  @Test
  public void serviceIdFilterQueryVerifyserviceIdValue() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
	  
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  
		  List<String> serviceIdValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "service_id");
		  
		  int service_id_counter=0;
		  for(String meter:meterValues){
			  String service_id_val=serviceIdValues.get(service_id_counter);
			  String query=mlq.genQuery("service_id", "eq", serviceIdValues.get(service_id_counter));
			  
			  String finalResourcePath="/v2/meters/"+meter+query;
			  ////System.out.println(ml.getMetersResponseList(at, finalResourcePath));
			  List<String> service_id_vals=ml.getJSONFieldValues(at,finalResourcePath,"service_id");
			  ////System.out.println(service_id_vals);
			  Assert.assertTrue(service_id_vals.contains(serviceIdValues.get(service_id_counter)));
			  service_id_counter++;
			  
			  
			  
		  }
		  
		 
	  }
	  
	  
	  //System.out.println("Success: serviceIdFilterQueryVerifyserviceIdValue");    
	  
  }
  
  
  
*/
}
 
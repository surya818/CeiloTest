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

public class MeterListFilterQueryByTimestamp_LE_Test {
	static TestDataDriver tdd;
	MeterListFilterVerification mlv; 
	MeterListVerification mlver; 
	MeterList ml;
	TimeStampGen tsg;
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
	  tsg=new TimeStampGen();
	  
	  List<String[]>lines=tdd.getTestData();
	  for(String[] sa:lines){
	  authToken=MeterList.postToken(sa[0], sa[1], sa[2]);
	  tokensList.add(authToken);
	  }
	  
  }

  @AfterClass
  public void afterClass() throws Throwable {
	  super.finalize();
  }
  @Test(dependsOnGroups={"v2-meters-basic"})
  public void timeStampFilterLessThanOrEqualToQueryResponseAssertion() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException, java.text.ParseException{
	  List<String> timeStampValues=null;
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  for(String tmp:meterValues){
		 
		  timeStampValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"+tmp), "timestamp");
		  }
		  
		  int timeStamp_counter=0;
		  for(String meter:meterValues){
			  String curr_date=timeStampValues.get(timeStamp_counter++);
			  String new_date=tsg.changeDateByDays(curr_date, 1);
			  String query=mlq.genQuery("timestamp", "le", new_date);
			  
			  String finalResourcePath="/v2/meters/"+meter+query;
			  int num=ml.getMetersStatus(at, finalResourcePath);
			  Assert.assertEquals(num,200);
			  
			  
		  }
		  
		 
	  }
	  
	//System.out.println("Success: timeStampFilterLessThanOrEqualToQueryResponseAssertion");  
  }
  @Test(dependsOnGroups={"v2-meters-basic"})
  public void timeStampFilterLessThanOrEqualToQueryResponseAllJSONfieldsExist() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException, java.text.ParseException{
	  
	  List<String> timeStampValues=null;
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  for(String tmp:meterValues){
		 
		  timeStampValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"+tmp), "timestamp");
		  }
		  
		  int timeStamp_counter=0;
		  for(String meter:meterValues){
			  String curr_date=timeStampValues.get(timeStamp_counter++);
			  String new_date=tsg.changeDateByDays(curr_date,1);
			  ////System.out.println("<<<<<<<<>>>>> "+new_date );
			  String query=mlq.genQuery("timestamp", "le", new_date);
			  
			  String finalResourcePath="/v2/meters/"+meter+query;
			 // //System.out.println(ml.getMetersResponseList(at, finalResourcePath));
			  int num=ml.getMetersStatus(at, finalResourcePath);
			  Assert.assertTrue(mlver.allJSONfieldsExist(at, finalResourcePath));
			  
			  
		  }
		  
		  
		 
	  }
	  //System.out.println("Success: timeStampFilterLessThanOrEqualToQueryResponseAllJSONfieldsExist");  
  }
  
 /* @Test
  public void timeStampFilterLessThanOrEqualToQueryResponseVerifyTimeStampValue() throws FileNotFoundException, ValueNotFoundException, IOException, ParseException, java.text.ParseException{
	  
	  List<String> timeStampValues=null;
	  for(String at:tokensList){
		  List<String> meterValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"), "name");
		  for(String tmp:meterValues){
		 
		  timeStampValues=mlv.getListOfFields(ml.getMetersResponseList(at, "/v2/meters/"+tmp), "timestamp");
		  }
		  
		  int timeStamp_counter=0;
		  for(String meter:meterValues){
			  String curr_date=timeStampValues.get(timeStamp_counter++);
			  String new_date=tsg.changeDateByDays(curr_date, 1);
			  String query=mlq.genQuery("timestamp", "le", new_date);
			  
			  String finalResourcePath="/v2/meters/"+meter+query;
			  
			  List<String> timestamp_vals=ml.getJSONFieldValues(at,finalResourcePath,"timestamp");
			  ////System.out.println(timestamp_vals);
			  Assert.assertTrue(timestamp_vals.contains(timeStampValues.get(timeStamp_counter)));
			  timeStamp_counter++;
			  
			  
			  
		  }
		  
		
	  }
	  
	  
	  //System.out.println("Success: timeStampFilterLessThanOrEqualToQueryResponseVerifyTimeStampValue");    
	  
  }
  /*/
  
}

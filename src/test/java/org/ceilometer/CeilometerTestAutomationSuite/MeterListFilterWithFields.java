package org.ceilometer.CeilometerTestAutomationSuite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import org.ceilometer.CeilometerTestAutomationSuite.*;

public class MeterListFilterWithFields {
	static TestDataDriver tdd;
	MeterListVerification mlv; 
	MeterList ml;
	String authToken;
	List<String> tokensList;
	//String[]jsonFields=["counter_name","resource_id","resource_metadata"]
	
	
	
  @Test(dependsOnGroups={"v2-meters-basic"})
  public void meterListResponseJSONObjectsExist() throws IOException, ParseException {
	  for(String authToken:tokensList){
	  int size=mlv.meterListSize(authToken,"/v2/meters/");
	  
	  Assert.assertTrue(size>0);
	  }
	  
	  
	  
  }
  @Test(dependsOnGroups={"v2-meters-basic"})
  public void validateMeterListResponseJSONFields() throws FileNotFoundException, IOException, ParseException{
	  for(String authToken:tokensList){
		  
	  List<Boolean> list=mlv.verifyTenant(authToken,"/v2/meters/");
	  //System.out.println(list);
	  for(boolean boo:list){
		  Assert.assertTrue(boo);
	  }
	  }
  }
  @BeforeClass
  public void beforeClass() throws IOException {
	  tdd=new TestDataDriver();
	  mlv=new MeterListVerification();
	  ml=new MeterList();
	  tokensList=new ArrayList<String>();
	  //List<String> meter_id_name=ml.
	  List<String[]>lines=tdd.getTestData();
	  for(String[] sa:lines){
	  authToken=ml.postToken(sa[0], sa[1], sa[2]);
	  tokensList.add(authToken);
	  }
  }
  
  @Test(dependsOnGroups={"v2-meters-basic"})
  public void verifyMeterListFilterWithMeterIdResponseCode() throws FileNotFoundException, IOException, ParseException{
  	for(String authToken:tokensList){
  		List <String>JSONMeterIdValues=ml.getJSONFieldValues(authToken, "/v2/meters", "meter_id");
  		
  		  for(String jsonValue: JSONMeterIdValues){
  			  String newResPath="/v2/meters/"+jsonValue;
  			  Assert.assertEquals(ml.getMetersStatus(authToken, newResPath), 200);
  			  
  			  
  			  
  		  }
  		  
  		  }
  	//System.out.println("Success: verifyMeterListFilterWithMeterIdResponseCode");
  }
@Test(dependsOnGroups={"v2-meters-basic"})
public void verifyMeterListFilterWithMeterIdAllJSONFieldsExist() throws FileNotFoundException, IOException, ParseException{
	for(String authToken:tokensList){
		List <String>JSONMeterIdValues=ml.getJSONFieldValues(authToken, "/v2/meters", "meter_id");
		
		  for(String jsonValue: JSONMeterIdValues){
			  String newResPath="/v2/meters/"+jsonValue;
			  
			 
			  List<Boolean> list=mlv.verifyTenant(authToken,newResPath);
			  
			  for(boolean boo:list){
				  Assert.assertTrue(boo);
			  }
			  
		  }
		  
		  }
	//System.out.println("Success: verifyMeterListFilterWithMeterIdAllJSONFieldsExist");
}
  @AfterClass
  public void afterClass() {
	  tdd=null;
  }

}

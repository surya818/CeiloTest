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

public class MeterListValidatorTest {
	static TestDataDriver tdd;
	MeterListVerification mlv; 
	MeterList ml;
	String authToken;
	List<String> tokensList;
	
	
  @Test
  public void meterListResponseJSONObjectsExist() throws IOException, ParseException {
	  for(String authToken:tokensList){
	  int size=mlv.meterListSize(authToken,"/v2/meters/");
	  
	  Assert.assertTrue(size>0);
	  }
	  
	  System.out.println("Success: meterListResponseJSONObjectsExist");
	  
  }
  @Test
  public void validateMeterListResponseJSONFields() throws FileNotFoundException, IOException, ParseException{
	  for(String authToken:tokensList){
	  List<Boolean> list=mlv.verifyTenant(authToken,"/v2/meters/");
	  
	  for(boolean boo:list){
		  Assert.assertTrue(boo);
	  }
	  }
	  System.out.println("Success: validateMeterListResponseJSONFields");
  }
  @BeforeClass
  public void beforeClass() throws IOException {
	  tdd=new TestDataDriver();
	  mlv=new MeterListVerification();
	  ml=new MeterList();
	  tokensList=new ArrayList<String>();
	  
	  List<String[]>lines=tdd.getTestData();
	  for(String[] sa:lines){
	  authToken=ml.postToken(sa[0], sa[1], sa[2]);
	  tokensList.add(authToken);
	  }
  }

  @AfterClass
  public void afterClass() {
	  tdd=null;
  }

}

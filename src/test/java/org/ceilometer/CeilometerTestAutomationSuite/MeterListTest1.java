
package org.ceilometer.CeilometerTestAutomationSuite;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import org.ceilometer.CeilometerTestAutomationSuite.MeterList;;





public class MeterListTest1 {
	
	MeterList mr=new MeterList();
	String authToken;
	List<String> tokensList;
	Properties pro=new Properties();
	
	@BeforeClass
	public void init() throws IOException{
		InputStream is=new FileInputStream("props.properties");
		pro.load(is);
	}
	
@BeforeClass

public void setUp() throws IOException{
	String tmp="";
	
	
	BufferedReader br=new BufferedReader(new FileReader("TestData.txt"));
	tokensList=new ArrayList<String>();
	while((tmp=br.readLine())!=null){
		String[]creds=tmp.split(",");
		authToken=MeterList.postToken(creds[0], creds[1], creds[2]);
		
		tokensList.add(authToken);
		
	}
	
	
	
}
	
	@Test(dependsOnGroups={"v2-meters-basic"})
	public void verifyGetMetersResponseCode() throws Exception{
		
		for(int i=0;i<tokensList.size();i++){
			int stat_code=mr.getMetersStatus(tokensList.get(i),"/v2/meters/");
			Assert.assertEquals(stat_code,200);
			if(stat_code!=200){
				throw new Exception(" Request failed with HTTP error code: "+stat_code);
			}
		}
	System.out.println("Success: verifyGetMetersResponseCode");
	}
	
	@Test
	public void invalidTokenGetMetersResponseCode() throws Exception{
		
			int stat_code=mr.getMetersStatus(tokensList.get(0)+"a","/v2/meters/");
			
			
			
			Assert.assertEquals(stat_code,401);
			if(stat_code!=401){
				throw new Exception(" Invalid Token: "+stat_code);
			}
		
			System.out.println("Success: invalidTokenGetMetersResponseCode");
	}
	
	
	
	

}

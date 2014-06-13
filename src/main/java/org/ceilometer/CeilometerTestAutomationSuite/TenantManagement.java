package org.ceilometer.CeilometerTestAutomationSuite;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class TenantManagement {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		/*
		Calendar cal2 = new GregorianCalendar();
		System.out.println(cal2.getTimeInMillis());
		//System.out.println(cal2.getTime());
		SimpleDateFormat date_format = new SimpleDateFormat("yy_MM_dd");
		System.out.println(buildSelfRegJson());
		createDomain();
		*/
	}
	
	public static String buildSelfRegJson(){
		String uname=createUser();
		String selfRegJson1="'{\"JobTicket\" : {\"actionParams\": {\"username\" : \"";
		String selfRegJson2="\", \"password\" : \"changeme\", \"emailAddress\":\"ceilotest@hp.com\", \"emailValidationRequired\":\"false\"}}}'";
		return selfRegJson1+uname+selfRegJson2;
	}
	public static String createUser(){
		String newUser="ceilotest_";
		Calendar cal2 = new GregorianCalendar();
		SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
		return newUser+date_format.format(cal2.getTime());
	}
	
	public static void createDomain() throws FileNotFoundException, IOException{
		String selfRegjson=buildSelfRegJson();
		Properties pro=new Properties();
		pro.load(new FileInputStream("props.properties"));
		Client client = Client.create();
		 
		WebResource webResource = client.resource(pro.getProperty("STB_IDENTITY_SERVER")+"/v2.0/HP-IDM/v1.0/action/SelfRegistration?timeOut=90000");
		ClientResponse resp=webResource.accept("application/json").header("Content-Type","application/json").post(ClientResponse.class, selfRegjson);
		System.out.println(resp.getStatus());
		BufferedReader br=new BufferedReader(new InputStreamReader(resp.getEntityInputStream()));
		String tmp="";String out="";
		while((tmp=br.readLine())!=null){
			System.out.println(tmp);
			
		}
		
		
	}

}

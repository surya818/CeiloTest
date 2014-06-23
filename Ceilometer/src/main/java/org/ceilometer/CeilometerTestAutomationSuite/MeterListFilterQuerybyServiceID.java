package org.ceilometer.CeilometerTestAutomationSuite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ceilometer.CeilometerTestAutomationSuite.MeterListFilterVerification.ValueNotFoundException;
import org.json.simple.parser.ParseException;

public class MeterListFilterQuerybyServiceID {

	public static void main(String[] args) throws FileNotFoundException, ValueNotFoundException, IOException, ParseException {
		// TODO Auto-generated method stub
		MeterList ml=new MeterList();
		System.out.println(getServicesList(ml.postToken("10227194874364","glancetest","abc@123"),"/v2/meters"));

	}
	
	
	public static List<String> getServicesList(String authToken,String resourcePath) throws FileNotFoundException, ValueNotFoundException, IOException, ParseException{
		MeterListFilterVerification mlfv=new MeterListFilterVerification();
		MeterList ml=new MeterList();
		List<String> outer=mlfv.getListOfFields(ml.getMetersResponseList(authToken, resourcePath), "resource_metadata");
		
		
		List<String> inner=new ArrayList<String>();
		for(String str:outer){
			String[] split=str.split("=");
			String ser_val=split[1].substring(0, split[1].length()-1);
			inner.add(ser_val);
			
		}
		return inner;
	}

}

package org.ceilometer.CeilometerTestAutomationSuite;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.ceilometer.CeilometerTestAutomationSuite.MeterListFilterVerification.ValueNotFoundException;
import org.json.simple.parser.ParseException;

public class MeterListFilterQuery {

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, ValueNotFoundException {
		MeterListFilterQuery mlq=new MeterListFilterQuery();
		String query=mlq.genQuery("metadata.project_id","eq","10335348806483");
		
		MeterList ml=new MeterList();
		String tok=ml.postToken("10335348806483","sur81113T1713_stb","abc@123");
		System.out.println(ml.getMetersResponseList(tok, "/v2/meters"));
		System.out.println("***********************");
		MeterListFilterVerification mlfv=new MeterListFilterVerification(); 
		List<String >l=mlfv.getListOfFields(ml.getMetersResponseList(tok, "/v2/meters/"), "name");
		System.out.println("(((((((((((((())))))))))))))))))))");
		System.out.println(l);
		MeterListVerification a=new MeterListVerification();
		for(String s:l){
			String bob="/v2/meters/"+s+query;
			//System.out.println(bob);
			//System.out.println(ml.getMetersResponseList(tok, bob));
			
			//System.out.println(a.verifyTenant(tok,bob ));
		}
		//System.out.println(ml.getMetersResponseList(tok, query));
		
		
	}
	//q.field=metadata.project_id&q.op=eq&q.value=91267388353562,10789593958737
	public String genQuery(String fieldName,String op,String fieldValue){
		
		String q1="?q.field=";
		String q2="&q.op=";
		String q3="&q.value=";
		return q1+fieldName+q2+op+q3+fieldValue;
		
	}
	
	

}

package org.ceilometer.CeilometerTestAutomationSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class MeterListFilterVerification {
	

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, ValueNotFoundException {
		/*// TODO Auto-generated method stub
		MeterList ml=new MeterList();
		MeterListFilterVerification mlfv=new MeterListFilterVerification();
		List<LinkedHashMap> lm=ml.getMetersResponseList((ml.postToken("10248803747547","sur81113T178_stb","abc@123")),"/v2/meters/instance:standard.8xlarge");
		System.out.println(lm);
		
		//System.out.println(mlfv.verifyField(lm, "counter_name"));
		try{
		System.out.println(mlfv.verifyField(lm, "counter_name"));
		}
		catch(ValueNotFoundException ve){
			System.out.println("BAAAAAAAAAAAAAABAAAAAAAAAAAAAAA");
		}
		
*/
	}
	class ValueNotFoundException extends Exception {
		
		
		 
	    private String message = "Bollocks to you";
	 
	    public ValueNotFoundException() {
	        super();
	    }
	 
	    public ValueNotFoundException(String message) {
	        super(message);
	        this.message = message;
	    }
	 
	    public ValueNotFoundException(Throwable cause) {
	        super(cause);
	    }
	 
	    @Override
	    public String toString() {
	        return message;
	    }
	 
	    @Override
	    public String getMessage() {
	        return message;
	    }
	}
	
	public List<String> verifyField(List<LinkedHashMap> list ,String field) throws ValueNotFoundException{
		String tmp;
		List<String> fieldValues=new ArrayList<String>();
		for(LinkedHashMap map:list){
			Iterator it=map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry entry=(Entry) it.next();
				tmp=entry.getKey().toString();
				if(tmp.equals(field)){
					//System.out.println(tmp);
					fieldValues.add(entry.getValue().toString());
				}
				
			}
		}
		return fieldValues;
	
	
	}
}

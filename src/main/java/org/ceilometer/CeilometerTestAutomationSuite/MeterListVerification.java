package org.ceilometer.CeilometerTestAutomationSuite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.parser.ParseException;

public class MeterListVerification {
	static MeterList mlist=new MeterList();
	 

	public static void main(String[] args) throws IOException, ParseException {
		/*
		MeterListVerification mlv=new MeterListVerification();
		System.out.println(mlv.verifyTenant(mlist.postToken("10248803747547","sur81113T178_stb","abc@123"), "/v2/meters/"));
		*/
	}
	
	
	public int meterListSize(String authToken,String resourcePath) throws FileNotFoundException, IOException, ParseException{
		List<LinkedHashMap> list=mlist.getMetersResponseList(authToken, resourcePath);
		return list.size();
				
	}
	
	public List<Boolean> verifyTenant(String authToken,String resourcePath) throws FileNotFoundException, IOException, ParseException{
		List<LinkedHashMap> list=mlist.getMetersResponseList(authToken,resourcePath);
		List<Boolean> verifyJSON=new ArrayList<Boolean>();
		int size=list.size();		
		int tenant_id_count=0;
		int meter_id_count = 0,resource_id_count = 0,user_id_count=0,counter_name_count=0,resource_metadata_count=0,counter_volume_count=0,timestamp_count=0,project_id_count=0;
		for(LinkedHashMap map:list){
			Iterator it=map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry entry=(Entry) it.next();
				if(resourcePath.equals("/v2/meters/")){
					if(entry.getKey().toString().equals("project_id")){
						project_id_count++;
					}
					if(entry.getKey().toString().equals("meter_id")){
						meter_id_count++;
					}
					if(entry.getKey().toString().equals("resource_id")){
						resource_id_count++;
					}
					if(entry.getKey().toString().equals("user_id")){
						user_id_count++;
					}
				}
				else if(resourcePath.length()>11){
					if(entry.getKey().toString().equals("counter_name")){
						counter_name_count++;
					}
					if(entry.getKey().toString().equals("resource_metadata")){
						resource_metadata_count++;
					}
					if(entry.getKey().toString().equals("resource_id")){
						resource_id_count++;
					}
					if(entry.getKey().toString().equals("user_id")){
						user_id_count++;
					}
					if(entry.getKey().toString().equals("counter_volume")){
						counter_volume_count++;
					}
					if(entry.getKey().toString().equals("project_id")){
						project_id_count++;
					}
					if(entry.getKey().toString().equals("timestamp")){
						timestamp_count++;
					}
				}
				
			}
			
			
		}
		if(size==tenant_id_count && size>0){
			verifyJSON.add(true);
		}
		if(size==meter_id_count && size>0){
			verifyJSON.add(true);
		}
		if(size==resource_id_count && size>0){
			verifyJSON.add(true);
		}
		if(size==user_id_count && size>0){
			verifyJSON.add(true);
		}
		//
		if(size==counter_name_count && size>0){
			verifyJSON.add(true);
		}
		if(size==resource_metadata_count && size>0){
			verifyJSON.add(true);
		}
		if(size==resource_id_count && size>0){
			verifyJSON.add(true);
		}
		if(size==counter_volume_count && size>0){
			verifyJSON.add(true);
		}
		//
		if(size==resource_metadata_count && size>0){
			verifyJSON.add(true);
		}
		if(size==project_id_count && size>0){
			verifyJSON.add(true);
		}
		if(size==counter_volume_count && size>0){
			verifyJSON.add(true);
		}
		return verifyJSON;
		
		
		
	}
		
		public boolean allJSONfieldsExist(String auth,String resourcePath) throws FileNotFoundException, IOException, ParseException{
			MeterListVerification mlv=new MeterListVerification();
			List <Boolean> bools=mlv.verifyTenant(auth, resourcePath);
			for(boolean b:bools){
				if(b==false){
					return false;
				}
			}
			return true;
		}
	
	


}

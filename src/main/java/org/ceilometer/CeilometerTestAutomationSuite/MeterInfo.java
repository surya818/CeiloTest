package org.ceilometer.CeilometerTestAutomationSuite;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class MeterInfo {



	public static List<Map<String,String>> getMetersInfo(String authToken) throws FileNotFoundException, IOException{
		Properties pro=new Properties();
		pro.load(new FileInputStream("props.properties"));
		List<Map<String,String>> complete_meters_list=new ArrayList<Map<String,String>>();
		
		Client client = Client.create();
		 
		WebResource webResource = client
		   .resource(pro.getProperty("REPORTING_SERVER")+"/v2/meters/");
 
		ClientResponse response = webResource.accept("application/json").header("X-Auth-Token", authToken).get(ClientResponse.class);
		
		BufferedReader br=new BufferedReader(new InputStreamReader(response.getEntityInputStream()));
		String tmp="";String out="";
		while((tmp=br.readLine())!=null){
			System.out.println(tmp);
			
		}
		//System.out.println(out);
		String [] toks=out.split("user_id");
		for(String temp:toks){
			if(temp.length()>4){
			Map<String,String> meters_map=new TreeMap<String,String>();
			
			//Stripping resource_id
			String res_id="";
			Pattern p=Pattern.compile("resource_id\":\\s\"[a-zA-Z0-9].+,\\s\"source");
			Matcher m=p.matcher(temp);
			while(m.find()){
				res_id=m.group();
				
			}
			res_id=res_id.substring(15,res_id.length()-10);
			
			meters_map.put("resource_id", res_id);
			
			
			//Stripping meter name
			
			String meter_name="";
			Pattern p_name=Pattern.compile("name\":\\s\"[a-zA-Z0-9].+\",\\s\"resource_id");
			Matcher m_name=p_name.matcher(temp);
			while(m_name.find()){
				meter_name=m_name.group();
			}
			
			//printing meter_name
			
			//System.out.println(meter_name=meter_name.substring(8,meter_name.length()-15));
			meter_name=meter_name.substring(8,meter_name.length()-15);
			meters_map.put("meter_name", meter_name);
			
			
			String meter_id="";
			Pattern p_meter_id=Pattern.compile("meter_id\":\\s\"[a-zA-Z0-9].+\",\\s\"project_id");
			Matcher m_meter_id=p_meter_id.matcher(temp);
			while(m_meter_id.find()){
				meter_id=m_meter_id.group();
			}
			
			//printing meter_id
			//System.out.println(meter_id=meter_id.substring(12, meter_id.length()-14));
			//System.out.println(meter_name=meter_name.substring(8,meter_name.length()-15));
			meter_id=meter_id.substring(12, meter_id.length()-14);
			
			meters_map.put("meter_id", meter_id);
			
			//Stripping project_id
			String project_id="";
			Pattern p_project_id=Pattern.compile("project_id\":\\s\"[a-zA-Z0-9].+\",\\s\"type");
			Matcher m_project_id=p_project_id.matcher(temp);
			while(m_project_id.find()){
				
				project_id=m_project_id.group();
			}
			
			//printing project_id
			project_id=project_id.substring(14, project_id.length()-9);
			//System.out.println(project_id=project_id.substring(14, project_id.length()-9));
			meters_map.put("project_id", project_id);
			complete_meters_list.add(meters_map);
			}
		}
		
		return complete_meters_list;
		
		
	}
	
	
	
	public static String getMetersStatusMsg(String authToken) throws FileNotFoundException, IOException{
		Properties pro=new Properties();
		pro.load(new FileInputStream("props.properties"));
		
		
		Client client = Client.create();
		 
		WebResource webResource = client
		   .resource(pro.getProperty("REPORTING_SERVER")+"/v2/meters/");
 
		ClientResponse response = webResource.accept("application/json").header("X-Auth-Token", authToken)
                   .get(ClientResponse.class);
		
		
		/*BufferedReader br=new BufferedReader(new InputStreamReader(response.getEntityInputStream()));
		String tmp="";String out="";
		while((tmp=br.readLine())!=null){
			//System.out.println(tmp+" bAABA ");
			out+=tmp;
		}
		*/
		return response.toString();
		
	}
}

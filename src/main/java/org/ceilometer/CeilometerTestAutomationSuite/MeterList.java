package org.ceilometer.CeilometerTestAutomationSuite;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class MeterList {
	
	public static void main(String[]args) throws FileNotFoundException, IOException, ParseException{
		
		/*
		MeterList ml=new MeterList();
		//System.out.println(ml.getMeters("HPAuth10_bfafb1254b374c9685c090d55c6dcc25c7878597272f28855cbb481e716eae8a"));
		
		//getMetersResponse(postToken("10227194874364","glanctest","abc@123"));
		
		
		//System.out.println(je.nameExists(r, "project_id"));
		
		//System.out.println("*********************|||||||||||||||||||||**********************");
		//System.out.println(ml.getMetersResponseList((postToken("10227194874364","glancetest","abc@123")),"/v2/meters"));
		List li=ml.getJSONFieldValues((postToken("10227194874364","glancetest","abc@123")),"/v2/meters","project_id");
		System.out.println(li);
		System.out.println("%%%%%%%%%%^^^^^^^^^^^^^^%%%%%%%%%%%%%%%%");
		String at=postToken("10227194874364","glancetest","abc@123");
		System.out.println(at);
		for(Object o:li){
			System.out.println(o.toString());
			String resourcePath="/v2/meters/"+o.toString();
			System.out.println(ml.getMetersResponseList(at, resourcePath));
			System.out.println();
			System.out.println("************************************************************************************");
			System.out.println();
		}
		/*
		System.out.println(postToken("10227194874364","glancetest","abc@123"));
		//getMetersInfo(postToken("10963373413755","sur101113T1632_stb","abc@123"));
		getMetersInfo(postToken("10227194874364","glancetest","abc@123"));
		//getMetersInfo(postToken("10248803747547","sur81113T178_stb","abc@123"));
		System.out.println(buildTokenJSON("10963373413755","sur101113T1632_stb","abc@123"));
		*/
	}
	
	
	public static String buildTokenJSON(String tenantId,String uname,String passwd){
		String jsonBody1="'{\"auth\": {\"tenantId\" : \"";
		
		
		String jsonBody2="\", \"passwordCredentials\": {\"username\": \"";
		String jsonBody3="\", \"password\": \"";
		String jsonBody4="\"}}}'";
		String finalJson=jsonBody1+tenantId+jsonBody2+uname+jsonBody3+passwd+jsonBody4;
		return finalJson;
		
		
		//String jsonBody="'{\"auth\": {\"tenantId\" : \"10963373413755\", \"passwordCredentials\": {\"username\": \"sur101113T1632_stb\", \"password\": \"abc@123\"}}}'";
	}
	
	/*This method returns Auth token
	 * 
	 * We're sending the http post to /tokens of Identity server and resourcePathing the auth token based on the regular expression
	 * 
	 */
	public static String postToken(String TenantId,String username,String password) throws IOException{
		String jBody=buildTokenJSON(TenantId,username,password);
		//System.out.println(jBody);
		Properties pro=new Properties();
		InputStream is=new FileInputStream("props.properties");
		String tukda="\'{\"auth\": {\"tenantId\" : \"10963373413755\", \"passwordCredentials\": {\"username\": \"sur101113T1632_stb\", \"password\": \"abc@123\"}}}'";
		pro.load(is);
		Client cli=Client.create();
		
		WebResource wr=cli.resource(pro.getProperty("IDENTITY_SERVER")+"/v2.0/tokens");
		
		ClientResponse resp=wr.header("Content-Type", "application/json").post(ClientResponse.class,jBody);
		
		//System.out.println(resp.getStatus());
		//System.out.println(resp.toString());
		InputStream iss=resp.getEntityInputStream();
		BufferedReader br=new BufferedReader(new InputStreamReader(iss));
		String tmp="";
		while((tmp=br.readLine())!=null){
			if(tmp.contains("HPAuth")){
				Pattern p=Pattern.compile("HP[a-zA-Z_0-9].+[a-zA-Z0-9]");
				Matcher m=p.matcher(tmp);
				while(m.find()){
					return m.group();
				}
			}
		}
		return "Unable to get Token"+resp.getStatus();
	}
	
	public ClientResponse getMetersResponse(String authToken,String resourcePath){
		Properties pro=new Properties();
		try {
			pro.load(new FileInputStream("props.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Client client = Client.create();
		
		 
		WebResource webResource = client
		   .resource(pro.getProperty("REPORTING_SERVER")+resourcePath);
 
		ClientResponse response = webResource.accept("application/json").header("X-Auth-Token", authToken)
                   .get(ClientResponse.class);
		return response;
		
	}
	
	
	
	public int getMetersStatus(String authToken,String resourcePath) throws FileNotFoundException, IOException{
		ClientResponse response = getMetersResponse(authToken,resourcePath);
		
		return response.getStatus();
		
		
	}
	
	
	public List<LinkedHashMap> getMetersResponseList(String authToken,String resourcePath) throws FileNotFoundException, IOException, ParseException{
		
 
		ClientResponse response = getMetersResponse(authToken, resourcePath);
		InputStreamReader isr=new InputStreamReader(response.getEntityInputStream());
		JSONParser jp=new JSONParser();
		ContainerFactory cf=new ContainerFactory(){

			
			public List creatArrayContainer() {
				// TODO Auto-generated method stub
				return new LinkedList();
			}

			
			public Map createObjectContainer() {
				// TODO Auto-generated method stub
				return new LinkedHashMap();
			}
			
		};
		List<LinkedHashMap> l = new LinkedList<LinkedHashMap>();
		try{
		l=(LinkedList<LinkedHashMap>)jp.parse(isr, cf);
		}
		catch(ClassCastException ce){
			System.out.println("Exception in MeterList.getMetersResponseList method: Invalid tenant details");
		}
		return l;
		
				
	}
	public List getJSONFieldValues(String authToken,String resourcePath,String JsonfieldName) throws FileNotFoundException, IOException, ParseException{
		List l=new ArrayList();
		MeterList ml=new MeterList();
		List<LinkedHashMap> lm=ml.getMetersResponseList(authToken, resourcePath);
		for(LinkedHashMap map:lm){
			Iterator it=map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry entry=(Entry) it.next();
				if(entry.getKey().equals(JsonfieldName)){
					l.add(entry.getValue());
				}
			}
		}
		return l;
		
	}
	
	
	
	

}

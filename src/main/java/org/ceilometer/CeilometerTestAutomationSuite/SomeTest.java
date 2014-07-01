package org.ceilometer.CeilometerTestAutomationSuite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SomeTest {

	public static void main(String[] args) throws ParseException {
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		String st="[{\"id\":1,\"name\":\"surya\",\"team\":\"CS\",\"passion\":\"code\"},{\"id\":2,\"name\":\"sunil\",\"team\":\"COS\",\"passion\":\"travel\"}]";
		
		JSONParser jp = new JSONParser();
		
		ContainerFactory cf = new ContainerFactory() {
			
			public Map createObjectContainer() {
				// TODO Auto-generated method stub
				return new HashMap();
			}
			
			public List creatArrayContainer() {
				// TODO Auto-generated method stub
				return new ArrayList();
			}
		};
		
		
		ArrayList<HashMap> al = new ArrayList<HashMap>();
		
		al = (ArrayList<HashMap>) jp.parse(st, cf);
		
		System.out.println(al);
		
		
		for(HashMap hm : al){
			
			Set<Entry> l= hm.entrySet();
			
			Iterator<Entry> it = l.iterator();
			while(it.hasNext()){
				
				if(it.next().getKey().equals("name")){
					if(!it.next().getKey().equals("baba")){
						System.out.println(it.next());
					}
				}
				
			}
			
		}

	}

}

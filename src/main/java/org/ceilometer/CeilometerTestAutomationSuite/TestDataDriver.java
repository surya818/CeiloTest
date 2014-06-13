package org.ceilometer.CeilometerTestAutomationSuite;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDataDriver {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/*
		TestDataDriver tdd=new TestDataDriver();
		String [] lines=tdd.getTestData().get(0);
		System.out.println(lines[0]+lines[1]+lines[2]);
*/
	}
	
	
	public List<String[]> getTestData() throws IOException{
		BufferedReader br=new BufferedReader(new FileReader("TestData.txt"));
		List<String[]> dataList=new ArrayList<String[]>();
		String s="";
		while((s=br.readLine())!=null){
			String [] dataLine=s.split(",");
			if(dataLine.length==3){
			dataList.add(dataLine);
			}
		}
		return dataList;
		
		
	}

}

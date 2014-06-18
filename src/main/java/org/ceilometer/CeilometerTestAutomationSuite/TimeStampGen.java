package org.ceilometer.CeilometerTestAutomationSuite;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeStampGen {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		String st="2014-05-02T12:00:00";
		System.out.println(st);
		System.out.println(changeDateByDays(st,5));
		

	}
	
	public static String changeDateByDays(String timeStamp,int changeDays) throws ParseException{
		String [] a1=timeStamp.split("T");
		String [] dateComps=a1[0].split("-");
		String curr_date="";
		for(String tmp:dateComps){
			curr_date+=tmp+"-";
		}
		curr_date=curr_date.substring(0, curr_date.length()-1);
		List newDatecomps=new ArrayList();
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(curr_date));
		c.add(Calendar.DATE, changeDays);
		String newDate=sdf.format(c.getTime());
		
		
		return newDate+"T"+a1[1];
		
	}
	

}

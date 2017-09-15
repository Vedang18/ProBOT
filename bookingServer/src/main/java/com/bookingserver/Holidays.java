package com.bookingserver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;


public class Holidays 
{
	private String date;
	private String day;
	private String reason;
	Holidays(String date, String day, String reason)
	{
		this.date = date;
		this.day = day;
		this.reason = reason;
	}
	Holidays(String [] split)
	{
		this.date = split[0].trim();
		this.day = split[1].trim();
		this.reason = split[2].trim();
	}

	@Override
	public String toString() {
		return date + ", " + day + ", " + reason;
	}

	public static void main(String[] args) {
		
		String url = "http://intranet.prorigo.com/";
		try {
			System.out.println("Working on " + url);
			List<Holidays> holidays = getHoildays(url);
			for (Holidays holiday : holidays) {
				System.out.println(holiday);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Holidays> getHoildays(String Url) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		HtmlPage page = client.getPage(Url);
		
		HtmlTable table = page.getFirstByXPath("//table");
		String s = table.asText();
		client.close();
		List<String> hoildayList = Arrays.asList(s.split("\n"));
		List<Holidays> holidays = new ArrayList<>();

		hoildayList.forEach( holiday-> holidays.add(new Holidays(holiday.split("\t"))));
		
		return holidays;
	}
	

}

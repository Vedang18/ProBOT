package com.prorigo.htmlunit;

import java.util.Calendar;
import java.util.Date;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.prorigo.entities.Meeting;
import com.prorigo.entities.User;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
public class Bookie
{

    private static final String WEBSITE = "apps.prorigo.com";
    private static final String BOOKING = "/conference/Booking";

    public void roomBooking(User user, Meeting meeting) throws Exception
    {

	try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52))
	{

	    // set proxy username and password
	    DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient
		    .getCredentialsProvider();
	    credentialsProvider.addNTLMCredentials(user.getUsername(), user.getPassword(), WEBSITE, 80, "", "");

	    String pageUrl = new StringBuilder("http://").append(WEBSITE).append(BOOKING).toString();
	    HtmlPage page = webClient.getPage(pageUrl);

	    HtmlForm form = page.getForms().get(0);
	    HtmlButton button = form.getFirstByXPath("//*[@id=\"Submit\"]");

	    HtmlSelect select = (HtmlSelect) page.getElementById("ConferenceRooms");
	    HtmlOption option = select.getOptionByValue(meeting.getRoom());
	    select.setSelectedAttribute(option, true);

	    String meetingTime = getTimeFormDate(meeting.getStartTime());
	    HtmlInput inputStartTime = form.getInputByName("StartTime");
	    inputStartTime.setValueAttribute(meetingTime);

	    meetingTime = getTimeFormDate(meeting.getEndTime());
	    HtmlInput inputEndTime = form.getInputByName("EndTime");
	    inputEndTime.setValueAttribute(meetingTime);

	    HtmlInput inputReason = form.getInputByName("Title");
	    inputReason.type("Test Booking By HtmlUnit");

	    button.click();
	}
    }

    private String getTimeFormDate(Date date)
    {
	Calendar instance = Calendar.getInstance();
	instance.setTime(date);
	int hour = instance.get(Calendar.HOUR_OF_DAY);
	int min = instance.get(Calendar.MINUTE);
	String median = " AM";
	if (hour > 12)
	{
	hour = hour % 12;
	median = " PM";
	}
	String meetingTime = hour + ":" + min + median;
	return meetingTime;
    }
}

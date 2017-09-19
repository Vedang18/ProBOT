package com.probot.helper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.google.common.collect.Iterables;
import com.probot.entities.Meeting;
import com.probot.entities.User;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@Component
public class Bookie {

	private static final String WEBSITE = "apps.prorigo.com";
	private static final String BOOKING = "/conference/Booking";
	private static final String SHOW_MY_BOOKINGS = "/conference/Home/MyBooking";
	private static final String SHOW_ALL_BOOKINGS = "/conference/Home/AllBooking";
	private static final String CANCEL_BOOKING = "/conference/Booking/Edit";

	@Autowired
	PasswordCoder passwordCoder;

	public void roomBooking(User user, Meeting meeting) throws Exception {

		try (final WebClient webClient = new WebClient(
				BrowserVersion.FIREFOX_52)) {

			// set proxy username and password
			addCredentials(user, webClient);

			String pageUrl = new StringBuilder("http://").append(WEBSITE)
					.append(BOOKING).toString();
			HtmlPage page = webClient.getPage(pageUrl);

			HtmlForm form = page.getForms().get(0);
			HtmlButton button = form.getFirstByXPath("//*[@id=\"Submit\"]");

			HtmlSelect select = (HtmlSelect) page
					.getElementById("ConferenceRooms");
			HtmlOption option = select.getOptionByValue(meeting.getRoom());
			select.setSelectedAttribute(option, true);

			HtmlInput inputStartTime = form.getInputByName("StartTime");
			inputStartTime.setValueAttribute(meeting.getFromTime());

			HtmlInput inputEndTime = form.getInputByName("EndTime");
			inputEndTime.setValueAttribute(meeting.getToTime());

			HtmlInput inputReason = form.getInputByName("Title");
			inputReason.type("Test Booking By HtmlUnit");

			button.click();
		}
	}

	private void addCredentials(User user, final WebClient webClient)
			throws Exception {
		DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient
				.getCredentialsProvider();
		credentialsProvider.addNTLMCredentials(user.getUsername(),
				passwordCoder.decrypt(user.getPassword()), WEBSITE, 80, "", "");
	}

	public List<Meeting> showMyBookings(User user) throws Exception {
		return getBooking(user, SHOW_MY_BOOKINGS);
	}

	public List<Meeting> showAllBookings(User user) throws Exception {
		return getBooking(user, SHOW_ALL_BOOKINGS);
	}

	private List<Meeting> getBooking(User user, String uri) throws Exception {
		final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
		addCredentials(user, webClient);
		List<Meeting> bookings = new ArrayList<Meeting>();
		
		Meeting meeting = new Meeting();
		String pageUrl = new StringBuilder("http://").append(WEBSITE)
				.append(uri).toString();
		HtmlPage page = webClient.getPage(pageUrl);
		
		HtmlTable table = (HtmlTable) page.getByXPath(".//*[@id='Grid']/table")
				.get(0);
		List<HtmlTableRow> rows = table.getRows();
		
		//TODO : Refactor the code
		for (HtmlTableRow htmlTableRow : Iterables.skip(rows,1)) {
			for (HtmlTableCell cell : htmlTableRow.getCells()) {
				if (cell.getElementsByTagName("a").getLength() != 0) {
					String url = cell.getElementsByTagName("a").get(0)
							.getAttribute("href").toString();
					meeting.setMeetingId(getMeetingUniqueId(url));
				}
				meeting.setRoom(htmlTableRow.getCell(0).getTextContent());
				/*meeting.setDate(new Date(htmlTableRow.getCell(1)
						.getTextContent()));*/
				String bookingTime = htmlTableRow.getCell(2).getTextContent();
				String[] timeArray = bookingTime.trim().split("-");
				meeting.setFromTime(timeArray[0]);
				meeting.setToTime(timeArray[1]);
				meeting.setReason(htmlTableRow.getCell(3).getTextContent());
				bookings.add(meeting);
			}
		}
		System.out.println(bookings);
		return bookings;
	}
	
	public void cancelBooking(User user,Meeting meeting) throws Exception {
		try (final WebClient webClient = new WebClient(
				BrowserVersion.FIREFOX_52)) {

			// set proxy username and password
			addCredentials(user, webClient);
			String pageUrl = new StringBuilder("http://").append(WEBSITE)
					.append(CANCEL_BOOKING).toString() + "/" + meeting.getMeetingId();
			HtmlPage page = webClient.getPage(pageUrl);
			
			HtmlForm form = page.getForms().get(0);
			HtmlButton button = form.getFirstByXPath(".//*[@id='CancelBooking']");
			
			button.click();
			Thread.sleep(1000);
			
			//TODO : Need to be enhanced
			HtmlButton confirmButton = (HtmlButton) page.getByXPath("html/body/div[4]/div[3]/div/button[1]").get(0);
			confirmButton.click();
			
			System.out.println("Booking cancelled");
		}

	}

	private String getMeetingUniqueId(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String[] segments = uri.getPath().split("/");
		String idStr = segments[segments.length - 1];
		return idStr;
	}

	public static void main(String[] args) {
		Bookie bookie = new Bookie();
		try {
			bookie.showAllBookings(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

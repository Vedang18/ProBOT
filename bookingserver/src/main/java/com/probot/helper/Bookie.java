package com.probot.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.google.common.collect.Iterables;
import com.probot.entities.Meeting;
import com.probot.entities.User;
import com.probot.exceptions.InvalidInputException;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@Component
public class Bookie
{

    private static final String WEBSITE = "apps.prorigo.com";

    private static final String BOOKING = "/conference/Booking";

    private static final String SHOW_MY_BOOKINGS = "/conference/Home/MyBooking";

    private static final String SHOW_ALL_BOOKINGS = "/conference/Home/AllBooking";

    private static final String CANCEL_BOOKING = "/conference/Booking/Delete";

    private static final Logger logger = Logger.getLogger( Bookie.class );

    @Autowired
    PasswordCoder passwordCoder;

    public void roomBooking( User user, Meeting meeting ) throws Exception
    {

        List< String > errorMessages = new ArrayList< String >();
        HtmlPage page = navigateToPage( user, BOOKING, true );
        logger.debug( "Page loaded" );

        HtmlForm form = page.getForms().get( 0 );
        HtmlButton button = form.getFirstByXPath( "//*[@id=\"Submit\"]" );

        HtmlSelect select = (HtmlSelect)page.getElementById( "ConferenceRooms" );
        HtmlOption option = select.getOptionByText( meeting.getRoom() );
        select.setSelectedAttribute( option, true );

        Date date = meeting.getDate();
        if( date != null )
        {
            HtmlTextInput startDate = form.getFirstByXPath( ".//*[@id='StartDate']" );
            DateFormat formatter = new SimpleDateFormat( "MM/dd/yyyy" );
            startDate.setAttribute( "value", formatter.format( date ) );
        }

        HtmlInput inputStartTime = form.getInputByName( "StartTime" );
        inputStartTime.setValueAttribute( meeting.getFromTime() );

        HtmlInput inputEndTime = form.getInputByName( "EndTime" );
        inputEndTime.setValueAttribute( meeting.getToTime() );

        HtmlInput inputReason = form.getInputByName( "Title" );
        inputReason.type( meeting.getReason() );

        List< String > attendeesList = meeting.getAttendees();
        if( attendeesList != null && attendeesList.size() > 0 )
        {
            HtmlSelect attendees = (HtmlSelect)page.getElementById( "AttendeesIds" );
            for( String participant : attendeesList )
            {
                attendees.getOptionByText( participant ).setSelected( true );
            }
        }
        logger.debug( "Page filled, clicking button" );
        HtmlPage nextPage = button.click();
        
        String pageUrl = new StringBuilder( "http://" ).append( WEBSITE ).append( SHOW_MY_BOOKINGS ).toString();
        if( !nextPage.getBaseURI().equals( pageUrl ) )
        {
            errorMessages.add( "Room already booked" );
            logger.error( errorMessages );
            throw new InvalidInputException( errorMessages );
        }

        // Error check
        DomNodeList< DomElement > list = page.getElementsByTagName( "span" );
        for( DomElement domElement : list )
        {
            if( domElement.getAttribute( "class" ).contains( "field-validation-error" ) )
            {
                errorMessages.add( domElement.getTextContent() );
            }
        }

        if( errorMessages.size() > 0 )
        {
            logger.error( errorMessages );
            throw new InvalidInputException( errorMessages );
        }
    }

    public List< Meeting > showMyBookings( User user ) throws Exception
    {
        return getBooking( user, SHOW_MY_BOOKINGS );
    }

    public List< Meeting > showAllBookings( User user ) throws Exception
    {
        return getBooking( user, SHOW_ALL_BOOKINGS );
    }

    private List< Meeting > getBooking( User user, String uri ) throws Exception
    {
        List< Meeting > bookings = new ArrayList< Meeting >();
        HtmlPage page = navigateToPage( user, uri, false );

        logger.debug( "Page loaded" );
        HtmlTable table = (HtmlTable)page.getByXPath( ".//*[@id='Grid']/table" ).get( 0 );
        List< HtmlTableRow > rows = table.getRows();

        logger.debug( "Retriving information for " + uri );
        for( HtmlTableRow htmlTableRow : Iterables.skip( rows, 1 ) )
        {
            Meeting meeting = new Meeting();

            String asText = htmlTableRow.asText();
            String[] split = asText.split( "\t" );
            if( split.length <= 1 )
            {
                return bookings;
            }
            meeting.setRoom( split[0].trim() );
            String bookingDate = split[1].trim();
            DateFormat format = new SimpleDateFormat( "MM/dd/yyyy", Locale.ENGLISH );
            meeting.setDate( format.parse( bookingDate ) );

            String bookingTime = split[2].trim();
            String[] timeArray = bookingTime.split( "-" );
            meeting.setFromTime( timeArray[0].trim() );
            meeting.setToTime( timeArray[1].trim() );

            meeting.setReason( split[3].trim() );
            if( uri.equals( SHOW_ALL_BOOKINGS ) )
            {
                meeting.setAttendees( Arrays.asList( split[4].trim() ) );
            }
            else
            {
                String meetingId = getMeetingId( htmlTableRow );
                meeting.setMeetingId( meetingId );
            }
            bookings.add( meeting );
        }
        return bookings;
    }

    public void navigateToPage( User user, boolean enableJS ) throws Exception, IOException, MalformedURLException
    {
        navigateToPage( user, SHOW_MY_BOOKINGS ,enableJS );
    }

    private HtmlPage navigateToPage( User user, String uri, boolean enableJS ) throws Exception, IOException, MalformedURLException
    {
        final WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled( false );
        webClient.getOptions().setJavaScriptEnabled( enableJS );
        addCredentials( user, webClient );

        String pageUrl = new StringBuilder( "http://" ).append( WEBSITE ).append( uri ).toString();
        HtmlPage page = webClient.getPage( pageUrl );
        return page;
    }

    public void cancelBooking( User user, Meeting meeting ) throws Exception
    {
        final WebClient webClient = new WebClient();
        addCredentials( user, webClient );

        String uri = CANCEL_BOOKING + "/" + meeting.getMeetingId();
        String pageUrl = new StringBuilder( "http://" ).append( WEBSITE ).append( uri ).toString();

        URL url = new URL( pageUrl );
        WebRequest requestSettings = new WebRequest( url, HttpMethod.POST );

        Page redirectPage = webClient.getPage( requestSettings );
        logger.debug( "Confirmed Cancellation " + redirectPage.getWebResponse().getContentAsString() );
    }


    /**
     * Gets meeting id of meeting
     * @param htmlTableRow
     * @return meeting Id
     * @throws URISyntaxException
     */
    private String getMeetingId( HtmlTableRow htmlTableRow ) throws URISyntaxException
    {
        String meetingId = null;
        for( HtmlTableCell cell : htmlTableRow.getCells() )
        {
            if( cell.getElementsByTagName( "a" ).getLength() != 0 )
            {
                String url = cell.getElementsByTagName( "a" ).get( 0 ).getAttribute( "href" ).toString();
                meetingId = getMeetingUniqueId( url );
            }
        }
        return meetingId;
    }

    /**
     * Adds credentials to access intranet
     * @param user
     * @param webClient
     * @throws Exception
     */
    private void addCredentials( User user, final WebClient webClient ) throws Exception
    {
        DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider)webClient
            .getCredentialsProvider();
        credentialsProvider.addNTLMCredentials( user.getUsername(), passwordCoder.decrypt( user.getPassword() ), WEBSITE,
                                                80, "", "" );
        webClient.getOptions().setPrintContentOnFailingStatusCode( false );
    }

    /**
     * Gets meeting id of meeting from meeting url  
     * @param url
     * @return meeting Id
     * @throws URISyntaxException
     */
    private String getMeetingUniqueId( String url ) throws URISyntaxException
    {
        URI uri = new URI( url );
        String[] segments = uri.getPath().split( "/" );
        String idStr = segments[segments.length - 1];
        return idStr;
    }

}

package com.probot.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.probot.entities.Meeting;
import com.probot.entities.User;
import com.probot.models.BookingModel;
import com.probot.services.IBookingService;
import com.probot.services.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@RestController
@EnableSwagger2
@RequestMapping( "/api/room" )
@Api( value = "roombooking", description = "Operations related to room booking" )
public class BookingController
{

    private static final Logger logger = Logger.getLogger( BookingController.class );

    @Autowired
    IUserService userService;

    @Autowired
    IBookingService bookingService;

    @RequestMapping( value = "/book", method = RequestMethod.POST )
    @ApiOperation( value = "Performs room booking" )
    public String bookRoom( @RequestBody BookingModel model, HttpServletResponse response ) throws IOException
    {
        logger.debug( "Booking " + model.getMeeting() );
        User fetchUser = model.getUser();
        Meeting meeting = model.getMeeting();
        User user = checkUserExistance( response, fetchUser );
        if( user == null )
        {
            return null;
        }
        try
        {
            bookingService.bookRoom( user, meeting );
            return JSONObject.quote( "room booked" );
        }
        catch( FailingHttpStatusCodeException e )
        {
            response.sendError( e.getStatusCode(), e.getMessage() );
        }
        catch( Exception e )
        {
            response.sendError( 500, e.getMessage() );
        }
        return null;
    }

    @RequestMapping( value = "/show", method = RequestMethod.POST )
    @ApiOperation( value = "Displays your booking" )
    public List< Meeting > showMyBookings( @RequestBody User fetchUser, HttpServletResponse response ) throws IOException
    {
        User user = checkUserExistance( response, fetchUser );
        if( user == null )
        {
            return null;
        }
        try
        {
            logger.debug( "showing booking of " + user.getUsername() );
            return bookingService.showMyBookings( user );
        }
        catch( FailingHttpStatusCodeException e )
        {
            response.sendError( e.getStatusCode(), e.getMessage() );
        }
        catch( Exception e )
        {
            response.sendError( 500, e.getMessage() );
        }
        return null;
    }

    @RequestMapping( value = "/showAll", method = RequestMethod.POST )
    @ApiOperation( value = "Displays all rooms booking (currently limited to 1 page)" )
    public List< Meeting > showAllBookings( @RequestBody User fetchUser, HttpServletResponse response ) throws IOException
    {
        User user = checkUserExistance( response, fetchUser );
        if( user == null )
        {
            return null;
        }
        try
        {
            logger.debug( "showing all booking" );
            return bookingService.showAllBookings( user );
        }
        catch( FailingHttpStatusCodeException e )
        {
            response.sendError( e.getStatusCode(), e.getMessage() );
        }
        catch( Exception e )
        {
            response.sendError( 500, e.getMessage() );
        }
        return null;
    }

    @RequestMapping( value = "/cancel", method = RequestMethod.POST )
    @ApiOperation( value = "Cancels room booking" )
    public String cancelRoomBooking( @RequestBody BookingModel model, HttpServletResponse response ) throws IOException
    {
        User fetchUser = model.getUser();
        Meeting meeting = model.getMeeting();
        logger.debug( "Canceling boooking of " + meeting );
        User user = checkUserExistance( response, fetchUser );
        if( user == null )
        {
            return null;
        }
        try
        {
            bookingService.cancelRoomBooking( user, meeting );
            return JSONObject.quote( "Meeting cancelled" );
        }
        catch( FailingHttpStatusCodeException e )
        {
            response.sendError( e.getStatusCode(), e.getMessage() );
        }
        catch( Exception e )
        {
            response.sendError( 500, e.getMessage() );
        }
        return null;
    }

    private User checkUserExistance( HttpServletResponse response, User fetchUser ) throws IOException
    {
        User user = userService.getUserByChannelAndUserId( fetchUser );
        if( user == null )
        {
            logger.info( "No user with user Id " + fetchUser.getUserId() + " found" );
            response.sendError( 401, "User does not exists" );
            return null;
        }
        return user;
    }

}

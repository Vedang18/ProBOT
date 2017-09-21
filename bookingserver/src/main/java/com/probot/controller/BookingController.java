package com.probot.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public String bookRoom( @RequestBody BookingModel model )
    {
        logger.debug( "Booking " + model.getMeeting() );
        User fetchUser = model.getUser();
        Meeting meeting = model.getMeeting();
        User user = userService.getUserByChannelAndUserId( fetchUser );
        if( user == null )
        {
            logger.info( "No user with user Id " + fetchUser.getUserId() + " found" );
            return null;
        }
        bookingService.bookRoom( user, meeting );
        return user.getUsername();
    }

    @RequestMapping( value = "/show", method = RequestMethod.POST )
    @ApiOperation( value = "Displays your booking" )
    public List< Meeting > showMyBookings( @RequestBody User fetchUser )
    {
        User user = userService.getUserByChannelAndUserId( fetchUser );
        if( user == null )
        {
            logger.info( "No user with user Id " + fetchUser.getUserId() + " found" );
        }
        else
        {
            logger.debug( "showing booking of " + user.getUsername() );
        }
        return bookingService.showMyBookings( user );
    }

    @RequestMapping( value = "/showAll", method = RequestMethod.POST )
    @ApiOperation( value = "Displays all rooms booking (currently limited to 1 page)" )
    public List< Meeting > showAllBookings( @RequestBody User fetchUser )
    {
        logger.debug( "showing all booking" );
        User user = userService.getUserByChannelAndUserId( fetchUser );
        if( user == null )
        {
            logger.info( "No user with user Id " + fetchUser.getUserId() + " found" );
        }
        return bookingService.showAllBookings( user );
    }

    @RequestMapping( value = "/cancel", method = RequestMethod.POST )
    @ApiOperation( value = "Cancels room booking" )
    public String cancelRoomBooking( @RequestBody BookingModel model )
    {
        User fetchUser = model.getUser();
        Meeting meeting = model.getMeeting();
        logger.debug( "Canceling boooking of " + meeting );
        User user = userService.getUserByChannelAndUserId( fetchUser );
        if( user == null )
        {
            logger.info( "No user with user Id " + fetchUser.getUserId() + " found" );
            return null;
        }
        bookingService.cancelRoomBooking( user, meeting );
        return user.getUsername();
    }

}

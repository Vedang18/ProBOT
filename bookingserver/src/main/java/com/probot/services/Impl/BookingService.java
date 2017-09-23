package com.probot.services.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probot.entities.Meeting;
import com.probot.entities.User;
import com.probot.helper.Bookie;
import com.probot.services.IBookingService;

/**
 * @author Vedang, Created on Sep 18, 2017
 *
 */
@Service
public class BookingService implements IBookingService
{

    @Autowired
    Bookie bookie;

    public void bookRoom( User user, Meeting meeting ) throws Exception
    {
        bookie.roomBooking( user, meeting );
    }

    public List< Meeting > showMyBookings( User user ) throws Exception
    {
        List< Meeting > myBookings = bookie.showMyBookings( user );
        return myBookings;
    }

    @Override
    public List< Meeting > showAllBookings( User user ) throws Exception
    {
        List< Meeting > allBookings = bookie.showAllBookings( user );
        return allBookings;
    }

    @Override
    public void cancelRoomBooking( User user, Meeting meeting ) throws Exception
    {
        bookie.cancelBooking( user, meeting );

    }
}

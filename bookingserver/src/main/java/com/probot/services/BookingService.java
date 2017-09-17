package com.probot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probot.entities.Meeting;
import com.probot.entities.User;
import com.probot.helper.Bookie;

/**
 * @author Vedang, Created on Sep 18, 2017
 *
 */
@Service
public class BookingService implements IBookingService
{

    @Autowired
    Bookie bookie;

    public void bookRoom(User user, Meeting meeting)
    {
	try
	{
	    bookie.roomBooking(user, meeting);
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
}

package com.probot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probot.entities.Meeting;
import com.probot.entities.User;
import com.probot.exceptions.InvalidInputException;
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
        } catch (InvalidInputException e)
        {
            e.printStackTrace();
            System.out.println(e.getErrors());
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    public List<Meeting> showMyBookings(User user)
    {
        List<Meeting> myBookings = null;
        try
        {
            myBookings = bookie.showMyBookings(user);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return myBookings;
    }

    @Override
    public List<Meeting> showAllBookings(User user)
    {
    	List<Meeting> allBookings = null;
        try
        {
            allBookings = bookie.showAllBookings(user);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return allBookings;
    }

	@Override
	public void cancelRoomBooking(User user,Meeting meeting) {
		try {
			bookie.cancelBooking(user,meeting);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

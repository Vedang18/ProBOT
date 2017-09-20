package com.probot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.probot.entities.Meeting;
import com.probot.entities.User;
import com.probot.models.BookingModel;
import com.probot.services.IBookingService;
import com.probot.services.IUserService;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@RestController
@RequestMapping("/api/room")
public class BookingController
{

    @Autowired
    IUserService userService;

    @Autowired
    IBookingService bookingService;

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public String bookRoom(@RequestBody BookingModel model)
    {
    	User fetchUser = model.getUser();
    	Meeting meeting = model.getMeeting();
        User user = userService.getUserByChannelAndUserId( fetchUser );
        if (user == null)
        {
            return null;
        }
        bookingService.bookRoom(user, meeting);
        return user.getUsername();
    }

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public List<Meeting> showMyBookings(@RequestBody User fetchUser)
    {
        User user = userService.getUserByChannelAndUserId( fetchUser );
        return bookingService.showMyBookings(user);
    }

    @RequestMapping(value = "/showAll", method = RequestMethod.POST)
    public List<Meeting> showAllBookings(@RequestBody User fetchUser)
    {
        User user = userService.getUserByChannelAndUserId( fetchUser );
        return bookingService.showAllBookings(user);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancelRoomBooking(@RequestBody BookingModel model)
    {
    	User fetchUser = model.getUser();
    	Meeting meeting = model.getMeeting();
        User user = userService.getUserByChannelAndUserId( fetchUser );
        if (user == null)
        {
            return null;
        }
        bookingService.cancelRoomBooking(user, meeting);
        return user.getUsername();
    }

}

package com.probot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.probot.entities.Meeting;
import com.probot.entities.User;
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

    // FIXME Do proper coding, take meeting as json body or separate parameter &
    // then make meeting object
    @RequestMapping("/book/{channelId}")
    public String bookRoom(@PathVariable String channelId, @RequestBody Meeting meeting)
    {
        User user = userService.getUserByChannelId(channelId);
        if (user == null)
        {
            return null;
        }
        bookingService.bookRoom(user, meeting);
        return user.getUsername();
    }

    @RequestMapping("/show/{channelId}")
    public List<Meeting> showMyBookings(@PathVariable String channelId)
    {
        User user = userService.getUserByChannelId(channelId);
        return bookingService.showMyBookings(user);
    }

    @RequestMapping("/show/all/{channelId}")
    public List<Meeting> showAllBookings(@PathVariable String channelId)
    {
        User user = userService.getUserByChannelId(channelId);
        return bookingService.showAllBookings(user);
    }
    
    @RequestMapping("/cancel/{channelId}")
    public String cancelRoomBooking(@PathVariable String channelId,@RequestBody Meeting meeting)
    {
        User user = userService.getUserByChannelId(channelId);
        if (user == null)
        {
            return null;
        }
        bookingService.cancelRoomBooking(user,meeting);
        return user.getUsername();
    }

}

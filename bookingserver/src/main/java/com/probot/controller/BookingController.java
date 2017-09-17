package com.probot.controller;

import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.BootstrapWith;
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
    public String bookRoom(@PathVariable String channelId, Meeting meeting)
    {
	User user = userService.getUserByChannelId(channelId);
	System.out.println("userName: " + user.getUsername());
	System.out.println("password: " + user.getPassword());
	bookingService.bookRoom(user, meeting);
	return user.getUsername();
    }
}

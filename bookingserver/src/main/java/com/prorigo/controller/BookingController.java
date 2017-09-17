package com.prorigo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prorigo.entities.User;
import com.prorigo.services.IUserService;

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

    // FIXME Do proper coding
    @RequestMapping("/book/{channelId}")
    public User bookRoom(@PathVariable String channelId)
    {
	User user = userService.getUserByChannelId(channelId);
	System.out.println("userName: " + user.getUsername());
	System.out.println("password: " + user.getPassword());
	return user;
    }
}

package com.probot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.probot.entities.User;
import com.probot.services.IUserService;

/**
 * @author Vedang, Created on Sep 20, 2017
 *
 */
@RestController
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
    IUserService userService;
    
    @RequestMapping(method = RequestMethod.POST)
    public void addUser(@RequestBody User user)
    {
        userService.save(user);
    }
}

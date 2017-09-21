package com.probot.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.probot.entities.User;
import com.probot.services.IUserService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Vedang, Created on Sep 20, 2017
 *
 */
@RestController
@EnableSwagger2
@RequestMapping( "/api/user" )
public class UserController
{
    private static final Logger logger = Logger.getLogger( UserController.class );

    @Autowired
    IUserService userService;

    @RequestMapping( method = RequestMethod.POST )
    public void addUser( @RequestBody User user )
    {
        logger.debug( "Saving user details of " + user.getUsername() );
        userService.save( user );
    }
}

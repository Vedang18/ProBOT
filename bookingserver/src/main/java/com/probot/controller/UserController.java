package com.probot.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.probot.entities.User;
import com.probot.services.IUserService;

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
    public String addUser( @RequestBody User user, HttpServletResponse response ) throws IOException
    {
        if( !isValid( user.getUsername() ) || !isValid( user.getPassword() ) )
        {
            return JSONObject.quote( "User input is not Valid" );
        }
        try
        {
            userService.testLogin( user );
            User userDetail = userService.getUserByChannelAndUserId( user );
            if( userDetail == null )
            {
                logger.info( "Saving user details of " + user.getUsername() );
                userService.save( user );
            }
            else
            {
                logger.info( "Updating user details of " + user.getUsername() );
                userDetail.setPassword( user.getPassword() );
                if( user.getAddress() != null && !user.getAddress().isEmpty() )
                {
                    userDetail.setAddress( user.getAddress() );
                }
                userService.update( userDetail );
            }
            return JSONObject.quote( "User added/Updated successfully" );
        }
        catch( FailingHttpStatusCodeException e )
        {
            response.sendError( e.getStatusCode(), e.getMessage() );
        }
        catch( Exception e )
        {
            logger.error( "Failed to add/update User", e );
            response.sendError( 500, "Failed to add/update  User" );
        }
        return JSONObject.quote( "Failed to add/Update User" );
    }

    @RequestMapping( value = "/byName", method = RequestMethod.POST )
    public List< User > getUserByUserName( @RequestBody User user, HttpServletResponse response ) throws IOException
    {
        logger.info( "Getting user details of " + user.getUsername() );
        List< User > userDetail = userService.getUserByUserName( user.getUsername() );
        if( userDetail == null )
        {
            logger.info( "No user with user Id " + user.getUserId() + " found" );
            response.sendError( 401, "User does not exists" );
            return null;
        }
        return userDetail;
    }

    @RequestMapping( value = "/byChannelUserId", method = RequestMethod.POST )
    public User getUserByChannelIdAndUserId( @RequestBody User user, HttpServletResponse response ) throws IOException
    {
        logger.debug( "Getting user details of user " + user.getUserId() + " channel " + user.getChannelId() );
        User userDetail = userService.getUserByChannelAndUserId( user );
        if( userDetail == null )
        {
            logger.info( "No user with user Id " + user.getUserId() + " found" );
            response.sendError( 401, "User does not exists" );
            return null;
        }
        return userDetail;
    }
    
    private boolean isValid( String input )
    {
        return input != null && !input.isEmpty();
    }

}

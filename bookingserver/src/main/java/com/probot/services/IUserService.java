package com.probot.services;

import java.util.List;

import com.probot.entities.User;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
public interface IUserService
{

    User getUserByChannelAndUserId(User user);
    List< User > getUserByUserName(String username);
    User save(User user) throws Exception;
    void testLogin( User user ) throws Exception;
    void update( User user ) throws Exception;
}

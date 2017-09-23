package com.probot.services;

import com.probot.entities.User;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
public interface IUserService
{

    User getUserByChannelAndUserId(User user);
    User getUserByUserName(String username);
    User save(String channelId, String userId, String username, String password) throws Exception;
    User save(User user) throws Exception;
    void testLogin( User user ) throws Exception;
}

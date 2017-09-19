package com.probot.services;

import com.probot.entities.User;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
public interface IUserService
{

    User getUserByChannelId(String channelId);
    User save(String channelId, String userId, String username, String password);
    User save(User user);
}

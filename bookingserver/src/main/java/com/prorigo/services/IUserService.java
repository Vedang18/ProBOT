package com.prorigo.services;

import com.prorigo.entities.User;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
public interface IUserService
{

    User getUserByChannelId(String channelId);
    
}

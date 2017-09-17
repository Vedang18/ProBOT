package com.prorigo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prorigo.entities.User;
import com.prorigo.repositories.UserRepository;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@Service
public class UserService implements IUserService
{

    @Autowired
    UserRepository userRepository;
    @Override
    public User getUserByChannelId(String channelId)
    {
	return userRepository.findByChannelId(channelId);
    }

}

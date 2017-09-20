package com.probot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probot.entities.User;
import com.probot.helper.PasswordCoder;
import com.probot.repositories.UserRepository;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@Service
public class UserService implements IUserService
{

    @Autowired
    PasswordCoder passwordCoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserByChannelId(String channelId)
    {
	return userRepository.findByChannelId(channelId);
    }

    @Override
    public void save(String channelId, String username, String password)
    {
	try
	{
	    User user = new User();
	    user.setChannelId(channelId);
	    user.setUsername(username);
	    user.setPassword(passwordCoder.encrypt(password));
	    userRepository.save(user);
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
    }

}

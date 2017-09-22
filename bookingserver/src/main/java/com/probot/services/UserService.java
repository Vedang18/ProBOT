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
    public User getUserByChannelAndUserId(User user)
    {
		return userRepository.findByChannelIdAndUserId( user.getChannelId(), user.getUserId() );
    }

    @Override
    public User save( String channelId, String userId, String username, String password ) throws Exception
    {
        User user = new User();
        user.setChannelId( channelId );
        user.setUserId( userId );
        user.setUsername( username );
        user.setPassword( passwordCoder.encrypt( password ) );
        return userRepository.save( user );
    }

    @Override
    public User save(User user) throws Exception
    {
        return save(user.getChannelId(), user.getUserId(), user.getUsername(), user.getPassword());
    }

    @Override
    public User getUserByUserName( String username )
    {
        return userRepository.findByUsername( username );
    }

}

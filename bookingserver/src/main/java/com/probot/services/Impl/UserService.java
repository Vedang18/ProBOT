package com.probot.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probot.entities.User;
import com.probot.helper.Bookie;
import com.probot.helper.PasswordCoder;
import com.probot.repositories.UserRepository;
import com.probot.services.IUserService;

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

    @Autowired
    Bookie bookie;

    @Override
    public User getUserByChannelAndUserId(User user)
    {
        return userRepository.findByChannelIdAndUserId(user.getChannelId(), user.getUserId());
    }

    @Override
    public User save( String channelId, String userId, String username, String password ) throws Exception
    {
        User user = createUser( channelId, userId, username, password );

        return userRepository.save( user );
    }

    private User createUser( String channelId, String userId, String username, String password ) throws Exception
    {
        // Create user object
        User user = new User();
        user.setChannelId( channelId );
        user.setUserId( userId );
        user.setUsername( username );
        user.setPassword( passwordCoder.encrypt( password ) );
        return user;
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
    
    @Override
    public void testLogin( User user ) throws Exception
    {
        User createdUser = createUser( user.getChannelId(), user.getUserId(), user.getUsername(), user.getPassword() );
        bookie.navigateToPage( createdUser, false );
    }

}

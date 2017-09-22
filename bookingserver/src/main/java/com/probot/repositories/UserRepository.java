package com.probot.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.probot.entities.User;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer>
{
	User findByChannelIdAndUserId( String channelId, String userId );
	User findByUsername( String username );

}

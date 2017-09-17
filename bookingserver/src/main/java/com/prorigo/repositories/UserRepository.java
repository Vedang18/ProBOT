package com.prorigo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prorigo.entities.User;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer>
{
    User findByChannelId(String channelId);

}

package com.prorigo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.prorigo.entities.User;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
public interface UserRepository extends CrudRepository<User, Integer>
{

}

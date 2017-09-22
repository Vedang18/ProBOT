package com.probot.repositories;

import org.springframework.data.repository.CrudRepository;

import com.probot.entities.Attendees;

/**
 * Repository for fetching attendees information
 * Created by vedangm on Sep 22, 2017.
 */
public interface AttendeesRepository extends CrudRepository< Attendees, String >
{

    Iterable<Attendees> findByNameIgnoreCaseContaining(String name);
    Iterable<Attendees> findByNameStartingWith(String name);
}

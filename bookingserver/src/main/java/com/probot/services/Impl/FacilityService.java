package com.probot.services.Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probot.entities.Attendees;
import com.probot.repositories.AttendeesRepository;
import com.probot.services.IFacilityService;

/**
 * @author Vedang, Created on Sep 23, 2017
 *
 */
@Service
public class FacilityService implements IFacilityService
{

    @Autowired
    AttendeesRepository attendeesRepository;

    public List<Attendees> getAttendeesNameContains(String name)
    {
        List<Attendees> attendees = new ArrayList<>();
        attendeesRepository.findByNameIgnoreCaseContaining(name).forEach(e -> attendees.add(e));
        return attendees;
    }

    @Override
    public List<Attendees> getAttendeesNameStarts(String name)
    {
        List<Attendees> attendees = new ArrayList<>();
        attendeesRepository.findByNameStartingWith(name).forEach(e -> attendees.add(e));
        return attendees;
    }

    public List<Attendees> getAttendeesNameOrSurNameStarting(String name)
    {
        Set<Attendees> attendees = new HashSet<>();
        attendeesRepository.findByNameStartingWith(name).forEach(e -> attendees.add(e));
        attendeesRepository.findByNameIgnoreCaseContaining(" " + name).forEach(e -> attendees.add(e));
        List<Attendees> attendeesList = new ArrayList<>(attendees);
        return attendeesList;
    }
}

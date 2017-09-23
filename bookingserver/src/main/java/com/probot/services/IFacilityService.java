package com.probot.services;

import java.util.List;

import com.probot.entities.Attendees;

/**
 * @author Vedang, Created on Sep 23, 2017
 *
 */
public interface IFacilityService
{
    List<Attendees> getAttendeesNameContains(String name);
    List<Attendees> getAttendeesNameStarts(String name);
    List<Attendees> getAttendeesNameOrSurNameStarting(String name);
}

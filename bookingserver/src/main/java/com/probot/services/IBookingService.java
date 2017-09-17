package com.probot.services;

import com.probot.entities.Meeting;
import com.probot.entities.User;

/**
 * @author Vedang, Created on Sep 18, 2017
 *
 */
public interface IBookingService
{
    void bookRoom(User user, Meeting meeting);
}

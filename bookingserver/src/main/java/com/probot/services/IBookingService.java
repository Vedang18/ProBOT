package com.probot.services;

import java.util.List;

import com.probot.entities.Meeting;
import com.probot.entities.User;

/**
 * @author Vedang, Created on Sep 18, 2017
 *
 */
public interface IBookingService
{
    void bookRoom(User user, Meeting meeting);
    List<Meeting> showMyBookings(User user);
    List<Meeting> showAllBookings(User user);
    void cancelRoomBooking(User user,Meeting meeting);
}

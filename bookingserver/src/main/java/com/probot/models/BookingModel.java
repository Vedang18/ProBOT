package com.probot.models;

import com.probot.entities.Meeting;
import com.probot.entities.User;

/**
 * Created by vedangm on Sep 20, 2017.
 */
public class BookingModel
{

	User user;
	Meeting meeting;

	public User getUser()
	{
		return user;
	}

	public void setUser( User user )
	{
		this.user = user;
	}

	public Meeting getMeeting()
	{
		return meeting;
	}

	public void setMeeting( Meeting meeting )
	{
		this.meeting = meeting;
	}
}

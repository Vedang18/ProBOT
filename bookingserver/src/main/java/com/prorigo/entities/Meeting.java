package com.prorigo.entities;

import java.util.Date;
import java.util.List;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
public class Meeting
{
    private String room;
    private Date startTime;
    private Date endTime;
    private String reason;
    private List<String> attendies;


    public Meeting()
    {
	
    }

    public Meeting(String room, Date startTime, Date endTime, String reason, List<String> attendies)
    {
	this.room = room;
	this.startTime = startTime;
	this.endTime = endTime;
	this.reason = reason;
	this.attendies = attendies;
    }

    public String getRoom()
    {
	return room;
    }

    public void setRoom(String room)
    {
	this.room = room;
    }

    public Date getStartTime()
    {
	return startTime;
    }

    public void setStartTime(Date startTime)
    {
	this.startTime = startTime;
    }

    public Date getEndTime()
    {
	return endTime;
    }

    public void setEndTime(Date endTime)
    {
	this.endTime = endTime;
    }

    public String getReason()
    {
	return reason;
    }

    public void setReason(String reason)
    {
	this.reason = reason;
    }

    public List<String> getAttendies()
    {
	return attendies;
    }

    public void setAttendies(List<String> attendies)
    {
	this.attendies = attendies;
    }
}

package com.probot.entities;

import java.util.Date;
import java.util.List;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
public class Meeting
{
    private String room;
    private Date date;
    private String fromTime;
    private String toTime;
    private String reason;
    private List<String> attendees;
    private String meetingId;

    public Meeting()
    {

    }

    public String getRoom()
    {
        return room;
    }

    public void setRoom(String room)
    {
        this.room = room;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getFromTime()
    {
        return fromTime;
    }

    public void setFromTime(String fromTime)
    {
        this.fromTime = fromTime;
    }

    public String getToTime()
    {
        return toTime;
    }

    public void setToTime(String toTime)
    {
        this.toTime = toTime;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public List<String> getAttendees()
    {
        return attendees;
    }

    public void setAttendees(List<String> attendees)
    {
        this.attendees = attendees;
    }

    public String getMeetingId()
    {
        return meetingId;
    }

    public void setMeetingId(String meetingId)
    {
        this.meetingId = meetingId;
    }
}

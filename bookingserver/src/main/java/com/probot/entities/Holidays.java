package com.probot.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@Entity
@Table(name = "holidays")
public class Holidays implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int uid;
    @JsonFormat(pattern="dd-MM-yyyy", timezone="IST")
    private Date date;
    private String day;
    private String reason;

    public Holidays()
    {
	
    }
    public Holidays(Date date, String day, String reason)
    {
	super();
	this.date = date;
	this.day = day;
	this.reason = reason;
    }

    public Date getDate()
    {
	return date;
    }

    public void setDate(Date date)
    {
	this.date = date;
    }

    public String getDay()
    {
	return day;
    }

    public void setDay(String day)
    {
	this.day = day;
    }

    public String getReason()
    {
	return reason;
    }

    public void setReason(String reason)
    {
	this.reason = reason;
    }

    public int getUid()
    {
	return uid;
    }
}

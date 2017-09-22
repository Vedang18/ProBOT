package com.probot.services;

import java.util.Date;
import java.util.List;

import com.probot.entities.Holidays;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
public interface IHolidayService
{
    List<Holidays> getHolidaysafter(Date date);
    List<Holidays> getAllHolidays();
    List<Holidays> getHolidaysInBetween(Date startDate, Date endDate);
}


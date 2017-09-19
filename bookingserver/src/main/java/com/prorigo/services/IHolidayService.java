package com.prorigo.services;

import java.util.Date;
import java.util.List;

import com.prorigo.entities.Holidays;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
public interface IHolidayService
{
    List<Holidays> getHolidaysafter(Date date);
    List<Holidays> getHolidaysBetween(Date startDate, Date endDate);
    List<Holidays> getAllHolidays();
    List<Holidays> getHolidaysInMonth(String month);
}


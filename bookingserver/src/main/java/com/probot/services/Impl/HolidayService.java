package com.probot.services.Impl;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probot.entities.Holidays;
import com.probot.repositories.HolidayRepository;
import com.probot.services.IHolidayService;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@Service
public class HolidayService implements IHolidayService
{
    @Autowired
    HolidayRepository holidayRepository;

    @Override
    public List<Holidays> getHolidaysafter(Date date)
    {
        List<Holidays> holidays = new ArrayList<>();
        holidayRepository.findHolidaysafter(date).forEach(e -> holidays.add(e));
        return holidays;
    }

    @Override
    public List<Holidays> getAllHolidays()
    {
        List<Holidays> holidays = new ArrayList<>();
        holidayRepository.findAllByOrderByDateAsc().forEach(e -> holidays.add(e));
        return holidays;
    }

    @Override
    public List<Holidays> getHolidaysInBetween(Date startDate, Date endDate)
    {
        List<Holidays> holidays = new ArrayList<>();
        holidayRepository.findByDateBetweenOrderByDate(startDate,endDate).forEach(e -> holidays.add(e));
        return holidays;
    }
}

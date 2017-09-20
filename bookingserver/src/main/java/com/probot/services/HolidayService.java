package com.probot.services;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probot.entities.Holidays;
import com.probot.repositories.HolidayRepository;

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
	holidayRepository.findAll().forEach(e -> holidays.add(e));
	return holidays;
    }

    @Override
    public List<Holidays> getHolidaysInMonth(String month)
    {
	//FIXME ugly code
	int m = Month.valueOf(month).getValue();
	List<Holidays> holidays = getAllHolidays();
	return holidays.subList(m / 2, (holidays.size() - 1));
    }

}

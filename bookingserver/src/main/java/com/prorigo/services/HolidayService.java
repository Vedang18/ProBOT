package com.prorigo.services;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prorigo.entities.Holidays;
import com.prorigo.repositories.HolidayRepository;

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
    public List<Holidays> getHolidaysBetween(Date startDate, Date endDate) {
        List<Holidays> holidays = new ArrayList<>();
        holidayRepository.findByDateBetweenOrderByDate(startDate, endDate).forEach(e -> holidays.add(e));
        return holidays;
    }

    @Override
    public List<Holidays> getAllHolidays()
    {
        List<Holidays> holidays = new ArrayList<>();
        holidayRepository.findAllByOrderByDate().forEach(e -> holidays.add(e));
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

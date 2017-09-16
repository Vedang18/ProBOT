package com.prorigo.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prorigo.entities.Holidays;
import com.prorigo.services.IHolidayService;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@RestController
@RequestMapping("/api/holidays")
public class HolidayController
{

    @Autowired
    IHolidayService holidayService;

    @RequestMapping("/all") 
    public List<Holidays> getAllHolidays()
    {
	return holidayService.getAllHolidays();
    }
    
    @RequestMapping("/after/{date}") 
    public List<Holidays> getHolidaysAfter(@PathVariable String date) throws ParseException
    {
	SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY");
	Date actualDate = sdf.parse(date);
	System.out.println(actualDate);
	return holidayService.getHolidaysafter(actualDate);
    }
}

package com.probot.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.probot.entities.Holidays;
import com.probot.models.HolidayModel;
import com.probot.services.IHolidayService;

import io.swagger.annotations.Api;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@RestController
@EnableSwagger2
@Api( value = "holidays", description = "returns holiday related information" )
@RequestMapping( "/api/holidays" )
public class HolidayController
{

    private static final Logger logger = Logger.getLogger( HolidayController.class );

    @Autowired
    IHolidayService holidayService;

    @RequestMapping( value = "/all", method = RequestMethod.POST )
    public List< Holidays > getAllHolidays()
    {
        logger.debug( "Fetching all holidays" );
        List< Holidays > allHolidays = holidayService.getAllHolidays();
        return allHolidays;
    }

    @RequestMapping( value = "/after/{date}", method = RequestMethod.POST )
    public List< Holidays > getHolidaysAfter( @PathVariable String date ) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
        Date actualDate = sdf.parse( date );
        logger.debug( "Fetching holidays after " + actualDate );
        return holidayService.getHolidaysafter( actualDate );
    }

    @RequestMapping( value = "/in", method = RequestMethod.POST )
    public List< Holidays > getHolidaysAfter( @RequestBody HolidayModel holidayModel ) throws ParseException
    {
        logger.debug( "Fetching holidays from " + holidayModel.getStartDate() + "to " + holidayModel.getEndDate() );
        return holidayService.getHolidaysInBetween( holidayModel.getStartDate(), holidayModel.getEndDate() );
    }

}

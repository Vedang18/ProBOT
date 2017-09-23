package com.probot.controller;

import io.swagger.annotations.Api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.probot.entities.Attendees;
import com.probot.services.IFacilityService;

/**
 * @author Vedang, Created on Sep 23, 2017
 *
 */
@RestController
@EnableSwagger2
@RequestMapping("/api/facility")
@Api(value = "roombooking", description = "Provides facilites to room booking services")
public class FacilityController
{

    private static final Logger logger = Logger.getLogger(FacilityController.class);

    @Autowired
    IFacilityService facilityService;

    @RequestMapping(value = "/attendees/{name}", method = RequestMethod.GET)
    public List<Attendees> getAttendes(@PathVariable String name, HttpServletResponse response) throws IOException
    {
        logger.info("Getting attendees for hint " + name);
        try
        {
            return facilityService.getAttendeesNameContains(name);
        } catch (Exception e)
        {
            response.sendError(500, e.getMessage());
            return null;
        }
    }
}

package com.prorigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.prorigo.entities.User;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@SpringBootApplication
@EntityScan(basePackageClasses = { User.class })
public class BookingServerApplication
{

    public static void main(String[] args)
    {
	SpringApplication.run(BookingServerApplication.class, args);
    }
}

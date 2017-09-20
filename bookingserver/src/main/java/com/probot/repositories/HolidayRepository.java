package com.probot.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.probot.entities.Holidays;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@Repository
public interface HolidayRepository extends CrudRepository<Holidays, Integer>
{
    @Query("SELECT h FROM Holidays h WHERE date > :date")
    Iterable<Holidays> findHolidaysafter(@Param("date") Date date);
}

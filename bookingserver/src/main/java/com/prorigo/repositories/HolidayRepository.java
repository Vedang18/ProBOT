package com.prorigo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prorigo.entities.Holidays;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@Repository
public interface HolidayRepository extends CrudRepository<Holidays, Integer>
{
    @Query("SELECT date FROM Holidays WHERE date > :date")
    List<Holidays> findHolidaysafter(@Param("date") Date date);
}

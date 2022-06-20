/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Responsitory;

import fpt.aptech.KHR.Entities.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

/**
 * @author Thành Nhân
 */
public interface TimelineRepository extends JpaRepository<Timeline, Integer> {
    @Query("SELECT t FROM Timeline t WHERE t.id = :id")
    Timeline findID(@PathVariable("id") int id);


    @Query(value = "SELECT t FROM Timeline t WHERE t.startdate = :startdate", nativeQuery = true)
    int findStartDay(@PathVariable("startdate") Date startdate);

    @Query(value = "SELECT COUNt(t.id) FROM Timeline t WHERE t.enddate = :enddate", nativeQuery = true)
    int findEndDay(@PathVariable("enddate") Date enddate);

}

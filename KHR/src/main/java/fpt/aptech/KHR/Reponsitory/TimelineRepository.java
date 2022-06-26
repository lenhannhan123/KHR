/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

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
    @Query("SELECT t FROM Timeline t WHERE t.id = :id AND t.isDelete = :isDelete")
    Timeline findID(@PathVariable("id") int id, @PathVariable("isDelete") short isDelete);


    @Query("SELECT t FROM Timeline t WHERE t.isDelete = :isDelete")
    List<Timeline> findAllByDelete(@PathVariable("isDelete") short isDelete);


    @Query("SELECT COUNT(t.id)  FROM  Timeline t WHERE t.startdate = :startdate AND t.isDelete = :isDelete")
    int findStartDay(@PathVariable("startdate") Date startdate, @PathVariable("isDelete") short isDelete);

    @Query("SELECT COUNT(t.id) FROM Timeline t WHERE t.enddate = :enddate AND t.isDelete = :isDelete")
    int findEndDay(@PathVariable("enddate") Date enddate, @PathVariable("isDelete") short isDelete);

}

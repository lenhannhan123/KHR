/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.Entities.TimelineDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Admin
 */
public interface TimelineDetailRepository extends JpaRepository<TimelineDetail, Integer> {

    @Query("SELECT t FROM TimelineDetail t WHERE t.id = :id")
    TimelineDetail findID(@PathVariable("id") int id);


    @Query("SELECT t FROM TimelineDetail t WHERE t.idTimeline = :idTimeline")
    List<TimelineDetail> findbyIdTimeline(@PathVariable("idTimeline") Timeline idTimeline);

    @Query("SELECT t FROM TimelineDetail t WHERE t.shiftCode = :shiftCode AND t.idTimeline = :idTimeline")
    List<TimelineDetail> findbyIdShiftcode(@PathVariable("shiftCode") int shiftCode,@PathVariable("idTimeline") Timeline idTimeline);


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.TimelineDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Admin
 */
public interface TimelineDetailRepository extends JpaRepository<TimelineDetail, Integer> {

    @Query("SELECT t FROM TimelineDetail t WHERE t.id = :id")
    TimelineDetail findID(@PathVariable("id") int id);


}

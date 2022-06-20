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

/**
 * @author Thành Nhân
 */
public interface TimelineRepository extends JpaRepository<Timeline, Integer> {
    @Query("SELECT t FROM Timeline t WHERE t.id = :id")
    Timeline findID(@PathVariable("id") int id);

}

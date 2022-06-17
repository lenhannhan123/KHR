/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Responsitory;

import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.Entities.UserTimeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Admin
 */
public interface UserTimeLineRepository extends JpaRepository<UserTimeline, Integer> {
    @Query("SELECT u FROM UserTimeline u WHERE u.id = :id")
    UserTimeline findID(@PathVariable("id") int id);
}

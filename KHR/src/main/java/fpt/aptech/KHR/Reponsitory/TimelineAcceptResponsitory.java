/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.TimeAccept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 *
 * @author Admin
 */
public interface TimelineAcceptResponsitory extends JpaRepository<TimeAccept, Integer> {

    @Query(nativeQuery = true, value = "SELECT DISTINCT idtimeline  FROM time_accept WHERE  iduser= :iduser")
    List<Integer> GetIdtimeline(@PathVariable("iduser") String iduser);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.DayOff;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author LÊ HỮU TÂM
 */
public interface DayOffRepository extends JpaRepository<DayOff, Integer> {
    @Query("SELECT d FROM DayOff d WHERE d.id = :id")
    DayOff findID(@PathVariable("id") int id);
    @Query("SELECT d FROM DayOff d WHERE d.status = :status")
    List<DayOff> findStatus(@PathVariable("status") short status);
}

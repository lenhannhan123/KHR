/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Responsitory;

import fpt.aptech.KHR.Entities.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

/**
 * @author Admin
 */
public interface ShiftRepository extends JpaRepository<Shift, Integer> {


    @Query("SELECT s FROM Shift s WHERE s.id = :id")
    Shift findID(@PathVariable("id") int id);


    @Query(value = "SELECT  * FROM Shift  WHERE Id_Timeline   = :Id_Timeline ", nativeQuery = true)
    List<Shift> findIDTime(@PathVariable("Id_Timeline ") int Id_Timeline);


}

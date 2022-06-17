/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Responsitory;

import fpt.aptech.KHR.Entities.Position;
import fpt.aptech.KHR.Entities.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Admin
 */
public interface ShiftRepository extends JpaRepository<Shift, Integer> {


    @Query("SELECT s FROM Shift s WHERE s.id = :id")
    Shift findID(@PathVariable("id") int id);


    @Query(value = "SELECT TOP 1 * FROM Shift u WHERE u.Id_Timeline  = :IdTimeLine", nativeQuery = true)
    Shift findIDTime(@PathVariable("IdTimeLine") int IdTimeLine);
}

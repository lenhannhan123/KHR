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

/**
 * @author Admin
 */
public interface ShiftRepository extends JpaRepository<Shift, Integer> {


    @Query("SELECT s FROM Shift s WHERE s.id = :id")
    Shift findID(@PathVariable("id") int id);


    @Query(value = "SELECT TOP 1 * FROM Shift u WHERE u.Id_Timeline  = :IdTimeLine", nativeQuery = true)
    Shift findIDTime(@PathVariable("IdTimeLine") int IdTimeLine);


    @Query(value = "INSER INTO Shift u (u.Id_Timeline , u.Id_Position , u.Number, u.Time_start , u.Is_OT, u.Time_end, u.Shift_code)  VALUES (:Id_Timeline , :Id_Position, :Number, :Time_start, :Is_OT, :Time_end,:Shift_code )", nativeQuery = true)
    void CreateNewShift(@PathVariable("Id_Timeline") int Id_Timeline, @PathVariable("Id_Position") int Id_Position, @PathVariable("Number") int Number, @PathVariable("Time_start") Date Time_start, @PathVariable("Is_OT") boolean Is_OT, @PathVariable("Time_end") Date Time_end, @PathVariable("Shift_code") int Shift_code);
}

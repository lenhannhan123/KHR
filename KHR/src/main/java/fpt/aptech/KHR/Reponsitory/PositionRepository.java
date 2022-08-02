/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Admin
 */
public interface PositionRepository extends JpaRepository<Position, Integer> {

    @Query("SELECT p FROM Position p WHERE p.id = :id")
    Position findID(@PathVariable("id") int id);

    @Query(value = "SELECT  COUNT(p.id ) FROM Position p ", nativeQuery = true)
    int CouPosition();


}

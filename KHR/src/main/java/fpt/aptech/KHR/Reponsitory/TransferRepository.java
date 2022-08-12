/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.Notification;
import fpt.aptech.KHR.Entities.TransferData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Admin
 */
public interface TransferRepository extends JpaRepository<TransferData, Integer> {

    @Query("SELECT n FROM TransferData n WHERE n.id = :id")
    TransferData findById(@PathVariable("id") int id);
    
}

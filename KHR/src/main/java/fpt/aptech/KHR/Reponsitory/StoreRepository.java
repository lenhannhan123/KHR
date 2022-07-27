/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Admin
 */
public interface StoreRepository extends JpaRepository<Store, Integer> {

    @Query("SELECT s FROM Store s WHERE s.id = :id")
    Shift findID(@PathVariable("id") int id);

}

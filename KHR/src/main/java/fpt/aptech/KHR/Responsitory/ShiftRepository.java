/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Responsitory;

import fpt.aptech.KHR.Entities.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Admin
 */
public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    
}

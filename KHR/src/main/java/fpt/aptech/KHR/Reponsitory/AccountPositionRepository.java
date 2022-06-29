/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.AccountPosition;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jthie
 */
public interface AccountPositionRepository extends JpaRepository<AccountPosition, Integer> {
    
}

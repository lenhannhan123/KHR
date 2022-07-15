/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountPosition;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author jthie
 */
public interface AccountPositionRepository extends JpaRepository<AccountPosition, Integer> {
    @Query("SELECT a FROM AccountPosition a WHERE a.mail = :mail")
    List<AccountPosition> findByEmail(@PathVariable("mail") Account mail);
    
    @Query("SELECT a FROM AccountPosition a WHERE a.mail = :mail AND a.idPosition = :idPosition")
    AccountPosition findByMailAndPosition(@PathVariable("mail") Account mail, @PathVariable("idPosition") int idPosition);
    
    
}

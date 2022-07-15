/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountPosition;
import java.util.List;

/**
 *
 * @author jthie
 */
public interface IAccountPositionServices {

    List<AccountPosition> findAll();
    
    void save(AccountPosition accountPosition);
    
    List<AccountPosition> findByEmail(Account mail);
    
    void delete(AccountPosition accountPosition);
    
    AccountPosition findByMailAndPosition(Account mail, int id);
}

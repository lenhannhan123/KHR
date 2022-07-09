/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountPosition;
import fpt.aptech.KHR.Reponsitory.AccountPositionRepository;
import fpt.aptech.KHR.Services.IAccountPositionServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jthie
 */
@Service
public class AccountPositionService implements IAccountPositionServices {
    @Autowired
    private AccountPositionRepository accountPositionRepository;
    @Override
    public List<AccountPosition> findAll() {
        return accountPositionRepository.findAll();
    }

    @Override
    public void save(AccountPosition accountPosition) {
        accountPositionRepository.save(accountPosition);
    }

    @Override
    public List<AccountPosition> findByEmail(Account account) {
        return accountPositionRepository.findByEmail(account);
    }

    @Override
    public void delete(AccountPosition accountPosition) {
        accountPositionRepository.delete(accountPosition);
    }
    
}

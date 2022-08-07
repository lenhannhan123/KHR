/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Store;
import fpt.aptech.KHR.Reponsitory.AccountRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jthie
 */
@Service
public class AccountServiceImp implements IAccountRepository {

    @Autowired
    private AccountRepository repository;

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(Account account) {
        repository.save(account);
    }

    @Override
    public void delete(Account account) {
        repository.delete(account);
    }

    @Override
    public Account findByMailAdmin(String mail) {
        return repository.findByEmailAdmin(mail);
    }

    @Override
    public Account findByMail(String mail) {
        return repository.findByEmail(mail);
    }

    @Override
    public List<Account> findAllAdminAccounts() {
        return null;
    }

    @Override
    public List<Account> findAllUserAccounts() {
        return null;
    }

    @Override
    public Account findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public List<Account> findByStore(Store id) {
        return repository.findByStore(id);
    }

    @Override
    public Account findByMailUser(String mail) {
        return repository.findByEmailUser(mail);
    }

    @Override
    public boolean checkOldPassword(String mail, String password) {
        if (repository.checkOldPass(mail, password) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkRecoveryCode(String mail, String recoverycode) {
        if (repository.checkRecoveryCode(mail, recoverycode) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updatePassword(String password, String mail) {
        repository.updatePassword(password, mail);
    }

    @Override
    public void updateRecoveryCode(String recoverycode, String mail) {
        repository.updateRecoveryCode(recoverycode, mail);
    }

    @Override
    public List<Account> listAll(String keyword) {
        if (keyword != null) {
            return repository.search(keyword);
        }
        return repository.findAll();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Reponsitory.AccountRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jthie
 */
@Service
public class AccountServiceImp implements IAccountRepository{
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
}

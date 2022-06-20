/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Account;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author jthie
 */
public interface IAccountRepository {

    @Autowired
    public List<Account> findAll();

    public void save(Account account);

    public void delete(Account camera);

    public Account findByMail(String mail);

    public List<Account> findAllAdminAccounts();

    public List<Account> findAllUserAccounts();
}

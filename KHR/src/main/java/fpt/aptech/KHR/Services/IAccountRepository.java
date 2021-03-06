/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Account;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author jthie
 */
public interface IAccountRepository {

    @Autowired
    public List<Account> findAll();

    public void save(Account account);

    public void delete(Account account);

    public Account findByMail(String mail);

    public Account findByMailAdmin(String mail);
    
    public Account findByMailUser(String mail);

    public List<Account> findAllAdminAccounts();

    public List<Account> findAllUserAccounts();

    public Account findByCode(String code);
    
}

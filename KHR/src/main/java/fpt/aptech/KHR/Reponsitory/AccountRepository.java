/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.Account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author jthie
 */
public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("SELECT a FROM Account a WHERE a.mail = :mail")
    Account findByEmail(@PathVariable("mail") String mail);

    @Query("SELECT a FROM Account a WHERE a.mail = :mail AND a.role = 1")
    Account findByEmailAdmin(@PathVariable("mail") String mail);

    @Query("SELECT a FROM Account a WHERE a.mail = :mail AND a.role = 0")
    Account findByEmailUser(@PathVariable("mail") String mail);

    @Query("SELECT a FROM Account a WHERE  a.role = 0")
    List<Account> findAllUser();

    @Query("SELECT a FROM Account a WHERE a.code = :code")
    Account findByCode(@PathVariable("code") String code);
    
    @Query("SELECT a FROM Account a WHERE a.mail = :mail AND a.password = :password")
    Account loginAccount(@PathVariable("mail") String mail, @PathVariable("password") String password);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.Account;

import java.util.List;

import fpt.aptech.KHR.Entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author jthie
 */
public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("SELECT a FROM Account a WHERE a.mail = :mail")
    Account findByEmail(@PathVariable("mail") String mail);

    @Query("SELECT a FROM Account a WHERE a.idStore = :idStore")
    List<Account> findByStore(@PathVariable("idStore") Store idStore);

    @Query("SELECT a FROM Account a WHERE a.mail = :mail AND a.role = 1 AND a.status = 1")
    Account findByEmailAdmin(@PathVariable("mail") String mail);

    @Query("SELECT a FROM Account a WHERE a.mail = :mail AND a.role = 0 AND a.status = 1")
    Account findByEmailUser(@PathVariable("mail") String mail);

    @Query("SELECT a FROM Account a WHERE  a.role = 0")
    List<Account> findAllUser();

    @Query("SELECT a FROM Account a WHERE a.code = :code")
    Account findByCode(@PathVariable("code") String code);

    @Query("SELECT a FROM Account a WHERE a.mail = :mail AND a.password = :password")
    Account loginAccount(@PathVariable("mail") String mail, @PathVariable("password") String password);

    @Query("SELECT a FROM Account a WHERE a.mail = :mail AND a.password = :password")
    Account checkOldPass(String mail, String password);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Account a SET a.password = :password WHERE a.mail = :mail")
    void updatePassword(@Param("password") String password, @Param("mail") String mail);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Account a SET a.recoverycode = :recoverycode WHERE a.mail = :mail")
    void updateRecoveryCode(@Param("recoverycode") String recoverycode, @Param("mail") String mail);
}

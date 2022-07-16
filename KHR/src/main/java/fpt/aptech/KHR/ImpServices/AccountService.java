/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.UserTimeline;
import fpt.aptech.KHR.Reponsitory.AccountRepository;
import fpt.aptech.KHR.Services.IAccountRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author jthie
 */
@Service
public class AccountService implements UserDetailsService {

    private Long userId;
    private String username;
    private String password;
    private Collection authorities;
    @Autowired
    IAccountRepository repository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account adminaccount = repository.findByMailAdmin(username);
        Account useraccount = repository.findByMailUser(username);
        if (adminaccount != null) {
            List<GrantedAuthority> grantList = new ArrayList<>();
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
            grantList.add(authority);
            UserDetails userDetails = new User(adminaccount.getMail(), adminaccount.getPassword(), grantList);
            return userDetails;
        } else {
            new UsernameNotFoundException("Login failed!");
        }
        return null;

    }

    public List<Account> findAll() {
        return repository.findAll();
    }

    public List<Account> findAllUser() {
        return accountRepository.findAllUser();
    }

    public Account findByMail(String mail) {
        return accountRepository.findByEmail(mail);
    }

    public Account loginAccount(String mail, String password) {
        return accountRepository.loginAccount(mail, password);
    }

}

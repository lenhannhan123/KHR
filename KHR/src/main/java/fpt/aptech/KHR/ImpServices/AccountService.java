/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Services.IAccountRepository;
import java.util.ArrayList;
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
 *
 * @author jthie
 */
@Service
public class AccountService implements UserDetailsService {

    @Autowired
    IAccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repository.findByMailAdmin(username);
        if (account != null) {
            List<GrantedAuthority> grantList = new ArrayList<>();
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
            grantList.add(authority);
            UserDetails userDetails = new User(account.getMail(), account.getPassword(), grantList);
            return userDetails;
        } else {
            new UsernameNotFoundException("Login failed!");
        }
        return null;

    }
}

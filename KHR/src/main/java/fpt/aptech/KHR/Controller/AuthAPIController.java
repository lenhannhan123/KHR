/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.ImpServices.AccountService;
import fpt.aptech.KHR.Services.AccountServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Admin
 */
@Controller
public class AuthAPIController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountServiceImp accountServiceImp;


    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                account.getMail(), account.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}

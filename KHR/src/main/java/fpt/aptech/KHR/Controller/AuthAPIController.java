/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.ImpServices.AccountService;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.Routes.RouteAPI;
import fpt.aptech.KHR.Services.AccountServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @RequestMapping(value = RouteAPI.CheckAuth, method = RequestMethod.GET)
    public void authenticateUser(Model model, @RequestParam("mail") String mail, @RequestParam("password") String password,
                                 HttpServletRequest request, HttpServletResponse response) {
        Account account = new Account();
        account.setMail(mail);
        account.setPassword(password);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                account.getMail(), account.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JsonServices.dd("User signed-in successfully!.", response);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.Services.IAccountRepository;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.AccountServiceImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.System.out;

/**
 *
 * @author jthie
 */
@Controller
public class AccountController {

    @Autowired
    private IAccountRepository accountRepository;
    private AccountServiceImp accountServiceImp;

    @RequestMapping(value = {RouteWeb.accountManageURL}, method = RequestMethod.GET)
    public String AccountList(Model model) {
        List<Account> list = accountRepository.findAll();
        boolean check = false;
        for (Account item : list) {
            if (item.getMail() != null) {

                check = true;
                break;
            }
        }
        model.addAttribute("list", list);
        model.addAttribute("check", check);
        return "/account/index";
    }

    @RequestMapping(value = {RouteWeb.AccountGetCreateURL}, method = RequestMethod.GET)
    public String GetCreate(Model model) {
        return "/account/create";
    }

    @RequestMapping(value = {RouteWeb.AccountGetCreateURL}, method = RequestMethod.POST)
    public String PostCreate(Model model, HttpServletRequest request, HttpServletResponse response) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String mail = request.getParameter("txtAccountMail");
        String name = request.getParameter("txtFullName");
        String phone = request.getParameter("txtPhone");
        boolean gender = Boolean.parseBoolean(request.getParameter("radioGender"));
        String strBday = request.getParameter("txtBirthDay");
        Date bday = null;
        try {
            bday = new SimpleDateFormat("yyyy-mm-dd").parse(strBday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
//        String code = codeGenerator();
//        String code = "A103";
//        Account checkCode = accountServiceImp.findByCode(code);
//        if(checkCode!=null){
//            out.println("true");
//        }
//        else{
//            out.println("false");
//        }
        Account account = new Account(mail, encoder.encode("123"), name, phone, bday, gender,encoder.encode(mail),  (short)1, true);

        accountRepository.save(account);
        String redirectUrl = "/account/index";
        return "redirect:" + redirectUrl;
    }

    public String codeGenerator() {
        double random = 1000 + Math.random() * 10000;
        String code = "KHR" + (int) random;
        return code;
    }

}

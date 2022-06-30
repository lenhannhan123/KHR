/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountPosition;
import fpt.aptech.KHR.Entities.Position;
import fpt.aptech.KHR.Entities.PositionJS;
import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.ImpServices.PositionServices;
import fpt.aptech.KHR.Reponsitory.AccountPositionRepository;
import fpt.aptech.KHR.Services.IAccountRepository;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.AccountServiceImp;
import fpt.aptech.KHR.Services.IAccountPositionServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fpt.aptech.KHR.Services.IPositionServices;
import static java.lang.System.out;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author jthie
 */
@Controller
public class AccountController {

    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private AccountServiceImp accountServiceImp;
    @Autowired
    private PositionServices positionServices;
    @Autowired
    private AccountPositionRepository accountPositionRepository;

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
        List<Position> positions = positionServices.findAll();
        model.addAttribute("positions", positions);
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
        short role = Short.parseShort(request.getParameter("txtRole"));

        Account account = new Account(mail, encoder.encode("123"), name, phone, bday, gender, encoder.encode(mail), role, true);

        accountRepository.save(account);
        List<Position> positions = positionServices.findAll();
        for (int i = 0; i < positions.size(); i++) {
            if (request.getParameter("check" + i) != null) {
                AccountPosition accountPosition = new AccountPosition();
                accountPosition.setIdPosition(new Position(Integer.parseInt(request.getParameter("check"+i).toString())));
                accountPosition.setMail(new Account(mail));
                accountPosition.setSalary(Integer.parseInt(request.getParameter("checkvalue" + i).toString()));
                accountPositionRepository.save(accountPosition);
            }
        }
        String redirectUrl = "/account/index";
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value = {RouteWeb.AccountGetUpdateURL}, method = RequestMethod.GET)
    public String GetUpdate(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Account account = accountRepository.findByMail(id);
        request.setAttribute("Account", account);
        model.addAttribute("Account",account);
        return "/account/update";
    }

}

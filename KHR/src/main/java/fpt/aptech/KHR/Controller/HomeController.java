/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.ImpServices.OverTimeService;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Admin
 */
@Controller
public class HomeController {

    @Autowired
    private IAccountRepository accountRepository;

    @RequestMapping(value = {RouteWeb.AdminHomeURL}, method = RequestMethod.GET)
    public String Index(Model model, HttpServletRequest request, HttpServletResponse response) {

        String idUser = request.getRemoteUser();
        Account account = accountRepository.findByMail(idUser);

        if (account.getStatus() == false) {
            return "redirect:/logout";
        }


        switch (account.getRole()) {
            case "0":
                return "redirect:/logout";
            case "1":

                HttpSession session = request.getSession();
                session.setAttribute("IdStore", account.getIdStore().getId());
                return "index";
            case "2":
                return "redirect:/logout";
            case "3":
                return "Boss/bosshome";
        }


        return "index";

    }

    @RequestMapping(value = {RouteWeb.index1URL}, method = RequestMethod.GET)
//    @GetMapping("/login")
    public String Login(Model model) {

        return "login";
    }


}

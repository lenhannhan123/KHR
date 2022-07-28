/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountNotification;
import fpt.aptech.KHR.ImpServices.AccountService;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.ImpServices.NotificationService;
import fpt.aptech.KHR.Routes.RouteWeb;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author LÊ HỮU TÂM
 */
@Controller
public class NotificationController {
    @Autowired
    NotificationService ns;
    @Autowired
    AccountService acs;
    @RequestMapping(value = {RouteWeb.notificationURL}, method = RequestMethod.GET)
    public String Index(Model model) {
        List<AccountNotification> list = ns.findAllNotification();
        boolean check = false;
        for (AccountNotification item : list) {
            if (item.getId() != null) {
                check = true;
                break;
            }
        }
        model.addAttribute("notificationList", list);
        model.addAttribute("check", check);
        return "admin/notification/index";
    }
    @RequestMapping(value = {RouteWeb.notificationAddURL}, method = RequestMethod.GET)
    public String AddPage(Model model) {
         model.addAttribute("accountList", JsonServices.ParseToJson(acs.findAll()));
         model.addAttribute("accountsize",acs.findAll().size());
        return "admin/notification/add";
    }
    @RequestMapping(value = {"/notification/create"}, method = RequestMethod.GET)
    public String AddNotification(Model model,HttpServletRequest request, HttpServletResponse response) {
       
        return "admin/notification/add";
    }
//    @RequestMapping(value = {"/notification/mail"}, method = RequestMethod.GET)
//    public String FindNotificationByMail(Model model,HttpServletRequest request, HttpServletResponse response) {
//        String mail = request.getParameter("mail");
//        Account account = acs.findByMail(mail);
//       JsonServices.dd(JsonServices.ParseToJson(ns.findbyAccount(account)), response);
//        return "admin/notification/add";
//    }
//    @RequestMapping(value = {"api/notification/mail"}, method = RequestMethod.GET)
//    public ResponseEntity<List<AccountNotification>> APINotificationByMail(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            String mail = request.getParameter("mail");
//        Account account = acs.findByMail(mail);
//        List<AccountNotification> list = ns.findbyAccount(account);
//        if (list!=null) {
//            return new ResponseEntity<List<AccountNotification>>(list, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
//        }
//    }
    
    
}

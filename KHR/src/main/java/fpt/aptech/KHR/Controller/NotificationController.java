/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountNotification;
import fpt.aptech.KHR.Entities.AccountToken;
import fpt.aptech.KHR.Entities.Notification;
import fpt.aptech.KHR.ImpServices.AccountService;
import fpt.aptech.KHR.ImpServices.FirebaseMessagingService;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.ImpServices.NotificationService;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.IAccountToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    IAccountToken accToken;
    @Autowired
    FirebaseMessagingService firebaseMessagingService;
//    private final FirebaseMessagingService firebaseService;
//
//    public NotificationController(FirebaseMessagingService firebaseService) {
//        this.firebaseService = firebaseService;
//    }

    @RequestMapping(value = {RouteWeb.notificationURL}, method = RequestMethod.GET)
    public String Index(Model model, HttpServletRequest request, HttpServletResponse response) {
        //List<Notification> list = ns.findAll();
        List<Notification> listlike = new ArrayList<>();
        List<Notification> listend = new ArrayList<>();
        List<AccountNotification> listnotifacation = ns.findAllNotification();
        HttpSession session = request.getSession();
        int IdStore = Integer.parseInt(session.getAttribute("IdStore").toString());
        for (int i = listnotifacation.size() - 1; i >= 0; i--) {
//            for (int j = listlike.size() - 1; j >= 0; j--) {
//                if (listlike.get(j).getId() == listnotifacation.get(i).getId()) {
//                    if (listnotifacation.get(i).getMail().getIdStore().getId() == IdStore) {
//                        listlike.add(listnotifacation.get(i).getIdnotification());
//                    }
//                }
//            }
            if (listnotifacation.get(i).getMail().getIdStore().getId() == IdStore) {
                listlike.add(listnotifacation.get(i).getIdnotification());
            }
        }
        for (int i = 0; i < listlike.size(); i++) {
            if (!listend.contains(listlike.get(i))) {
                listend.add(listlike.get(i));
            }
        }
        boolean check = false;
        for (Notification item : listlike) {
            if (item.getId() != null) {
                check = true;
                break;
            }
        }
        model.addAttribute("notificationList", listend);
        model.addAttribute("check", check);
        return "admin/notification/index";
    }

    @RequestMapping(value = {"/notification/details"}, method = RequestMethod.GET)
    public String Details(Model model, HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.valueOf(request.getParameter("id"));
        List<AccountNotification> list = ns.findNotificationByAny(id);
        String content = null;
        boolean check = false;
        for (AccountNotification item : list) {
            if (item.getId() != null) {
                content = item.getIdnotification().getContent();
                check = true;
                break;
            }
        }
        model.addAttribute("ifomation", content);
        model.addAttribute("notificationaccountList", list);
        model.addAttribute("check", check);
        return "admin/notification/details";
    }

    @RequestMapping(value = {RouteWeb.notificationAddURL}, method = RequestMethod.GET)
    public String AddPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<Account> accounts = acs.findAll();
        HttpSession session = request.getSession();
        List<Account> list = new ArrayList<>();
        int IdStore = Integer.parseInt(session.getAttribute("IdStore").toString());
        for (Account account : accounts) {
            if (account.getRole().equals("0")) {
                if (account.getIdStore().getId() == IdStore) {
                    list.add(account);
                }
            }

        }
        model.addAttribute("accountList", JsonServices.ParseToJson(list));
        model.addAttribute("accountsize", list.size());
        return "admin/notification/add";
    }

    @RequestMapping(value = {"/notification/save"}, method = RequestMethod.POST)
    public String AddNotification(Model model, HttpServletRequest request, HttpServletResponse response) throws FirebaseMessagingException {
        String title = request.getParameter("NotificationName");
        String content = request.getParameter("NotificationContent");
        String typesend = request.getParameter("TypeSend");
        String[] user = request.getParameterValues("UserTo");
        //JsonServices.dd(JsonServices.ParseToJson(user), response);
        Notification n = new Notification();
        Date date = new Date();
        n.setTitle(title);
        n.setContent(content);
        n.setDateCreate(date);
        Notification ni = ns.AddNotification(n);
        Notification check = new Notification();
        check.setId(ni.getId());
        check.setTitle(ni.getTitle());
        check.setContent(ni.getContent());
        check.setDateCreate(ni.getDateCreate());
        if (typesend.equals("any")) {
            List<String> listtokenstring = new ArrayList<>();
            for (int i = 0; i < user.length; i++) {
                Account account = acs.findByMail(user[i]);
                List<AccountToken> listToken = accToken.GetTokenByMail(user[i]);

                for (AccountToken accountToken : listToken) {
                    listtokenstring.add(accountToken.getToken());
                }
                AccountNotification accountNotification = new AccountNotification();
                accountNotification.setIdnotification(check);
                accountNotification.setMail(account);
                accountNotification.setStatus(false);
                ns.AddAccountNotification(accountNotification);
            }
            firebaseMessagingService.sendAllPeople(ni, listtokenstring);
        } else if (typesend.equals("all")) {

            List<Account> accounts = acs.findAll();
            HttpSession session = request.getSession();
            List<Account> list = new ArrayList<>();
            int IdStore = Integer.parseInt(session.getAttribute("IdStore").toString());
            for (Account account : accounts) {
                if (account.getRole().equals("0")) {
                    if (account.getIdStore().getId() == IdStore) {
                        list.add(account);
                    }
                }

            }
            List<String> listtokenstring = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Account account = acs.findByMail(list.get(i).getMail());
                List<AccountToken> listToken = accToken.GetTokenByMail(list.get(i).getMail());

                for (AccountToken accountToken : listToken) {
                    listtokenstring.add(accountToken.getToken());
                }
                AccountNotification accountNotification = new AccountNotification();
                accountNotification.setIdnotification(check);
                accountNotification.setMail(account);
                accountNotification.setStatus(false);
                ns.AddAccountNotification(accountNotification);
            }
            firebaseMessagingService.sendAllPeople(check, listtokenstring);

        }
        return "redirect:/notification";
    }
//    @RequestMapping(value = {"/notification/send/token"}, method = RequestMethod.GET)
//    public String FindNotificationByMail(Model model,HttpServletRequest request, HttpServletResponse response) {
//        String token = request.getParameter("token");
//       // Account account = acs.findByMail(mail);
//       //JsonServices.dd(JsonServices.ParseToJson(ns.findbyAccount(account)), response);
//        return "admin/notification/add";
//    }

    @RequestMapping(value = {"api/notification/token"}, method = RequestMethod.GET)
    public ResponseEntity<List<AccountNotification>> APINotificationByMail(HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            String mail = request.getParameter("token");
            AccountToken atk = accToken.GetToken(mail);
            List<AccountNotification> list = ns.findbyAccount(atk.getMail().getMail());
            List<AccountNotification> listdayoff = new ArrayList<AccountNotification>();
            for (int i = list.size() - 1; i >= 0; i--) {
                listdayoff.add(list.get(i));
            }
            if (listdayoff != null) {
                return new ResponseEntity<List<AccountNotification>>(listdayoff, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = {"api/notification/token/search"}, method = RequestMethod.GET)
    public ResponseEntity<List<AccountNotification>> APINotificationSearchMail(HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            String mail = request.getParameter("token");
            String year = request.getParameter("year");
            AccountToken atk = accToken.GetToken(mail);
            List<AccountNotification> list = ns.findbyAccount(atk.getMail().getMail());
            List<AccountNotification> listbyyear = new ArrayList<AccountNotification>();

            if (list != null) {
                return new ResponseEntity<List<AccountNotification>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = {"api/notification/seen"}, method = RequestMethod.POST)
    public ResponseEntity<AccountNotification> APISeenNotifacation(@RequestBody AccountNotification accountNotification, HttpServletRequest request,
            HttpServletResponse response
    ) {
        // Account acc = request.getParameter("mail")
        try {
            AccountNotification accountNotificationnew = ns.Seen(accountNotification);
            if (accountNotificationnew != null) {
                return new ResponseEntity<AccountNotification>(accountNotificationnew, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
//    @RequestMapping(value = {"api/notification/demos"}, method = RequestMethod.GET)
//    @ResponseBody
//    public BatchResponse APINotifiByMail(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            List<AccountToken> listToken = accToken.GetTokenByMail("user1@gmail.com");
//            List<String> list = new ArrayList<>();
//            List<AccountNotification> nolist = ns.findNotificationByAny(1);
//            for (AccountToken accountToken : listToken) {
//                list.add(accountToken.getToken());
//            }
//            //JsonServices.dd(JsonServices.ParseToJson(list), response);
//            return firebaseMessagingService.sendMorePeople(nolist.get(1), list);    
//            //JsonServices.dd(JsonServices.ParseToJson(listToken), response);
//            
//        } catch (FirebaseMessagingException ex) {
//            Logger.getLogger(NotificationController.class.getName()).log(Level.SEVERE, null, ex);
//             return null;
//        }
//       
//    }

}

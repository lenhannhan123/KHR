/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.ImpServices.NotificationService;
import fpt.aptech.KHR.Routes.RouteWeb;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping(value = {RouteWeb.notificationURL}, method = RequestMethod.GET)
    public String Index(Model model) {
        model.addAttribute("notificationList", ns.findAll());
        return "notification/index";
    }
    @RequestMapping(value = {RouteWeb.notificationAddURL}, method = RequestMethod.GET)
    public String AddPage(Model model) {
        model.addAttribute("notificationList", ns.findAll());
        return "notification/add";
    }
    
    
}

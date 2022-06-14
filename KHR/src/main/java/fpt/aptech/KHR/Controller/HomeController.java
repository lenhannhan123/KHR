/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.ImpServices.OverTimeService;
import fpt.aptech.KHR.Routes.RouteWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Admin
 */
@Controller
public class HomeController {

    @RequestMapping(value = {RouteWeb.AdminHomeURL}, method = RequestMethod.GET)
    public String Index(Model model) {

        return "index";
    }
    @RequestMapping(value = {RouteWeb.index1URL}, method = RequestMethod.GET)
    public String Login(Model model) {

        return "login";
    }
}

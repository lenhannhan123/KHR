/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.ITimekeepingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author backs
 */
@Controller
public class TimekeepingController {
    
    @Autowired
    ITimekeepingServices timekeepingServices;
    
    @RequestMapping(value = {RouteWeb.TimekeepingIndexURL}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("list", timekeepingServices.findAll());
        return "timekeeping/index";
    }
    
//    @RequestMapping(value = {RouteWeb.TimekeepingIndexURL}, method = RequestMethod.GET)
//    public String checkin(Model model) {
//        model.addAttribute("list", timekeepingServices.findAll());
//        return "timekeeping/index";
//    }
    
}

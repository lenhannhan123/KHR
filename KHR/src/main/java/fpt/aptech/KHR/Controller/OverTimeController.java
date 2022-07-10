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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author LÊ HỮU TÂM
 */
@Controller
public class OverTimeController {
    @Autowired
    OverTimeService iots;
    @RequestMapping(value = {RouteWeb.overtimeURL}, method = RequestMethod.GET)
    public String Index(Model model) {
        model.addAttribute("overtimeList", iots.findAll());
        return "overtime/index";
    }
//    @RequestMapping(value = {RouteWeb.dayoffapproveURL}, method = RequestMethod.GET)
//    public String approved(@PathVariable int id, Model model) throws Exception {
//        iots.approve(id);
//        model.addAttribute("overtimeList", iots.findAll());
//        return "overtime/index";
//    }
//    @RequestMapping(value = {RouteWeb.dayoffdenyingURL}, method = RequestMethod.GET)
//    public String denying(@PathVariable int id, Model model) throws Exception {
//        iots.denying(id);
//        model.addAttribute("overtimeList", iots.findAll());
//        return "overtime/index";
//    }
    
}

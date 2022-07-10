/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.IDayOffServices;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class DayOffController {
    @Autowired
    IDayOffServices idos;
    @RequestMapping(value = {RouteWeb.dayoffURL}, method = RequestMethod.GET)
    public String Index(Model model) {
        model.addAttribute("listdateoff", idos.findAll());
        return "dayoff/index";
    }
    @RequestMapping(value = {RouteWeb.dayoffapproveURL}, method = RequestMethod.GET)
    public String approved(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.valueOf(request.getParameter("id"));
        idos.approve(id);
        String redirectUrl = "/dayoff";
        return "redirect:" + redirectUrl;
    }
    @RequestMapping(value = {RouteWeb.dayoffdenyingURL}, method = RequestMethod.GET)
    public String denying( Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.valueOf(request.getParameter("id"));
        idos.denying(id);
        String redirectUrl = "/dayoff";
        return "redirect:" + redirectUrl;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.DayOff;
import fpt.aptech.KHR.Entities.OverTime;
import fpt.aptech.KHR.ImpServices.OverTimeService;
import fpt.aptech.KHR.Routes.RouteWeb;
import java.util.List;
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
public class OverTimeController {

    @Autowired
    OverTimeService iots;

    @RequestMapping(value = {RouteWeb.overtimeURL}, method = RequestMethod.GET)
    public String Index(Model model,HttpServletRequest request, HttpServletResponse response) {
        List<OverTime> list = iots.findAll();
//        JsonServices.dd(JsonServices.ParseToJson(list), response);
//        JsonServices.ParseToJson(list);
        boolean check = false;
        for (OverTime item : list) {
            if (item.getMail() != null) {
                check = true;
                break;
            }
        }
        model.addAttribute("list", list);
        model.addAttribute("check", check);
        return "admin/overtime/index";
    }
  @RequestMapping(value = {"/overtime/list/approved"}, method = RequestMethod.GET)
    public String ApprovedList(Model model,HttpServletRequest request, HttpServletResponse response) {
        List<OverTime> list = iots.findStatusApproved();
//        JsonServices.dd(JsonServices.ParseToJson(list), response);
//        JsonServices.ParseToJson(list);
        boolean check = false;
        for (OverTime item : list) {
            if (item.getMail() != null ) {
                check = true;
                break;
            }
        }
        model.addAttribute("list", list);
        model.addAttribute("check", check);
        return "admin/overtime/index";
    }
            @RequestMapping(value = {"/overtime/list/denying"}, method = RequestMethod.GET)
    public String DenyingList(Model model,HttpServletRequest request, HttpServletResponse response) {
        List<OverTime> list = iots.findStatusDenying();
//        JsonServices.dd(JsonServices.ParseToJson(list), response);
//        JsonServices.ParseToJson(list);
        boolean check = false;
        for (OverTime item : list ) {
            if (item.getMail() != null) {
                check = true;
                break;
            }
        }
        model.addAttribute("list", list);
        model.addAttribute("check", check);
        return "admin/overtime/index";
    }
    @RequestMapping(value = {"/overtime/list/notcheck"}, method = RequestMethod.GET)
    public String NotCheckList(Model model,HttpServletRequest request, HttpServletResponse response) {
        List<OverTime> list = iots.findStatusNotCheck();
//        JsonServices.dd(JsonServices.ParseToJson(list), response);
//        JsonServices.ParseToJson(list);
        boolean check = false;
        for (OverTime item : list ) {
            if (item.getMail() != null) {
                check = true;
                break;
            }
        }
        model.addAttribute("list", list);
        model.addAttribute("check", check);
        return "admin/overtime/index";
    }
    @RequestMapping(value = {RouteWeb.overtimeapproveURL}, method = RequestMethod.GET)
    public String approved(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.valueOf(request.getParameter("id"));
        iots.approve(id);
        String redirectUrl = "/overtime";
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value = {RouteWeb.overtimedenyingURL}, method = RequestMethod.GET)
    public String denying(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.valueOf(request.getParameter("id"));
        iots.denying(id);
        String redirectUrl = "/overtime";
        return "redirect:" + redirectUrl;
    }

}

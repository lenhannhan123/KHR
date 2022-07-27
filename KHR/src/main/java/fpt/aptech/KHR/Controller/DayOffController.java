/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.DayOff;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.IDayOffServices;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String Index(Model model,HttpServletRequest request, HttpServletResponse response) {
        List<DayOff> list = idos.findAll();
//        JsonServices.dd(JsonServices.ParseToJson(list), response);
//        JsonServices.ParseToJson(list);
        boolean check = false;
        for (DayOff item : list) {
            if (item.getMail() != null) {
                check = true;
                break;
            }
        }
        model.addAttribute("listdateoff", list);
        model.addAttribute("check", check);
        return "admin/dayoff/index";
    }
        @RequestMapping(value = {"/dayoff/list/approved"}, method = RequestMethod.GET)
    public String ApprovedList(Model model,HttpServletRequest request, HttpServletResponse response) {
        List<DayOff> list = idos.findApproveList();
//        JsonServices.dd(JsonServices.ParseToJson(list), response);
//        JsonServices.ParseToJson(list);
        boolean check = false;
        for (DayOff item : list) {
            if (item.getMail() != null ) {
                check = true;
                break;
            }
        }
        model.addAttribute("listdateoff", list);
        model.addAttribute("check", check);
        return "admin/dayoff/index";
    }
            @RequestMapping(value = {"/dayoff/list/denying"}, method = RequestMethod.GET)
    public String DenyingList(Model model,HttpServletRequest request, HttpServletResponse response) {
        List<DayOff> list = idos.findDenyingList();
//        JsonServices.dd(JsonServices.ParseToJson(list), response);
//        JsonServices.ParseToJson(list);
        boolean check = false;
        for (DayOff item : list ) {
            if (item.getMail() != null) {
                check = true;
                break;
            }
        }
        model.addAttribute("listdateoff", list);
        model.addAttribute("check", check);
        return "admin/dayoff/index";
    }
    @RequestMapping(value = {"/dayoff/list/notcheck"}, method = RequestMethod.GET)
    public String NotCheckList(Model model,HttpServletRequest request, HttpServletResponse response) {
        List<DayOff> list = idos.findNotCheck();
//        JsonServices.dd(JsonServices.ParseToJson(list), response);
//        JsonServices.ParseToJson(list);
        boolean check = false;
        for (DayOff item : list ) {
            if (item.getMail() != null) {
                check = true;
                break;
            }
        }
        model.addAttribute("listdateoff", list);
        model.addAttribute("check", check);
        return "admin/dayoff/index";
    }
    @RequestMapping(value = {RouteWeb.dayoffapproveURL}, method = RequestMethod.GET)
    public String approved(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.valueOf(request.getParameter("id"));
        idos.approve(id);
        String redirectUrl = "dayoff";
        return "redirect:" + redirectUrl;
    }
    @RequestMapping(value = {RouteWeb.dayoffdenyingURL}, method = RequestMethod.GET)
    public String denying( Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.valueOf(request.getParameter("id"));
        idos.denying(id);
        String redirectUrl = "dayoff";
        return "redirect:" + redirectUrl;
    }
    @RequestMapping(value = {"api/dayoff/add"}, method = RequestMethod.POST)
    public ResponseEntity<DayOff> APIAddDayOff(@RequestBody DayOff dayOff ) {
        try {
        DayOff df = idos.AddDayOff(dayOff);
       
        if (df!=null) {
            return new ResponseEntity<DayOff>(df, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    
}

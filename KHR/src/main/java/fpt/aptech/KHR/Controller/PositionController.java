/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Position;
import fpt.aptech.KHR.ImpServices.PositionServices;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.IAccountRepository;
import static java.lang.System.out;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.spring.web.json.Json;

/**
 *
 * @author jthie
 */
@Controller
public class PositionController {

    @Autowired
    private PositionServices positionServices;

    @RequestMapping("position/index")
    public String PositionList(Model model,HttpServletRequest request) {
        request.setAttribute("sidebar","3");

        List<Position> list = positionServices.findAll();
        boolean check = false;
        for (Position item : list) {
            if (item.getId() != null) {
                check = true;
                break;
            }
        }
        model.addAttribute("list", list);
        model.addAttribute("check", check);
        return "admin/position/index";
    }

    @RequestMapping(value = {RouteWeb.PositionGetCreateURL}, method = RequestMethod.GET)
    public String GetCreate(Model model,HttpServletRequest request) {
        request.setAttribute("sidebar","3");
        return "admin/position/create";
    }

    @RequestMapping(value = {RouteWeb.PositionGetCreateURL}, method = RequestMethod.POST)
    public String PostCreate(Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        request.setAttribute("sidebar","3");
        String positionName = request.getParameter("txtPositionName");
        String currency = request.getParameter("txtSalaryDefault");
        String newStr = currency.replace("vnd", "").replace(",", "").replace(" ", "");
        Position position = new Position(positionName,Integer.parseInt(newStr));
        positionServices.Create(position);
        String redirectUrl = "/position/index";
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value = {RouteWeb.PositionGetUpdateURL}, method = RequestMethod.GET)
    public String GetUpdate(Model model, HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("sidebar","3");
        Position position = positionServices.FindOne(id);
        model.addAttribute("Position", position);
        return "admin/position/update";
    }

    @RequestMapping(value = {RouteWeb.PositionGetUpdateURL}, method = RequestMethod.POST)
    public String PostUpdate(Model model, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("sidebar","3");
        int id = Integer.parseInt(request.getParameter("txtPositionId"));
        String positionName = request.getParameter("txtPositionName");
        String currency = request.getParameter("txtSalaryDefault");
        String newStr = currency.replace("vnd", "").replace(",", "").replace(" ", "");
        Position position = positionServices.FindOne(id);
        position.setPositionname(positionName);
        position.setSalarydefault(Integer.parseInt(newStr));
        positionServices.save(position);
        String redirectUrl = "/position/index";
        return "redirect:" + redirectUrl;
    }

}

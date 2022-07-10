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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author jthie
 */
@Controller
public class PositionController {

    @Autowired
    private PositionServices positionServices;

    @RequestMapping("/position/index")
    public String PositionList(Model model) {
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
        return "/position/index";
    }
    
    @RequestMapping(value = {RouteWeb.PositionGetCreateURL}, method = RequestMethod.GET)
    public String GetCreate(Model model){
        return "/position/create";
    }
    
    @RequestMapping(value = {RouteWeb.PositionGetCreateURL}, method = RequestMethod.POST)
    public String PostCreate(Model model, HttpServletRequest request, HttpServletResponse response){
        String positionName = request.getParameter("txtPositionName");
        int salaryDefault = Integer.parseInt(request.getParameter("txtSalaryDefault"));
        Position position = new Position(positionName,salaryDefault);
        positionServices.Create(position);
        String redirectUrl = "/position/index";
        return "redirect:" + redirectUrl;
    }
    
    @RequestMapping(value = {RouteWeb.PositionGetUpdateURL}, method = RequestMethod.GET)
    public String GetUpdate(Model model, HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        Position position = positionServices.FindOne(id);
        model.addAttribute("Position", position);
        return "/position/update";
    }
    
    @RequestMapping(value = {RouteWeb.PositionGetUpdateURL}, method = RequestMethod.POST)
    public String PostUpdate(Model model, HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("txtPositionId"));
        String positionName = request.getParameter("txtPositionName");
        int salaryDefault = Integer.parseInt(request.getParameter("txtSalaryDefault"));
        Position position = positionServices.FindOne(id);
        position.setPositionname(positionName);
        position.setSalarydefault(salaryDefault);
        positionServices.save(position);
        String redirectUrl = "/position/index";
        return "redirect:" + redirectUrl;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Routes.RouteWeb;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Admin
 */
@Controller
public class HomeController {
    
    @RequestMapping(RouteWeb.index)
    public String page(Model model) {
        
        return "index";
    }


      @RequestMapping("/index")
    public String page1(Model model) {
        
        return "layout/layout";
    }
    
}

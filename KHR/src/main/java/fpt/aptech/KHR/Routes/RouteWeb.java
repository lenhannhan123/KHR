/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Routes;

import fpt.aptech.KHR.Controller.AccountController;
import fpt.aptech.KHR.Controller.HomeController;
import fpt.aptech.KHR.Entities.ModelDemo;
import fpt.aptech.KHR.Entities.Notification;
import fpt.aptech.KHR.Entities.RouteModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Admin
 */
public class RouteWeb {

    ModelDemo model = new ModelDemo();

//    Khu vuc new Model
    HomeController homeController = new HomeController();
    AccountController accountController = new AccountController();

//    Khu vuc khai bao Route    
    public final static String indexURL = "/index";
    String IndexControler = homeController.AdminHome(model.model);

    public final static String index1URL = "/";
    String Index1Controler = homeController.Index(model.model);

    public final static String accountManageURL = "/account/index";
    String AccountControllerString = accountController.Index(model.model);

}

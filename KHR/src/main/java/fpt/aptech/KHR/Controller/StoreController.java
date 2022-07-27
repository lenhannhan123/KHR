/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Store;
import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.ImpServices.AccountService;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.ImpServices.StoreService;
import fpt.aptech.KHR.ImpServices.TimelineServices;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.AccountServiceImp;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Admin
 */
@Controller
public class StoreController {
    @Autowired
    StoreService storeService;

    @Autowired
    AccountServiceImp accountService;


    @RequestMapping(value = {RouteWeb.BossStoreIndex}, method = RequestMethod.GET)
    public String GetCofirm(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<Store> store = storeService.FindAl();


        boolean check = false;

        for (Store item : store) {
            if (item.getId() != null) {

                check = true;
                break;
            }
        }


        model.addAttribute("check", check);

        model.addAttribute("list", store);

        return "Boss/store/index";

    }

    @RequestMapping(value = {RouteWeb.BossStoreCreate}, method = RequestMethod.GET)
    public String GetCreate(Model model, HttpServletRequest request, HttpServletResponse response) {

        return "Boss/store/create";
    }

    @RequestMapping(value = {RouteWeb.BossStoreCreate}, method = RequestMethod.POST)
    public String PostCreate(Model model, HttpServletRequest request, HttpServletResponse response) {

        String StoreName = request.getParameter("Storename");
        String StoreAdress = request.getParameter("Storeaddresss");

        Store store = new Store();
        store.setNameStore(StoreName);
        store.setAddress(StoreAdress);
        store.setIsBlock(0);


        storeService.Create(store);

        return "redirect:/boss/store/index";
    }

    @RequestMapping(value = {RouteWeb.BossStoreEdit}, method = RequestMethod.GET)
    public String GetEdit(Model model, HttpServletRequest request, HttpServletResponse response) {
        String Id = request.getParameter("id");

        Store store = storeService.FindOne(Integer.parseInt(Id));

        model.addAttribute("store", store);

        return "Boss/store/edit";
    }

    @RequestMapping(value = {RouteWeb.BossStoreEdit}, method = RequestMethod.POST)
    public String PostEdit(Model model, HttpServletRequest request, HttpServletResponse response) {
        String Id = request.getParameter("Id");
        String StoreName = request.getParameter("Storename");
        String StoreAdress = request.getParameter("Storeaddresss");

        Store store = storeService.FindOne(Integer.parseInt(Id));

        if (store != null) {

            store.setNameStore(StoreName);
            store.setAddress(StoreAdress);
            storeService.Edit(store);


        }

        return "redirect:/boss/store/index";
    }

    @RequestMapping(value = {RouteWeb.BossStoreBlock}, method = RequestMethod.GET)
    public String BlockStore(Model model, HttpServletRequest request, HttpServletResponse response) {


        String Id = request.getParameter("id");

        Store store = storeService.FindOne(Integer.parseInt(Id));

        if (store.getIsBlock() == 0) {
            store.setIsBlock(1);
        } else {
            store.setIsBlock(0);
        }


        storeService.Edit(store);


        List<Account> accounts = accountService.findByStore(new Store((Integer.parseInt(Id))));

        for (Account item : accounts
        ) {

            if (item.getRole().equals("1")) {
                if (item.getStatus() == true) {
                    item.setStatus(false);
                } else {
                    item.setStatus(true);
                }

                accountService.save(item);
            }

        }

       
        return "redirect:/boss/store/index";
    }


}

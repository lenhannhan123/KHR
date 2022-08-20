/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Store;
import fpt.aptech.KHR.Entities.Timeline;
import fpt.aptech.KHR.Entities.TimelineDetail;
import fpt.aptech.KHR.ImpServices.AccountService;
import fpt.aptech.KHR.ImpServices.JsonServices;
import fpt.aptech.KHR.ImpServices.StoreService;
import fpt.aptech.KHR.ImpServices.TimelineDetailServices;
import fpt.aptech.KHR.ImpServices.TimelineServices;
import fpt.aptech.KHR.Routes.RouteWeb;
import fpt.aptech.KHR.Services.AccountServiceImp;
import io.swagger.models.auth.In;
import java.util.ArrayList;
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

    @Autowired
    TimelineDetailServices timelineDetailServices;
    
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
    @RequestMapping(value = {"/boss/store/statistical"}, method = RequestMethod.GET)
    public String CharPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<Store> list = storeService.FindAl();
        List<Integer> s1 = new ArrayList<>();
        List<String> s2 = new ArrayList<>();
//        s2.add("Ca 1");
//        s2.add("Ca 2");
//        s2.add("Ca 3");
//        s2.add("Ca 4");
//        s2.add("Ca 5");
    
        
        for (int i = 0; i < list.size(); i++) {
            Store get = list.get(i);
            List<Account> l = accountService.findByStore(get);
            s1.add(l.size());
            s2.add(get.getNameStore());
            
        }
        
//        List<TimelineDetail> our = new ArrayList<>();
//        List<TimelineDetail> our2 = new ArrayList<>();
//        List<TimelineDetail> our3 = new ArrayList<>();
//        List<TimelineDetail> our4 = new ArrayList<>();
//        List<TimelineDetail> our5 = new ArrayList<>();
//        List<TimelineDetail> timelineDetails = timelineDetailServices.FindbyIdTimeline(106);
//        for (int i = 0; i < timelineDetails.size(); i++) {
//            TimelineDetail td = timelineDetails.get(i);
//            int time = td.getShiftCode();
//            for (int j = 5; j < 35; j=j+5) {
//            TimelineDetail get = timelineDetails.get(j);
//            if( ((time == 4) || (time == 9) || (time == 14) || (time == 19) || (time == 24) || (time == 29) || (time == 34)) && time < j){
//            our.add(td);
//            }
//            else 
//            if(((time == 0) || (time == 5) || (time == 10) || (time == 15) || (time == 20) || (time == 25) || (time == 30)) && time < j){ 
//            our2.add(td);
//            }
//            if(((time == 1) || (time == 6) || (time == 11) || (time == 16) || (time == 21) || (time == 26) || (time == 31)) && time < j){ 
//            our3.add(td);
//            }
//            if(((time == 2) || (time == 7) || (time == 12) || (time == 17) || (time == 22) || (time == 27) || (time == 32)) && time < j){ 
//            our4.add(td);
//            }
//            if(((time == 3) || (time == 8) || (time == 13) || (time == 18) || (time == 23) || (time == 28) || (time == 33)) && time < j){ 
//            our5.add(td);
//            }
//                
//            }
//            
//
//        }
//        s1.add(our2.size());
//        s1.add(our3.size());
//        s1.add(our4.size());
//        s1.add(our5.size());
//        s1.add(our.size());
//        
//        int total = (our.size()*8)+(our2.size()*4);
        model.addAttribute("name", JsonServices.ParseToJson(s2));
        model.addAttribute("size", JsonServices.ParseToJson(s1));
        //JsonServices.dd(JsonServices.ParseToJson(total), response);
        return "Boss/store/statistical";
    }
    
}

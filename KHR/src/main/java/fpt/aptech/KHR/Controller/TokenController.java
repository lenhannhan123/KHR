/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import fpt.aptech.KHR.Entities.Token;
import fpt.aptech.KHR.ImpServices.FirebaseMessagingService;
import fpt.aptech.KHR.Services.ITokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author LÊ HỮU TÂM
 */
@Controller
public class TokenController {
    
    private final FirebaseMessagingService firebaseService;

    public TokenController(FirebaseMessagingService firebaseService) {
        this.firebaseService = firebaseService;
    }
    @RequestMapping(value = "/api/token", method = RequestMethod.POST)
    public String sendNotification() throws FirebaseMessagingException {
       firebaseService.sendNotification("fR2HKA40SfOcVmKiYcLhEc:APA91bFlPTzjyWZiJO9rptAabFw3-sxhQ2KRf2q2Yqy5H14C5u9F-1AWRIrlRrDrI4FMZZnWGO024hzDLjbYAZ9U8anK5_VccbFQhlXVdJ0GzTB-HfNbU-B4UVaAGA7zwMn_W6ZoeP0g", "Okela", "dươc chưa");
       return "Thanh Cong";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import fpt.aptech.KHR.Entities.AccountToken;
//import fpt.aptech.KHR.ImpServices.FirebaseMessagingService;
import fpt.aptech.KHR.Reponsitory.AccountTokenRepository;
import fpt.aptech.KHR.Services.IAccountToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    @Autowired 
    IAccountToken accToken;
//    @RequestMapping(value = "/api/token", method = RequestMethod.POST)
//    public String sendNotification() throws FirebaseMessagingException {
//       
//       firebaseService.sendNotification("fR2HKA40SfOcVmKiYcLhEc:APA91bFlPTzjyWZiJO9rptAabFw3-sxhQ2KRf2q2Yqy5H14C5u9F-1AWRIrlRrDrI4FMZZnWGO024hzDLjbYAZ9U8anK5_VccbFQhlXVdJ0GzTB-HfNbU-B4UVaAGA7zwMn_W6ZoeP0g", "Okela", "dươc chưa");
//       return "Thanh Cong";
//    }
    @RequestMapping(value = "/api/token/get", method = RequestMethod.GET)
    public ResponseEntity<AccountToken> GetToken(HttpServletRequest request,HttpServletResponse response){
        String token = request.getParameter("token");
        AccountToken at = accToken.GetToken(token);
        if (at != null) {
            return new ResponseEntity<>(at, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        
    }
    @RequestMapping(value = "/api/token/add", method = RequestMethod.POST)
    public ResponseEntity<AccountToken> addon(@RequestBody AccountToken accountToken){
        AccountToken at = accToken.NewToken(accountToken);
        if (at != null) {
            return new ResponseEntity<>(at, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        
    }
    
}

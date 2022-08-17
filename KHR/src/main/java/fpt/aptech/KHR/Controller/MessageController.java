/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Controller;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.ModelString;
import fpt.aptech.KHR.Services.IAccountRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author LÊ HỮU TÂM
 */
@Controller
public class MessageController {
    @Autowired
    private IAccountRepository accountRepository;
    @RequestMapping("/api/message/contact")
    public void Getcontact(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelString in = new ModelString();
        List<ModelString> out = new ArrayList<>();
        in.setData1(request.getParameter("mail"));
        Account acc = accountRepository.findByMail(in.getData1());
        
    }
    
}

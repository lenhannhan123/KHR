/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Mail;
import fpt.aptech.KHR.Reponsitory.MailRepository;
import fpt.aptech.KHR.Services.SendMailService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jthie
 */
@Service
public class SendMailServiceImp implements SendMailService{
//    private final JavaMailSender javaMailSender;
//    public SendMailServiceImp(JavaMailSender javaMailSender){
//        this.javaMailSender = javaMailSender;
//    }
    @Autowired
    MailRepository repository;
    @Override
    public void sendMail(Mail mail) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Mail> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

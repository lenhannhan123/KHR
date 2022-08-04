/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Mail;
import fpt.aptech.KHR.Reponsitory.MailRepository;
import fpt.aptech.KHR.Services.IAccountRepository;
import fpt.aptech.KHR.Services.SendMailService;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author jthie
 */
@Service
public class SendMailServiceImp implements SendMailService {

    @Autowired
    IAccountRepository accountRepository;

    @Autowired
    MailRepository mailRepository;

    private final JavaMailSender javaMailSender;

    public SendMailServiceImp(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(Mail mail) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(mail.getRecipient());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getContent(), true);
            helper.setSentDate(new Date());
//            ##Nếu muốn thêm attachment thì add dòng dưới này
//            helper.addAttachment("something.jpg", new ClassPathResource("./static/images/something.jpg"));
            mailRepository.save(mail);
            javaMailSender.send(msg);

        } catch (MessagingException ex) {
            Logger.getLogger(SendMailServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Mail> findAll() {
        return mailRepository.findAll();
    }

    @Override
    public void sendRecoveryCode(String mail) {
        try {
//            ##Generate recovery code then add it into relevant account
            Account account = accountRepository.findByMail(mail);
            String code = getRandomNumberString();
            account.setRecoverycode(code);
            accountRepository.updateRecoveryCode(code, account.getMail());
            
//            ##Save mail content to DB
            Mail mailObject = new Mail();
            mailObject.setRecipient(mail);
            mailObject.setSubject("Mã phục hồi tài khoản đăng nhập KHR Mobile");
            mailObject.setContent("Account code");
            mailRepository.save(mailObject);
            
            
//            ##Send mail
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(mail);
            helper.setSubject("Mã phục hồi tài khoản đăng nhập KHR Mobile");
            helper.setText("Mã của bạn là: " + account.getRecoverycode(), true);
            helper.setSentDate(new Date());
            javaMailSender.send(msg);
            

        } catch (MessagingException ex) {
            Logger.getLogger(SendMailServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

}

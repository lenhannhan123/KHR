/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.Twilio;
import fpt.aptech.KHR.Entities.SmsPojo;
import org.springframework.stereotype.Service;
import com.twilio.type.PhoneNumber;
import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Sms;
import fpt.aptech.KHR.Reponsitory.MailRepository;
import fpt.aptech.KHR.Reponsitory.SmsRepository;
import fpt.aptech.KHR.Services.IAccountRepository;
import fpt.aptech.KHR.Services.SendSmsService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;

/**
 *
 * @author jthie
 */
@Service
public class SmsService implements SendSmsService {

    @Autowired
    IAccountRepository accountRepository;

    @Autowired
    SmsRepository smsRepository;

    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private final String FROM_NUMBER = "+19704465883";

    public void send(SmsPojo sms) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage())
                .create();
        System.out.println("here is my id:" + message.getSid());// Unique resource ID created to manage this transaction

    }

    public void receive(MultiValueMap<String, String> smscallback) {
    }

    @Override
    public void sendSmS(Sms Sms) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sms> findAll() {
        return smsRepository.findAll();
    }

    @Override
    public void sendRecoveryCode(String mail) {
        try {
//            ##Generate recovery code then add it into relevant account
            Account account = accountRepository.findByMail(mail);
            String code = getRandomNumberString();
            account.setRecoverycode(code);
            accountRepository.updateRecoveryCode(code, account.getMail());

//            ##Save sms content to DB
            Sms smsObject = new Sms();
            smsObject.setPhone(account.getPhone());
            smsObject.setContent("Account recovery code");
            smsRepository.save(smsObject);

//            ##Send Sms
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message
                    .creator(new PhoneNumber(account.getPhone()), new PhoneNumber(FROM_NUMBER), code)
                    .create();
            System.out.println("here is my id:" + message.getSid());// Unique resource ID created to manage this transaction

        } catch (Exception ex) {
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

    public String getTimeStamp() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }
}

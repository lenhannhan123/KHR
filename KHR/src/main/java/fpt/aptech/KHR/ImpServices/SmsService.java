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
import org.springframework.util.MultiValueMap;

/**
 *
 * @author jthie
 */
@Service
public class SmsService {

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
}

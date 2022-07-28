/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountNotification;
import fpt.aptech.KHR.Entities.AccountToken;
import fpt.aptech.KHR.Entities.Notification;
import fpt.aptech.KHR.Reponsitory.AccountNotificationRepository;
import fpt.aptech.KHR.Reponsitory.AccountRepository;
import fpt.aptech.KHR.Reponsitory.AccountTokenRepository;
import fpt.aptech.KHR.Reponsitory.NotificationRepository;
import fpt.aptech.KHR.Services.INotificationServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LÊ HỮU TÂM
 */
@Service
public class NotificationService implements INotificationServices {
    @Autowired
    NotificationRepository nr;
    @Autowired
    AccountNotificationRepository anr;
    @Autowired
    AccountRepository ar;
    @Autowired
    AccountTokenRepository tk;
    @Override
    public List<Notification> findAll() {
        return nr.findAll();
    }

    @Override
    public List<AccountNotification> findAllNotification() {
       return anr.findAll();
    }

    @Override
    public boolean AddNotification(Notification notification) {
        nr.save(notification);
        return true;
    }

    @Override
    public void SendAllPeople(Notification notification) {
        List<Account> accounts = ar.findAll();
        for (Account account : accounts) {
            AccountNotification accountNotification = new AccountNotification();
            accountNotification.setIdnotification(notification);
            accountNotification.setMail(account);
            accountNotification.setStatus(false);
            anr.save(accountNotification);
        }
    }

    @Override
    public void SendAnyPeople(Notification notification, List<Account> listAccount) {
        for (Account account : listAccount) {
            AccountNotification accountNotification = new AccountNotification();
            accountNotification.setIdnotification(notification);
            accountNotification.setMail(account);
            accountNotification.setStatus(false);
            anr.save(accountNotification);
        }
    }

    @Override
    public List<AccountNotification> findbyAccount(Account account) {
        return anr.findByEmail(account);
    }

    @Override
    public void Seen(AccountNotification accountNotification) {
        
    }
        
        
    @Override
    public AccountToken findSendPeople(String mail) {
        Account a = ar.findByEmail(mail);
       return tk.findByMail(a);
    }

    @Override
    public AccountNotification CreateNotificationOnMail(String mail, String type) {
        try {
         Account a = ar.findByEmail(mail);
         Notification n = new Notification();
         n.setTitle("Thông báo phản hồi yêu cầu xin nghĩ");
         n.setContent("Quản trị viên xin trân trọng thông báo.Yêu cầu của nhân viên "+ a.getFullname()+" chúng tôi đã xem xét."+"Bạn đã được "+ type +" với lý do trên.Quản trị viên xin trân trọng thông báo" );
         Notification odl = nr.save(n);
         AccountNotification an = new AccountNotification();
         an.setIdnotification(odl);
         an.setMail(a);
         an.setStatus(false);
         
         return anr.save(an);
        } catch (Exception e) {
            return null;
        }
         
    }

    
    
}

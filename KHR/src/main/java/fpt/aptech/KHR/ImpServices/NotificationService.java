/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountNotification;
import fpt.aptech.KHR.Entities.Notification;
import fpt.aptech.KHR.Reponsitory.AccountNotificationRepository;
import fpt.aptech.KHR.Reponsitory.AccountRepository;
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
    @Override
    public List<Notification> findAll() {
        return nr.findAll();
    }

    @Override
    public List<AccountNotification> findAllNotification() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        accountNotification.setStatus(true);
        anr.save(accountNotification);
    }
    
}

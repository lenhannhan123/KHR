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

    @Override
    public List<Notification> findAll() {
        return nr.findAll();
    }

    @Override
    public List<AccountNotification> findAllNotification() {
        return anr.findAll();
    }

    @Override
    public Notification AddNotification(Notification notification) {

        return nr.save(notification);
    }

    @Override
    public List<AccountNotification> findbyAccount(String mail) {
        Account a = ar.findByEmailUser(mail);
        return anr.findByEmail(a);
    }

    @Override
    public AccountNotification CreateNotificationOnMail(String mail, String type) {
        try {
            Account a = ar.findByEmail(mail);
            Notification n = new Notification();
            n.setTitle("Thông báo phản hồi yêu cầu xin nghĩ");
            n.setContent("Quản trị viên xin trân trọng thông báo.Yêu cầu của nhân viên " + a.getFullname() + " chúng tôi đã xem xét." + "Bạn đã được " + type + " với lý do trên.Quản trị viên xin trân trọng thông báo");
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

    @Override
    public List<AccountNotification> findNotificationByAny(int idnotifacation) {
        Notification n = nr.findById(idnotifacation);
        return anr.findByNotifications(n);
    }

    @Override
    public AccountNotification AddAccountNotification(AccountNotification accountNotification) {
        return anr.save(accountNotification);
    }

    @Override
    public AccountNotification Seen(AccountNotification accountNotification) {
        accountNotification.setStatus(true);
        return anr.save(accountNotification);
    }

}

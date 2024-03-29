/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountNotification;
import fpt.aptech.KHR.Entities.AccountToken;
import fpt.aptech.KHR.Entities.Notification;
import java.util.List;

/**
 *
 * @author LÊ HỮU TÂM
 */
public interface INotificationServices {
    List<Notification> findAll();
    List<AccountNotification> findAllNotification();
    List<AccountNotification> findNotificationByAny(int idnotifacation);
    Notification AddNotification(Notification notification);
    List<AccountNotification> findbyAccount(String mail);
    AccountNotification CreateNotificationOnMail(String mail,String type);
    AccountNotification AddAccountNotification(AccountNotification accountNotification);
    AccountNotification Seen(AccountNotification accountNotification);
}

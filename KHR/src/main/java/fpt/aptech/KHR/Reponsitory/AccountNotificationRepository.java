/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Reponsitory;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.AccountNotification;
import fpt.aptech.KHR.Entities.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author LÊ HỮU TÂM
 */
public interface AccountNotificationRepository extends JpaRepository<AccountNotification, Integer> {
    @Query("SELECT a FROM AccountNotification a WHERE a.mail = :mail")
    List<AccountNotification> findByEmail(@PathVariable("mail") Account mail);
    @Query("SELECT a FROM AccountNotification a WHERE a.idnotification = :idnotification")
    List<AccountNotification> findByNotifications(@PathVariable("idnotification") Notification idnotification);
}

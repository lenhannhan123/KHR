/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Notification;
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
    @Override
    public List<Notification> findAll() {
        return nr.findAll();
    }
    
}

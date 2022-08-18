/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import java.util.List;
import fpt.aptech.KHR.Entities.MessageAccount;
/**
 *
 * @author LÊ HỮU TÂM
 */
public interface IMessageservices {
    List<MessageAccount> findSendTo(String to,String send);
    List<MessageAccount> findSendToStore(String to);
    List<MessageAccount> findSendAccount(String send);
    void AddMessageAccount(MessageAccount newMessageAccount);
}

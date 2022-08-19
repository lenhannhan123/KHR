/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.MessageAccount;
import fpt.aptech.KHR.Reponsitory.MesageAccountRepository;
import fpt.aptech.KHR.Services.IMessageservices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LÊ HỮU TÂM
 */
@Service
public class MesageService implements IMessageservices{
    @Autowired
    MesageAccountRepository mesageAccountRepository;
    @Override
    public List<MessageAccount> findSendTo(String to, String send) {

        return mesageAccountRepository.findMesage(to, send);
    }

    @Override
    public List<MessageAccount> findSendToStore(String to) {
        return mesageAccountRepository.findMessageStore(to);
    }

    @Override
    public void AddMessageAccount(MessageAccount newMessageAccount) {
        mesageAccountRepository.save(newMessageAccount);
    }

    @Override
    public List<MessageAccount> findSendAccount(String send) {
        return mesageAccountRepository.findMessageAccount(send);
    }

    @Override
    public List<MessageAccount> PrivaiteSendTo(String to, String send) {
        return mesageAccountRepository.findMesageSento(to, send);
    }
    
}

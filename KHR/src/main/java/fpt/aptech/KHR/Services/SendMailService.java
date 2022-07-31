/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Mail;
import java.util.List;

/**
 *
 * @author jthie
 */
public interface SendMailService {
    public void sendMail(Mail mail);
    List<Mail> findAll();
    public void sendRecoveryCode(String mail);
}

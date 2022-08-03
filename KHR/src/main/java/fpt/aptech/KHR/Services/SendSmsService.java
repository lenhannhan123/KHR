/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Sms;
import java.util.List;

/**
 *
 * @author jthie
 */
public interface SendSmsService {
    public void sendSmS(Sms Sms);
    List<Sms> findAll();
    public void sendRecoveryCode(String mail);
}

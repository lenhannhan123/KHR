/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Account;
import fpt.aptech.KHR.Entities.Timekeeping;
import java.util.List;

/**
 *
 * @author backs
 */
public interface ITimekeepingServices {

    List<Timekeeping> findAll();

    Timekeeping findOne(int id);

    Timekeeping findByMail(Account mail);

    void checkin(Timekeeping timekeeping);

    void checkout(Timekeeping timekeeping);
    
    public List<String> search(String keyword);
}

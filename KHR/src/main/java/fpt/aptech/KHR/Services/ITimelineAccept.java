/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Store;
import fpt.aptech.KHR.Entities.TimeAccept;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface ITimelineAccept {
    
    public List<TimeAccept> FindAl();

    public boolean Create(TimeAccept timeAccept);

    public boolean Edit(TimeAccept timeAccept);


    public boolean Delete(TimeAccept id);

    public List<Integer> GetIdTimeline(String mail) ;

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.DayOff;
import java.util.List;

/**
 *
 * @author LÊ HỮU TÂM
 */
public interface IDayOffServices {
    List<DayOff> findAll();
    boolean approve(int id);
    boolean denying(int id);
    List<DayOff> findApproveList();
    List<DayOff> findDenyingList();
    List<DayOff> findNotCheck();
}

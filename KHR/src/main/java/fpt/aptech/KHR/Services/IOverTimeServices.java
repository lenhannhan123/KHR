/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.OverTime;
import java.util.List;

/**
 *
 * @author LÊ HỮU TÂM
 */
public interface IOverTimeServices {
   List<OverTime> findAll();
   OverTime findOne(int id);
   boolean approve(int id);
   boolean denying(int id);
   OverTime newOverTime(OverTime overTime);
   List<OverTime> findStatusApproved();
   List<OverTime> findStatusDenying();
   List<OverTime> findStatusNotCheck();
}

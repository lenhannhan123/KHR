/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.OverTime;
import fpt.aptech.KHR.Responsitory.OverTimeRepository;
import fpt.aptech.KHR.Services.IOverTimeServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LÊ HỮU TÂM
 */
@Service
public class OverTimeService implements IOverTimeServices{
    @Autowired
    OverTimeRepository otr;
    
    @Override
    public List<OverTime> findAll() {
        return otr.findAll();
    }
    
}

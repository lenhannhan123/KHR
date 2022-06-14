/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.DayOff;
import fpt.aptech.KHR.Responsitory.DayOffRepository;
import fpt.aptech.KHR.Services.IDayOffServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author LÊ HỮU TÂM
 */
@Service
public class DayOffService implements IDayOffServices{
    @Autowired
    DayOffRepository dor;
    @Override
    public List<DayOff> findAll() {
        return dor.findAll();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.TransferData;
import fpt.aptech.KHR.Reponsitory.TimekeepingRepository;
import fpt.aptech.KHR.Reponsitory.TransferRepository;
import fpt.aptech.KHR.Services.ITransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Admin
 */
@Service
public class TransferService implements ITransfer {

    @Autowired
    TransferRepository transferRepository;

    @Override
    public List<TransferData> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public boolean Create(TransferData transferData) {
        transferRepository.save(transferData);
        return true;
    }

    @Override
    public boolean Edit(TransferData transferData) {
        transferRepository.save(transferData);
        return true;
    }

    @Override
    public String Delete(int id) {


        return "True";
    }

    @Override
    public TransferData FindOne(int id) {
        return transferRepository.findById(id);
    }
}

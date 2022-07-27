/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.ImpServices;

import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Store;
import fpt.aptech.KHR.Reponsitory.ShiftRepository;
import fpt.aptech.KHR.Reponsitory.StoreRepository;
import fpt.aptech.KHR.Services.IStoreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Admin
 */
@Service
public class StoreService implements IStoreServices {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public List<Store> FindAl() {
        return storeRepository.findAll();
    }

    @Override
    public boolean Create(Store store) {

        storeRepository.save(store);

        return true;
    }

    @Override
    public boolean Edit(Store store) {
        storeRepository.save(store);
        return true;
    }

    @Override
    public String Delete(int id) {
        storeRepository.delete(new Store(id));
        return "true";
    }

    @Override
    public Store FindOne(int id) {
        return storeRepository.findID(id);
    }
}

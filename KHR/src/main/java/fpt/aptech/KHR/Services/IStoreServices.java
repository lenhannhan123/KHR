/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.Shift;
import fpt.aptech.KHR.Entities.Store;

import java.util.List;

/**
 * @author Admin
 */
public interface IStoreServices {

    public List<Store> FindAl();

    public boolean Create(Store store);

    public boolean Edit(Store store);


    public String Delete(int id);

    public Store FindOne(int id);


}

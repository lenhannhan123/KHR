/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.aptech.KHR.Services;

import fpt.aptech.KHR.Entities.TimelineDetail;
import fpt.aptech.KHR.Entities.TransferData;

import java.util.List;

/**
 *
 * @author Admin
 */
public interface ITransfer {
    public List<TransferData> findAll();

    public boolean Create(TransferData transferData);

    public boolean Edit(TransferData transferData);


    public String Delete(int id);

    public TransferData FindOne(int id);
    
}

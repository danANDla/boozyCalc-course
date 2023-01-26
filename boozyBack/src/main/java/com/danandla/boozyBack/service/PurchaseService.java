package com.danandla.boozyBack.service;


import com.danandla.boozyBack.entity.PurchaseEntity;
import com.danandla.boozyBack.repository.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PurchaseService {

    @Autowired
    PurchaseRepo purchaseRepo;

    public ArrayList<PurchaseEntity> getAllPurchases() {
        ArrayList<PurchaseEntity> list = (ArrayList<PurchaseEntity>) purchaseRepo.findAll();
        return list;
    }

    public ArrayList<PurchaseEntity> getPurchasesByParty(Long id) {
        ArrayList<PurchaseEntity> list = (ArrayList<PurchaseEntity>) purchaseRepo.findByParty(id);
        return list;
    }
}

package com.danandla.boozyBack.service;


import com.danandla.boozyBack.entity.PartyEntity;
import com.danandla.boozyBack.entity.PurchaseEntity;
import com.danandla.boozyBack.entity.PurchaseId;
import com.danandla.boozyBack.exception.ItemIdNotFoundException;
import com.danandla.boozyBack.exception.ItemNameUsedException;
import com.danandla.boozyBack.model.PartyModel;
import com.danandla.boozyBack.repository.PartyRepo;
import com.danandla.boozyBack.repository.ProductRepo;
import com.danandla.boozyBack.repository.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PurchaseService {

    @Autowired
    PurchaseRepo purchaseRepo;

    @Autowired
    PartyRepo partyRepo;

    @Autowired
    ProductRepo productRepo;

    public ArrayList<PurchaseEntity> getAllPurchases() {
        ArrayList<PurchaseEntity> list = (ArrayList<PurchaseEntity>) purchaseRepo.findAll();
        return list;
    }

    public ArrayList<PurchaseEntity> getPurchasesByParty(Long id) {
        ArrayList<PurchaseEntity> list = (ArrayList<PurchaseEntity>) purchaseRepo.findByParty(id);
        return list;
    }

    public PurchaseEntity addPurchase(PurchaseEntity newPurchase) throws ItemIdNotFoundException {
        if (partyRepo.findById(newPurchase.getParty_id()).isEmpty())
            throw new ItemIdNotFoundException("party with this id was not found");
        if (productRepo.findById(newPurchase.getProduct_id()).isEmpty())
            throw new ItemIdNotFoundException("party with this id was not found");

        return purchaseRepo.save(newPurchase);
    }

    public PurchaseEntity editPurchase(PurchaseEntity newPurchase) throws ItemIdNotFoundException, IllegalArgumentException {
        PurchaseId id = new PurchaseId(newPurchase.getProduct_id(), newPurchase.getParty_id());
        if (purchaseRepo.findById(id).isEmpty())
            throw new ItemIdNotFoundException("purchase with this id was not found");
        PurchaseEntity purchase = purchaseRepo.findById(id).get();
        purchase.setQuantity(newPurchase.getQuantity());
        return purchaseRepo.save(purchase);
    }
}

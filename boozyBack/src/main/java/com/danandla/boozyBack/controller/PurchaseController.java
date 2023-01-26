package com.danandla.boozyBack.controller;


import com.danandla.boozyBack.entity.PurchaseEntity;
import com.danandla.boozyBack.exception.ItemIdNotFoundException;
import com.danandla.boozyBack.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity getPurchases() {
        try {
            return ResponseEntity.ok(purchaseService.getAllPurchases());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/party")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity getPurchasesByParty(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(purchaseService.getPurchasesByParty(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity addPurchase(@RequestBody PurchaseEntity newPurchase) {
        try {
            purchaseService.addPurchase(newPurchase);
            return ResponseEntity.ok("purchase successfully added");
        } catch (ItemIdNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity editPurchase(@RequestBody PurchaseEntity newPurchase) {
        try {
            purchaseService.editPurchase(newPurchase);
            return ResponseEntity.ok("purchase successfully edited");
        } catch (ItemIdNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}

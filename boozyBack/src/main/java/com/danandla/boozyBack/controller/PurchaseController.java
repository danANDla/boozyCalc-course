package com.danandla.boozyBack.controller;


import com.danandla.boozyBack.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

package com.danandla.boozyBack.controller;


import com.danandla.boozyBack.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity getProducts() {
        try {
            return ResponseEntity.ok(productService.getAllIngredients());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

}

package com.danandla.boozyBack.controller;

import com.danandla.boozyBack.service.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cocktails")
public class CocktailController {

    @Autowired
    private CocktailService cocktailService;

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity getCocktails() {
        try {
            return ResponseEntity.ok(cocktailService.getAllCocktails());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

}

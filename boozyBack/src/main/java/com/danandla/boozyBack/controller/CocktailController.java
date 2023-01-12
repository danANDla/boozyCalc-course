package com.danandla.boozyBack.controller;

import com.danandla.boozyBack.entity.CocktailEntity;
import com.danandla.boozyBack.entity.ProductEntity;
import com.danandla.boozyBack.exception.ItemIdNotFoundException;
import com.danandla.boozyBack.exception.ItemNameNotFoundException;
import com.danandla.boozyBack.exception.ItemNameUsedException;
import com.danandla.boozyBack.model.CocktailModel;
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

    @GetMapping("/allTypes")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity getTypes() {
        try {
            return ResponseEntity.ok(cocktailService.getAllTypes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity addCocktail(@RequestBody CocktailModel newCocktail) {
        try {
            cocktailService.addCocktail(newCocktail);
            return ResponseEntity.ok("cocktail successfully added");
        } catch (ItemNameUsedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ItemIdNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity editCocktail(@RequestBody CocktailModel newCocktail) {
        try {
            cocktailService.editCocktail(newCocktail);
            return ResponseEntity.ok("cocktail successfully added");
        } catch (ItemIdNotFoundException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity deleteCocktailById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(cocktailService.deleteById(id));
        } catch (ItemNameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}

package com.danandla.boozyBack.controller;


import com.danandla.boozyBack.entity.IngredientEntity;
import com.danandla.boozyBack.exception.ItemIdNotFoundException;
import com.danandla.boozyBack.exception.ItemNameNotFoundException;
import com.danandla.boozyBack.exception.ItemNameUsedException;
import com.danandla.boozyBack.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity getIngredients() {
        try {
            return ResponseEntity.ok(ingredientService.getAllIngredients());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/allTypes")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity getTypes() {
        try {
            return ResponseEntity.ok(ingredientService.getAllTypes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/distinctTypes")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity getDistinctTypes() {
        try {
            return ResponseEntity.ok(ingredientService.getAllDistinctTypes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity deleteIngredientById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(ingredientService.deleteById(id));
        } catch (ItemNameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity addIngredient(@RequestBody IngredientEntity newIngredient) {
        try {
            ingredientService.addIngredient(newIngredient);
            return ResponseEntity.ok("ingredient successfully added");
        } catch (ItemNameUsedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/edit")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity editIngredient(@RequestBody IngredientEntity newIngredient) {
        try {
            ingredientService.editIngredient(newIngredient);
            return ResponseEntity.ok("ingredient successfully added");
        } catch (ItemIdNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}

package com.danandla.boozyBack.controller;


import com.danandla.boozyBack.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity getRecipes() {
        try {
            return ResponseEntity.ok(recipeService.getAllRecipes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

}

package com.danandla.boozyBack.service;


import com.danandla.boozyBack.entity.RecipeEntity;
import com.danandla.boozyBack.repository.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RecipeService {

    @Autowired
    RecipeRepo recipeRepo;

    public ArrayList<RecipeEntity> getAllRecipes() {
        ArrayList<RecipeEntity> list = (ArrayList<RecipeEntity>) recipeRepo.findAll();
        return list;
    }
}

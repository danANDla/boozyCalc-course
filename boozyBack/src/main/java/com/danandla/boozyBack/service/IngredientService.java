package com.danandla.boozyBack.service;


import com.danandla.boozyBack.entity.IngredientEntity;
import com.danandla.boozyBack.repository.IngredientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class IngredientService {

    @Autowired
    IngredientRepo ingrRepo;

    public ArrayList<IngredientEntity> getAllIngredients() {
        ArrayList<IngredientEntity> list = (ArrayList<IngredientEntity>) ingrRepo.findAll();
        return list;
    }
}

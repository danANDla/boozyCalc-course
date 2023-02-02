package com.danandla.boozyBack.service;


import com.danandla.boozyBack.entity.CocktailTypeEntity;
import com.danandla.boozyBack.entity.IngredientEntity;
import com.danandla.boozyBack.entity.IngredientTypeEntity;
import com.danandla.boozyBack.entity.ProductEntity;
import com.danandla.boozyBack.exception.ItemIdNotFoundException;
import com.danandla.boozyBack.exception.ItemNameNotFoundException;
import com.danandla.boozyBack.exception.ItemNameUsedException;
import com.danandla.boozyBack.repository.IngredientRepo;
import com.danandla.boozyBack.repository.IngredientTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {

    @Autowired
    IngredientRepo ingrRepo;

    @Autowired
    IngredientTypeRepo typeRepo;

    public ArrayList<IngredientEntity> getAllIngredients() {
        ArrayList<IngredientEntity> list = (ArrayList<IngredientEntity>) ingrRepo.findAll();
        return list;
    }

    public Long deleteById(Long ingredientId) throws ItemNameNotFoundException {
        IngredientEntity t = ingrRepo.findById(ingredientId).get();
        if (t != null) {
            ingrRepo.deleteById(ingredientId);
            return ingredientId;
        }
        else throw new ItemNameNotFoundException("ingredient with this id wasn't found");
    }

    public IngredientEntity addIngredient(IngredientEntity newIngredient) throws ItemNameUsedException {
        if (ingrRepo.findByName(newIngredient.getName()) != null)
            throw new ItemNameUsedException("ingredient with this name already exists");
        return ingrRepo.save(newIngredient);
    }

    public IngredientEntity editIngredient(IngredientEntity newIngredient) throws ItemIdNotFoundException, IllegalArgumentException {
        if(ingrRepo.findById(newIngredient.getId()).isEmpty()) throw new ItemIdNotFoundException("ingredient with this id was not found");
        IngredientEntity ingredient = ingrRepo.findById(newIngredient.getId()).get();
        ingredient.setDescription(newIngredient.getDescription());
        ingredient.setName(newIngredient.getName());
        ingredient.setType(newIngredient.getType());
        return ingrRepo.save(ingredient);
    }

    public List<IngredientTypeEntity> getAllTypes() {
        List<IngredientTypeEntity> list = typeRepo.findAll();
        return list;
    }

    public List<Long> getAllDistinctTypes() {
        List<Long> list = ingrRepo.findAllTypes();
        return list;
    }
}

package com.danandla.boozyBack.service;

import com.danandla.boozyBack.entity.CocktailEntity;
import com.danandla.boozyBack.entity.RecipeEntity;
import com.danandla.boozyBack.exception.ItemIdNotFoundException;
import com.danandla.boozyBack.exception.ItemNameNotFoundException;
import com.danandla.boozyBack.exception.ItemNameUsedException;
import com.danandla.boozyBack.model.CocktailModel;
import com.danandla.boozyBack.model.WeightedIngredientModel;
import com.danandla.boozyBack.repository.CocktailRepo;
import com.danandla.boozyBack.repository.IngredientRepo;
import com.danandla.boozyBack.repository.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocktailService {
    @Autowired
    private CocktailRepo cocktailRepo;

    @Autowired
    private IngredientRepo ingredientRepo;

    @Autowired
    private RecipeRepo recipeRepo;

    public List<CocktailEntity> getAllCocktails() {
        List<CocktailEntity> list = (List<CocktailEntity>) cocktailRepo.findAll();
        return list;
    }

    public CocktailEntity addCocktail(CocktailModel newCocktail) throws ItemNameUsedException, ItemIdNotFoundException {
        if (cocktailRepo.findByName(newCocktail.getName()) != null)
            throw new ItemNameUsedException("cocktail with this name already exists");
        for (WeightedIngredientModel i : newCocktail.getIngredients()) {
            if (ingredientRepo.findById(i.getIngredientId()).isEmpty())
                throw new ItemIdNotFoundException("ingredient with this id was not found");
        }
        CocktailEntity cocktailEntity = new CocktailEntity(
                newCocktail.getName(),
                newCocktail.getDescription(),
                newCocktail.getRecipe(),
                1L
        );
        CocktailEntity saved = cocktailRepo.save(cocktailEntity);
        for (WeightedIngredientModel i : newCocktail.getIngredients()) {
            RecipeEntity recipeEntity = new RecipeEntity(i.getIngredientId(), saved.getId(), i.getAmount());
            recipeRepo.save(recipeEntity);
        }
        return saved;
    }

    public Long deleteById(Long cocktailId) throws ItemNameNotFoundException {
        CocktailEntity t = cocktailRepo.findById(cocktailId).get();
        if (t != null) {
            cocktailRepo.deleteById(cocktailId);
            return cocktailId;
        }
        else throw new ItemNameNotFoundException("cocktail with this id wasn't found");
    }
}

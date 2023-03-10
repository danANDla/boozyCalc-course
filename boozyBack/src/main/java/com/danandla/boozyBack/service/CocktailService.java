package com.danandla.boozyBack.service;

import com.danandla.boozyBack.entity.CocktailEntity;
import com.danandla.boozyBack.entity.CocktailTypeEntity;
import com.danandla.boozyBack.entity.RecipeEntity;
import com.danandla.boozyBack.exception.ItemIdNotFoundException;
import com.danandla.boozyBack.exception.ItemNameNotFoundException;
import com.danandla.boozyBack.exception.ItemNameUsedException;
import com.danandla.boozyBack.model.CocktailModel;
import com.danandla.boozyBack.model.WeightedIngredientModel;
import com.danandla.boozyBack.repository.CocktailRepo;
import com.danandla.boozyBack.repository.CocktailTypeRepo;
import com.danandla.boozyBack.repository.IngredientRepo;
import com.danandla.boozyBack.repository.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CocktailService {
    @Autowired
    private CocktailRepo cocktailRepo;

    @Autowired
    private CocktailTypeRepo typeRepo;

    @Autowired
    private IngredientRepo ingredientRepo;

    @Autowired
    private RecipeRepo recipeRepo;

    public List<CocktailModel> getAllCocktails() {
        List<CocktailEntity> list = (List<CocktailEntity>) cocktailRepo.findAll();
        ArrayList<CocktailModel> retList = new ArrayList<>();
        for (CocktailEntity i : list) {
            List<RecipeEntity> recipe = recipeRepo.findByCocktailId(i.getId());
            ArrayList<WeightedIngredientModel> ingredients = new ArrayList<>();
            for (RecipeEntity j : recipe)
                ingredients.add(new WeightedIngredientModel(j.getIngredient_id(), j.getQuantity()));
            CocktailModel cocktail = new CocktailModel(
                    i.getId(),
                    i.getName(),
                    i.getDescription(),
                    i.getRecipe(),
                    i.getType_id(),
                    ingredients
            );
            retList.add(cocktail);
        }
        return retList;
    }

    public List<CocktailTypeEntity> getAllTypes() {
        List<CocktailTypeEntity> list = typeRepo.findAll();
        return list;
    }

    public List<Long> getAllDistinctTypes() {
        List<Long> list = cocktailRepo.findAllTypes();
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
                newCocktail.getType_id()
        );
        CocktailEntity saved = cocktailRepo.save(cocktailEntity);
        for (WeightedIngredientModel i : newCocktail.getIngredients()) {
            RecipeEntity recipeEntity = new RecipeEntity(i.getIngredientId(), saved.getId(), i.getAmount());
            recipeRepo.save(recipeEntity);
        }
        return saved;
    }

    public CocktailEntity editCocktail(CocktailModel newCocktail) throws ItemIdNotFoundException, IllegalArgumentException {
        if (cocktailRepo.findById(newCocktail.getId()).isEmpty())
            throw new ItemIdNotFoundException("cocktail with this id was not found");
        CocktailEntity cocktail = cocktailRepo.findById(newCocktail.getId()).get();
        cocktail.setName(newCocktail.getName());
        cocktail.setDescription(newCocktail.getDescription());
        cocktail.setType_id(newCocktail.getType_id());

        ArrayList<RecipeEntity> ingredients = (ArrayList<RecipeEntity>) recipeRepo.findByCocktailId(cocktail.getId());
        recipeRepo.deleteAll(ingredients);

        CocktailEntity saved = cocktailRepo.save(cocktail);
        System.out.println(newCocktail.getIngredients());
        for (WeightedIngredientModel i : newCocktail.getIngredients()) {
            RecipeEntity recipeEntity = new RecipeEntity(i.getIngredientId(), saved.getId(), i.getAmount());
            recipeRepo.save(recipeEntity);
            System.out.println(recipeEntity);
        }
        return cocktailRepo.save(cocktail);
    }

    public Long deleteById(Long cocktailId) throws ItemNameNotFoundException {
        CocktailEntity t = cocktailRepo.findById(cocktailId).get();
        if (t != null) {
            cocktailRepo.deleteById(cocktailId);
            return cocktailId;
        } else throw new ItemNameNotFoundException("cocktail with this id wasn't found");
    }
}

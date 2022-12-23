package com.danandla.boozyBack.entity;

import java.io.Serializable;
import java.util.Objects;

public class RecipeId implements Serializable {
    private Long ingredient_id;
    private Long cocktail_id;

    public RecipeId() {
    }

    public RecipeId(Long ingredient_id, Long cocktail_id) {
        this.ingredient_id = ingredient_id;
        this.cocktail_id = cocktail_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RecipeId recipeId = (RecipeId) obj;
        return ingredient_id.equals(recipeId.ingredient_id) &&
                cocktail_id.equals(recipeId.cocktail_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient_id, cocktail_id);
    }
}

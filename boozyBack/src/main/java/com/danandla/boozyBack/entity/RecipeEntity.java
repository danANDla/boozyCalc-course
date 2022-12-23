package com.danandla.boozyBack.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "recipes")
@IdClass(RecipeId.class)
public class RecipeEntity implements Serializable {
    @Id
    private Long ingredient_id;
    @Id
    private Long cocktail_id;
    private float quantity;

    public RecipeEntity() {
    }

    public RecipeEntity(Long ingredient_id, Long cocktail_id, float quantity) {
        this.ingredient_id = ingredient_id;
        this.cocktail_id = cocktail_id;
        this.quantity = quantity;
    }

    public Long getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(Long ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public Long getCocktail_id() {
        return cocktail_id;
    }

    public void setCocktail_id(Long cocktail_id) {
        this.cocktail_id = cocktail_id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}


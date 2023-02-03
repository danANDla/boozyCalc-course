package com.danandla.boozyBack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AvailableCocktailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cocktail_id;
    private int quantity;

    public AvailableCocktailsEntity() {
    }

    public Long getCocktail_id() {
        return cocktail_id;
    }

    public void setCocktail_id(Long cocktail_id) {
        this.cocktail_id = cocktail_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

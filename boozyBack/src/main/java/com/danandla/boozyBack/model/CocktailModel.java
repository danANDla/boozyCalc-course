package com.danandla.boozyBack.model;


import java.util.List;

public class CocktailModel {
    private Long id;
    private String name;
    private String description;
    private String recipe;
    private Long type_id;
    private List<WeightedIngredientModel> ingredients;

    public CocktailModel(Long id, String name, String description, String recipe, Long type_id, List<WeightedIngredientModel> ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.recipe = recipe;
        this.type_id = type_id;
        this.ingredients = ingredients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public Long getType_id() {
        return type_id;
    }

    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }

    public List<WeightedIngredientModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<WeightedIngredientModel> ingredients) {
        this.ingredients = ingredients;
    }
}

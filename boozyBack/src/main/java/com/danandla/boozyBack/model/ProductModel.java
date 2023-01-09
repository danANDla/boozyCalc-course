package com.danandla.boozyBack.model;

public class ProductModel {
    private Long id;
    private String name;
    private String ingredientName;
    private Long ingredientId;

    public ProductModel() {
    }

    public ProductModel(Long id, String name, String ingredientName, Long ingredientId, float price) {
        this.id = id;
        this.name = name;
        this.ingredientName = ingredientName;
        this.ingredientId = ingredientId;
        this.price = price;
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

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    private float price;
}

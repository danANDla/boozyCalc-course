package com.danandla.boozyBack.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ingredients")
public class IngredientEntity {
    @Id
    private Long id;
    private String name;
    private String description;

    public IngredientEntity(){

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
}

package com.danandla.boozyBack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "menus")
@IdClass(MenuId.class)
public class MenuEntity {
    @Id
    private Long cocktail_id;
    @Id
    private Long party_id;

    public MenuEntity() {
    }

    public MenuEntity(Long party_id, Long cocktail_id) {
        this.party_id = party_id;
        this.cocktail_id = cocktail_id;
    }

    public Long getParty_id() {
        return party_id;
    }

    public void setParty_id(Long party_id) {
        this.party_id = party_id;
    }

    public Long getCocktail_id() {
        return cocktail_id;
    }

    public void setCocktail_id(Long cocktail_id) {
        this.cocktail_id = cocktail_id;
    }
}

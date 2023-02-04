package com.danandla.boozyBack.model;

public class OrderModel {
    private Long party_id;
    private Long cocktail_id;
    private Long person_id;

    public OrderModel() {
    }

    public OrderModel(Long party_id, Long cocktail_id, Long person_id) {
        this.party_id = party_id;
        this.cocktail_id = cocktail_id;
        this.person_id = person_id;
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

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }
}

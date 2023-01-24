package com.danandla.boozyBack.entity;

import java.io.Serializable;
import java.util.Objects;

public class MenuId implements Serializable {
    private Long party_id;
    private Long cocktail_id;

    public MenuId() {
    }

    public MenuId(Long party_id, Long cocktail_id) {
        this.party_id = party_id;
        this.cocktail_id = cocktail_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MenuId menuId = (MenuId) obj;
        return cocktail_id.equals(menuId.cocktail_id) &&
                party_id.equals(menuId.party_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(party_id, cocktail_id);
    }
}

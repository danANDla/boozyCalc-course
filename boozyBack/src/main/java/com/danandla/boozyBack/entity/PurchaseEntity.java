package com.danandla.boozyBack.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "purchases")
@IdClass(PurchaseId.class)
public class PurchaseEntity implements  Serializable{
    @Id
    private Long product_id;
    @Id
    private Long party_id;
    private float quantity;

    public PurchaseEntity() {
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getParty_id() {
        return party_id;
    }

    public void setParty_id(Long party_id) {
        this.party_id = party_id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}

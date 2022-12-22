package com.danandla.boozyBack.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "purchases")
public class PurchaseEntity implements  Serializable{
    @EmbeddedId PurchaseKey id;
    private float quantity;

    public PurchaseEntity() {
    }

    public PurchaseKey getId() {
        return id;
    }

    public void setId(PurchaseKey id) {
        this.id = id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}

@Embeddable
class PurchaseKey implements Serializable {
    private Long product_id;
    private Long party_id;
}

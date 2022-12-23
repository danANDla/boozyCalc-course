package com.danandla.boozyBack.entity;

import java.io.Serializable;
import java.util.Objects;

public class PurchaseId implements Serializable {
    private Long product_id;
    private Long party_id;

    public PurchaseId() {
    }

    public PurchaseId(Long product_id, Long party_id) {
        this.product_id = product_id;
        this.party_id = party_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PurchaseId purchaseId = (PurchaseId) obj;
        return product_id.equals(purchaseId.product_id) &&
                party_id.equals(purchaseId.party_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, party_id);
    }
}

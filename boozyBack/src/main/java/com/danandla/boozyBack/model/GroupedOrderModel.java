package com.danandla.boozyBack.model;

import com.danandla.boozyBack.entity.GroupedOrderItemEntity;

import java.util.ArrayList;
import java.util.List;

public class GroupedOrderModel {
    Long person_id;
    List<GroupedOrderItemEntity> items;
    float total;

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public List<GroupedOrderItemEntity> getItems() {
        return items;
    }

    public void setItems(List<GroupedOrderItemEntity> items) {
        this.items = items;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public GroupedOrderModel(Long person_id, List<GroupedOrderItemEntity> items, float total) {
        this.person_id = person_id;
        this.items = items;
        this.total = total;
    }

    @Override
    public String toString() {
        return "GroupedOrderModel{" +
                "person_id=" + person_id +
                ", items=" + items +
                '}';
    }
}

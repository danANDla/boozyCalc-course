package com.danandla.boozyBack.model;

import java.sql.Timestamp;
import java.util.List;

public class PartyModel {
    private Long id;
    private String name;
    private java.sql.Timestamp date;
    private String location;
    private String description;
    private List<Long> menu;

    public PartyModel() {
    }

    public PartyModel(Long id, String name, Timestamp date, String location, String description, List<Long> menu) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.menu = menu;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getMenu() {
        return menu;
    }

    public void setMenu(List<Long> menu) {
        this.menu = menu;
    }
}

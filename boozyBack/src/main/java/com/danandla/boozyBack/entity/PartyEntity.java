package com.danandla.boozyBack.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "parties")
public class PartyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private java.sql.Timestamp event_date;
    private String location;
    private String description;

    public PartyEntity() {
    }

    public PartyEntity(String name, Timestamp date, String location, String description) {
        this.name = name;
        this.event_date = date;
        this.location = location;
        this.description = description;
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
        return event_date;
    }

    public void setDate(Timestamp date) {
        this.event_date = date;
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
}

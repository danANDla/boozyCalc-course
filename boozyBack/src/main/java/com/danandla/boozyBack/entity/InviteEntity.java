package com.danandla.boozyBack.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "invites")
@IdClass(InviteId.class)
public class InviteEntity {
    @Id
    private Long party_id;
    @Id
    private Long person_id;

    public InviteEntity() {
    }

    public InviteEntity(Long party_id, Long person_id) {
        this.party_id = party_id;
        this.person_id = person_id;
    }

    public Long getParty_id() {
        return party_id;
    }

    public void setParty_id(Long party_id) {
        this.party_id = party_id;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }
}

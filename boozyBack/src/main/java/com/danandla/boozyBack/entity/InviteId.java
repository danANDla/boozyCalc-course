package com.danandla.boozyBack.entity;

import java.io.Serializable;
import java.util.Objects;

public class InviteId implements Serializable {
    private Long party_id;
    private Long person_id;

    public InviteId() {
    }

    public InviteId(Long party_id, Long person_id) {
        this.party_id = party_id;
        this.person_id = person_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        InviteId inviteId = (InviteId) obj;
        return party_id.equals(inviteId.party_id) && person_id.equals(inviteId.person_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(party_id, person_id);
    }
}

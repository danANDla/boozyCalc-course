package com.danandla.boozyBack.service;

import com.danandla.boozyBack.entity.AvailableCocktailsEntity;
import com.danandla.boozyBack.entity.InviteEntity;
import com.danandla.boozyBack.entity.MenuEntity;
import com.danandla.boozyBack.entity.PartyEntity;
import com.danandla.boozyBack.exception.ItemIdNotFoundException;
import com.danandla.boozyBack.exception.ItemNameNotFoundException;
import com.danandla.boozyBack.exception.ItemNameUsedException;
import com.danandla.boozyBack.model.PartyModel;
import com.danandla.boozyBack.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartyService {

    @Autowired
    PartyRepo partiesRepo;

    @Autowired
    MenuRepo menuRepo;

    @Autowired
    CocktailRepo cocktailRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    InviteRepo inviteRepo;

    @Autowired
    AvailableCocktailRepo availableCocktailRepo;

    public ArrayList<PartyModel> getAllParties() {
        List<PartyEntity> list = (List<PartyEntity>) partiesRepo.findAllDateSorted();
        ArrayList<PartyModel> retList = new ArrayList<>();
        for (PartyEntity i : list) {
            List<MenuEntity> recipe = menuRepo.findByPartId(i.getId());
            List<Long> cocktails = new ArrayList<>();
            for (MenuEntity j : recipe)
                cocktails.add(j.getCocktail_id());
            PartyModel party = new PartyModel(
                    i.getId(),
                    i.getName(),
                    i.getDate(),
                    i.getLocation(),
                    i.getDescription(),
                    cocktails
            );
            retList.add(party);
        }
        return retList;
    }

    public Long deleteById(Long partyId) throws ItemNameNotFoundException {
        PartyEntity t = partiesRepo.findById(partyId).get();
        if (t != null) {
            partiesRepo.deleteById(partyId);
            return partyId;
        } else throw new ItemNameNotFoundException("party with this id wasn't found");
    }

    public PartyEntity addParty(PartyModel newParty) throws ItemNameUsedException, ItemIdNotFoundException {
        if (partiesRepo.findByName(newParty.getName()) != null)
            throw new ItemNameUsedException("party with this name already exists");
        for (Long i : newParty.getMenu()) {
            if (cocktailRepo.findById(i).isEmpty())
                throw new ItemIdNotFoundException("cocktail with this id was not found");

        }
        System.out.println(newParty.getDate());
        PartyEntity party = new PartyEntity(
                newParty.getName(),
                newParty.getDate(),
                newParty.getLocation(),
                newParty.getDescription()
        );
        PartyEntity saved = partiesRepo.save(party);
        for (Long i : newParty.getMenu()) {
            MenuEntity menu = new MenuEntity(saved.getId(), i);
            menuRepo.save(menu);
        }
        return saved;
    }

    public PartyEntity editParty(PartyModel newParty) throws ItemIdNotFoundException, IllegalArgumentException {
        if (partiesRepo.findById(newParty.getId()).isEmpty())
            throw new ItemIdNotFoundException("party with this id was not found");
        PartyEntity party = partiesRepo.findById(newParty.getId()).get();
        party.setDescription(newParty.getDescription());
        party.setName(newParty.getName());
        party.setLocation(newParty.getLocation());
        party.setDate(newParty.getDate());

        ArrayList<MenuEntity> menu = (ArrayList<MenuEntity>) menuRepo.findByPartId(newParty.getId());
        menuRepo.deleteAll(menu);

        for (Long i : newParty.getMenu()) {
            MenuEntity cocktail = new MenuEntity(newParty.getId(), i);
            menuRepo.save(cocktail);
        }
        return partiesRepo.save(party);
    }

    public ArrayList<MenuEntity> getMenu(long partyId) throws ItemNameNotFoundException {
        PartyEntity t = partiesRepo.findById(partyId).get();
        if (t != null) {
            ArrayList<MenuEntity> list = (ArrayList<MenuEntity>) menuRepo.findByPartId(partyId);
            return list;
        } else throw new ItemNameNotFoundException("party with this id wasn't found");
    }

    public ArrayList<InviteEntity> getInvites(long partyId) throws ItemNameNotFoundException {
        PartyEntity t = partiesRepo.findById(partyId).get();
        if (t != null) {
            ArrayList<InviteEntity> list = (ArrayList<InviteEntity>) inviteRepo.findByPartId(partyId);
            return list;
        } else throw new ItemNameNotFoundException("party with this id wasn't found");
    }

    public ArrayList<AvailableCocktailsEntity> getAvailableCocktails(long partyId) throws ItemIdNotFoundException {
        PartyEntity t = partiesRepo.findById(partyId).get();
        if (t != null) {
            ArrayList<AvailableCocktailsEntity> list = (ArrayList<AvailableCocktailsEntity>) availableCocktailRepo.getByParty(partyId);
            return list;
        } else throw new ItemIdNotFoundException("party with this id wasn't found");
    }
}
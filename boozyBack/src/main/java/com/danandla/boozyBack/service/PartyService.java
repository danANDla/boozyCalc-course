package com.danandla.boozyBack.service;

import com.danandla.boozyBack.entity.*;
import com.danandla.boozyBack.exception.ItemIdNotFoundException;
import com.danandla.boozyBack.exception.ItemNameNotFoundException;
import com.danandla.boozyBack.exception.ItemNameUsedException;
import com.danandla.boozyBack.exception.ItemNotAddedException;
import com.danandla.boozyBack.model.GroupedOrderModel;
import com.danandla.boozyBack.model.OrderModel;
import com.danandla.boozyBack.model.PartyModel;
import com.danandla.boozyBack.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    NeededIngredientsRepo neededIngredientsRepo;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrderJpaRepo orderRepoAdd;

    private ArrayList<PartyModel> fillPartyModelList(List<PartyEntity> list){
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

    public ArrayList<PartyModel> getAllParties() {
        List<PartyEntity> list = (List<PartyEntity>) partiesRepo.findAllDateSorted();
        return fillPartyModelList(list);
    }

    public ArrayList<PartyModel> getInvitings(Long person_id) {
        List<PartyEntity> list = (List<PartyEntity>) partiesRepo.findAllDateSortedForPerson(person_id);
        return fillPartyModelList(list);
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

    public ArrayList<InviteEntity> getInvites(long partyId) throws ItemIdNotFoundException {
        PartyEntity t = partiesRepo.findById(partyId).get();
        if (t != null) {
            ArrayList<InviteEntity> list = (ArrayList<InviteEntity>) inviteRepo.findByPartId(partyId);
            return list;
        } else throw new ItemIdNotFoundException("party with this id wasn't found");
    }

    public ArrayList<AvailableCocktailsEntity> getAvailableCocktails(long partyId) throws ItemIdNotFoundException {
        PartyEntity t = partiesRepo.findById(partyId).get();
        if (t != null) {
            ArrayList<AvailableCocktailsEntity> list = (ArrayList<AvailableCocktailsEntity>) availableCocktailRepo.getByParty(partyId);
            return list;
        } else throw new ItemIdNotFoundException("party with this id wasn't found");
    }

    public ArrayList<NeededIngredientsEntity> getNeededIngredients(long p_id, long c_id) throws ItemIdNotFoundException {
        PartyEntity t = partiesRepo.findById(p_id).get();
        if (t != null) {
            ArrayList<NeededIngredientsEntity> list = (ArrayList<NeededIngredientsEntity>) neededIngredientsRepo.getNeeded(p_id, c_id);
            return list;
        } else throw new ItemIdNotFoundException("party with this id wasn't found");
    }

    public ArrayList<OrderEntity> getOrders(long partyId) throws ItemIdNotFoundException {
        PartyEntity t = partiesRepo.findById(partyId).get();
        if (t != null) {
            ArrayList<OrderEntity> list = (ArrayList<OrderEntity>) orderRepo.findByParty(partyId);
            return list;
        } else throw new ItemIdNotFoundException("party with this id wasn't found");
    }

    public ArrayList<GroupedOrderModel> getGroupedOrders(long partyId) throws ItemIdNotFoundException {
        PartyEntity t = partiesRepo.findById(partyId).get();
        ArrayList<InviteEntity> invites = getInvites(partyId);
        if (t != null) {
            ArrayList<GroupedOrderModel> list = new ArrayList<>();
            for(InviteEntity i: invites){
                ArrayList<GroupedOrderItemEntity> items = (ArrayList<GroupedOrderItemEntity>) orderRepo.findGroupedOrder(partyId, i.getPerson_id());
                float price = 0;
                for(GroupedOrderItemEntity item: items) price += item.getPrice();
                GroupedOrderModel groupedOrder = new GroupedOrderModel(i.getPerson_id(), items, price);
                list.add(groupedOrder);
            }
            return list;
        } else throw new ItemIdNotFoundException("party with this id wasn't found");
    }

    public static Throwable findCauseUsingPlainJava(Throwable throwable) {
        Objects.requireNonNull(throwable);
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    public OrderEntity addOrder(OrderModel newOrder) throws ItemIdNotFoundException, ItemNotAddedException {

        if (cocktailRepo.findById(newOrder.getCocktail_id()).isEmpty())
            throw new ItemIdNotFoundException("cocktail with this id was not found");
        if (partiesRepo.findById(newOrder.getParty_id()).isEmpty())
            throw new ItemIdNotFoundException("party with this id was not found");

        Long orderId = -1L;
        try {
            orderId = orderRepo.addOrder(newOrder.getParty_id(), newOrder.getCocktail_id(), newOrder.getPerson_id());
        } catch (DataAccessException e) {
            Throwable root = findCauseUsingPlainJava(e);
            throw new ItemNotAddedException(root.getMessage().split("\n", 2)[0]);
        }
        if (orderRepo.findById(orderId).isPresent()) return orderRepo.findById(orderId).get();
        else throw new ItemIdNotFoundException("order wasn't added");
    }
}
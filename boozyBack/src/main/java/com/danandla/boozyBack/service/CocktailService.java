package com.danandla.boozyBack.service;

import com.danandla.boozyBack.entity.CocktailEntity;
import com.danandla.boozyBack.repository.CocktailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocktailService {
    @Autowired
    private CocktailRepo cocktailRepo;


    public List<CocktailEntity> getAllCocktails() {
        List<CocktailEntity> list = (List<CocktailEntity>) cocktailRepo.findAll();
        return list;
    }
}

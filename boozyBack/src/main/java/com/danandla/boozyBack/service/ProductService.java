package com.danandla.boozyBack.service;

import com.danandla.boozyBack.entity.ProductEntity;
import com.danandla.boozyBack.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public ArrayList<ProductEntity> getAllIngredients() {
        ArrayList<ProductEntity> list = (ArrayList<ProductEntity>) productRepo.findAll();
        return list;
    }
}

package com.danandla.boozyBack.service;

import com.danandla.boozyBack.entity.IngredientEntity;
import com.danandla.boozyBack.entity.ProductEntity;
import com.danandla.boozyBack.exception.ItemIdNotFoundException;
import com.danandla.boozyBack.exception.ItemNameNotFoundException;
import com.danandla.boozyBack.exception.ItemNameUsedException;
import com.danandla.boozyBack.model.ProductModel;
import com.danandla.boozyBack.repository.IngredientRepo;
import com.danandla.boozyBack.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    IngredientRepo ingredientRepo;

    public ArrayList<ProductModel> getAllProducts() {
        ArrayList<ProductEntity> list = (ArrayList<ProductEntity>) productRepo.findAll();
        ArrayList<ProductModel> retList = new ArrayList<>();
        for(ProductEntity i: list){
            String ingrName = "";
            if(i.getIngredientId() != null && ingredientRepo.existsById(i.getIngredientId())){
                ingrName = (ingredientRepo.findById(i.getIngredientId()).get()).getName();
            }
            retList.add(new ProductModel(i.getId(), i.getName(), ingrName, i.getIngredientId(), i.getPrice()));
        }
        return retList;
    }

    public Long deleteById(Long productId) throws ItemNameNotFoundException {
        try{
            productRepo.deleteById(productId);
        } catch (IllegalArgumentException e){
            throw new ItemNameNotFoundException("product with this id wasn't found");
        }
        return productId;
    }

    public ProductEntity addProduct(ProductEntity newProduct) throws ItemNameUsedException, ItemIdNotFoundException {
        if (productRepo.findByName(newProduct.getName()) != null)
            throw new ItemNameUsedException("product with this name already exists");
        if (ingredientRepo.findById(newProduct.getIngredientId()).isEmpty())
            throw new ItemIdNotFoundException("ingredient with this id was not found");

        return productRepo.save(newProduct);
    }

    public ProductEntity editProduct(ProductEntity newProduct) throws ItemIdNotFoundException, IllegalArgumentException {
        if(productRepo.findById(newProduct.getId()).isEmpty()) throw new ItemIdNotFoundException("product with this id was not found");
        ProductEntity product = productRepo.findById(newProduct.getId()).get();
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        product.setName(newProduct.getName());
        product.setIngredientId(newProduct.getIngredientId());
        return productRepo.save(product);
    }
}

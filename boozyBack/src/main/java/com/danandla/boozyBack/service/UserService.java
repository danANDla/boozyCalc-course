package com.danandla.boozyBack.service;

import com.danandla.boozyBack.entity.UserEntity;
import com.danandla.boozyBack.exception.ItemNameNotFoundException;
import com.danandla.boozyBack.model.UserModel;
import com.danandla.boozyBack.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;


    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserEntity> list = (ArrayList<UserEntity>) userRepo.findAll();
        ArrayList<UserModel> retlist = new ArrayList<UserModel>();
        for(UserEntity u: list){
            retlist.add(new UserModel(u.getId(), u.getUserName(), u.getRealName()));
        }
        return retlist;
    }

    public Long deleteById(Long userId) throws ItemNameNotFoundException {
        UserEntity t = userRepo.findById(userId).get();
        if (t != null) {
            userRepo.deleteById(userId);
            return userId;
        } else throw new ItemNameNotFoundException("user with this id wasn't found");
    }
}

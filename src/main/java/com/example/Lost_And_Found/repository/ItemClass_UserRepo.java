package com.example.Lost_And_Found.repository;

import com.example.Lost_And_Found.entity.ItemClass_User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemClass_UserRepo extends MongoRepository<ItemClass_User,String> {
}

package com.example.Lost_And_Found.repository;

import java.util.List;

import com.example.Lost_And_Found.entity.ItemClass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemClassRepo extends MongoRepository<ItemClass,String> {
    List<ItemClass> findByDescription(String description);
}

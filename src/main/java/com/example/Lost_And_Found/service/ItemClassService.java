package com.example.Lost_And_Found.service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.example.Lost_And_Found.entity.ItemClass;
import com.example.Lost_And_Found.repository.ItemClassRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ItemClassService {

    private final ItemClassRepo itemClassRepo;

    public ItemClassService(ItemClassRepo itemClassRepo) {
        this.itemClassRepo = itemClassRepo;
    }

    public void addItem(ItemClass itemData,MultipartFile[] imageData) throws IOException {
        if(imageData!=null&& imageData.length>0){
            List<byte[]> listImages = new ArrayList<>();
            for(MultipartFile data:imageData){
                if(!data.isEmpty()){
                    listImages.add(data.getBytes());
                }
            }
            itemData.setImage(listImages);
            itemData.setCreatedAt(Instant.now());
        }
        itemClassRepo.save(itemData);
    }

}

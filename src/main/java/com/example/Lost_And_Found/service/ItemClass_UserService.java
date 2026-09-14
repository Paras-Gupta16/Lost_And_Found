package com.example.Lost_And_Found.service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.example.Lost_And_Found.entity.ItemClass_User;
import com.example.Lost_And_Found.repository.ItemClass_UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ItemClass_UserService {

    private final ItemClass_UserRepo itemClassUserRepo;

    public ItemClass_UserService(ItemClass_UserRepo itemClassUserRepo) {
        this.itemClassUserRepo = itemClassUserRepo;
    }

    public void user_ItemDetail
            (ItemClass_User itemClassUser, MultipartFile[] imageData) throws IOException {

        if(imageData!=null && imageData.length>0){
            List<byte[]> imageListData = new ArrayList<>();
            for(MultipartFile data:imageData){
                imageListData.add(data.getBytes());
            }
            itemClassUser.setUserImageList(imageListData);
            itemClassUser.setUser_report_createdTime(Instant.now());
        }
        itemClassUserRepo.save(itemClassUser);
    }
}

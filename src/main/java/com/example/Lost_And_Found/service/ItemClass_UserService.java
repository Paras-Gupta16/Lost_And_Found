package com.example.Lost_And_Found.service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.example.Lost_And_Found.entity.ItemClass_User;
import com.example.Lost_And_Found.repository.ItemClass_UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ItemClass_UserService {

    private final ItemClass_UserRepo itemClassUserRepo;
    private final DescriptionBasedMatchingService descriptionBasedMatchingService;

    public ItemClass_UserService(ItemClass_UserRepo itemClassUserRepo, DescriptionBasedMatchingService descriptionBasedMatchingService) {
        this.itemClassUserRepo = itemClassUserRepo;
        this.descriptionBasedMatchingService = descriptionBasedMatchingService;
    }

    private static final Logger log =
            LoggerFactory.getLogger(ItemClass_UserService.class);

    public void user_ItemDetail
            (ItemClass_User itemClassUser, MultipartFile[] imageData) throws IOException {

        if(imageData!=null && imageData.length>0){
            List<byte[]> imageListData = new ArrayList<>();
            for(MultipartFile data:imageData){
                imageListData.add(data.getBytes());
            }
            itemClassUser.setUserImageList(imageListData);
            List<ItemClass_User> user_description = itemClassUserRepo.findAll();
            List<String> user_descriptionEmbedding = new ArrayList<>(
                    user_description
                            .stream()
                            .map(ItemClass_User::getUser_description)
                            .filter(Objects::nonNull)
                            .toList()
            );
            float[] user_embeddings = descriptionBasedMatchingService
                    .generateEmbedding(itemClassUser.getUser_description(), user_descriptionEmbedding);
            itemClassUser.setUser_descriptionEmbeddings(user_embeddings);
            itemClassUser.setUser_report_createdTime(Instant.now());
            log.info("User_Embedding details saved:"+Arrays.toString(user_embeddings));
        }
        itemClassUserRepo.save(itemClassUser);
    }

    public void user_UpdateImage(MultipartFile[] newImageData,String Id) throws IOException {
        ItemClass_User itemClassUser = itemClassUserRepo.findById(Id).orElse(null);
        if(itemClassUser!=null){
            List<byte[]> user_ImageDataList = itemClassUser.getUserImageList();
            user_ImageDataList.clear();
            for(MultipartFile user_imageData:newImageData){
                user_ImageDataList.add(user_imageData.getBytes());
            }
            itemClassUser.setUserImageList(user_ImageDataList);
            itemClassUserRepo.save(itemClassUser);
        }
    }
}

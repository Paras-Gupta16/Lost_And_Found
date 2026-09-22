package com.example.Lost_And_Found.service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.example.Lost_And_Found.entity.ItemClass;
import com.example.Lost_And_Found.repository.ItemClassRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ItemClassService {

    private final ItemClassRepo itemClassRepo;
    private final DescriptionBasedMatchingService descriptionBasedMatchingService;

    public ItemClassService(ItemClassRepo itemClassRepo, DescriptionBasedMatchingService descriptionBasedMatchingService) {
        this.itemClassRepo = itemClassRepo;
        this.descriptionBasedMatchingService = descriptionBasedMatchingService;
    }

    private static final Logger log =
            LoggerFactory.getLogger(ItemClass.class);

    public void addItem(ItemClass itemData,MultipartFile[] imageData) throws IOException {
        if(imageData!=null&& imageData.length>0){
            List<byte[]> listImages = new ArrayList<>();
            for(MultipartFile data:imageData){
                if(!data.isEmpty()){
                    listImages.add(data.getBytes());
                }
            }
            itemData.setImage(listImages);
            List<ItemClass> existingItem = itemClassRepo.findAll();
            List<String> descriptionBased = new ArrayList<>(existingItem
                    .stream()
                    .map(ItemClass::getDescription)
                    .filter(Objects::nonNull)
                    .toList());

            descriptionBased.add(itemData.getDescription());
            float[] embeddings = descriptionBasedMatchingService
                    .generateEmbedding(itemData.getDescription(),descriptionBased);

            log.info("Embedding saved successfully:"+ Arrays.toString(embeddings));
            itemData.setDescriptionEmbedding(embeddings);
            itemData.setCreatedAt(Instant.now());
        }
        itemClassRepo.save(itemData);
    }

    public void updateImage(MultipartFile[] imageData,String id) throws IOException {
        ItemClass itemClass = itemClassRepo.findById(id).orElseThrow(null);
        if(itemClass!=null) {
            List<byte[]> imageList = itemClass.getImage();
            imageList.clear();
            for (MultipartFile newImageData : imageData) {
                if (newImageData != null&&!newImageData.isEmpty()) {
                    imageList.add(newImageData.getBytes());
                }
            }
            itemClass.setImage(imageList);
            itemClassRepo.save(itemClass);
        }
    }

    public ItemClass displayTheReport(String id){
        ItemClass itemClass = itemClassRepo.findById(id).orElseThrow(null);
        return itemClass;
    }

    public Page<ItemClass> displayAllTheData(int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page,size);
        return itemClassRepo.findAll(pageable);
    }

}

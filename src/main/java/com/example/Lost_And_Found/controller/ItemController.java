package com.example.Lost_And_Found.controller;

import com.example.Lost_And_Found.entity.ItemClass;
import com.example.Lost_And_Found.service.ItemClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/lost/report")
public class ItemController {

    private static final Logger log =
            LoggerFactory.getLogger(ItemController.class);

    private final ItemClassService itemService;

    public ItemController(ItemClassService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> reportLostItem
            (@RequestPart("item") String item,@RequestPart("images") MultipartFile[] imageData){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ItemClass itemClass = objectMapper.readValue(item,ItemClass.class);
            itemService.addItem(itemClass,imageData);
            log.info("Item Saved\n"+itemClass.getId());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Data and Images successfully uploaded");
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process the data");
        }
    }


}

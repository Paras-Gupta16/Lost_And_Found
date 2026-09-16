package com.example.Lost_And_Found.controller;

import com.example.Lost_And_Found.entity.ItemClass_User;
import com.example.Lost_And_Found.service.ItemClass_UserService;
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
@RequestMapping("/user/report/")
public class UserItemController {

    private final ItemClass_UserService itemClassUserService;


    private static final Logger log =
            LoggerFactory.getLogger(ItemController.class);

    public UserItemController(ItemClass_UserService itemClassUserService) {
        this.itemClassUserService = itemClassUserService;
    }

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String>
    userUploadItem(@RequestPart("user-item") String item, @RequestPart("user-image")MultipartFile[] userImageData){
        try{
            ObjectMapper  objectMapper = new ObjectMapper();
            ItemClass_User itemClassUser = objectMapper
                    .readValue(item,ItemClass_User.class);
            itemClassUserService.user_ItemDetail(itemClassUser,userImageData);
            log.info("User-Item_Saved:"+itemClassUser.getId());
            return ResponseEntity.status(HttpStatus.OK)
                                 .body("Data uploaded successfully"+"\n"+"Id for reference:"+itemClassUser.getId());
        }catch (Exception e){
            log.error("Error in uploading the data:\t"+e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error in uploading the data");
        }
    }

    @PostMapping(value = "/user/new/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> user_UploadNewImage(MultipartFile[] user_newImageData,String user_Id){
        try{
            itemClassUserService.user_UpdateImage(user_newImageData,user_Id);
            log.info("Image update successfully for Id:"+user_Id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Image Update successfully");
        }catch (Exception e){
            log.error("Error in updating the image:"+e.getMessage()+"Id for reference:"+user_Id);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error in Uploading the image");
        }
    }
}

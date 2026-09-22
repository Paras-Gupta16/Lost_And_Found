package com.example.Lost_And_Found.entity;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user-lost-report")
@Getter
@Setter
public class ItemClass_User {

    @Id
    private String Id;

    private String user_name;
    private String user_description;
    private String user_phoneNUmber;

    @CreatedDate
     private Instant user_report_createdTime;

    private List<byte[]> userImageList;

    private float[] user_descriptionEmbeddings;

}

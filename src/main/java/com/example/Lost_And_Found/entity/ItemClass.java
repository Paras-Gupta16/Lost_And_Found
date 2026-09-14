package com.example.Lost_And_Found.entity;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "lost-report")
@Getter
@Setter
public class ItemClass {

    @Id
    private String id;

    private String name;
    private String description;
    private String phoneNumber;

    @CreatedDate
    private Instant createdAt;
    private List<byte[]> image;
}

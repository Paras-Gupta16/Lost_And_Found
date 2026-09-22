package com.example.Lost_And_Found.dto;

import com.example.Lost_And_Found.entity.ItemClass;

public class DescriptionMatchingResultDTO {
    public ItemClass getItem() {
        return item;
    }

    public double getPercentage() {
        return Percentage;
    }

    ItemClass item;

    public DescriptionMatchingResultDTO(ItemClass item, double percentage) {
        this.item = item;
        Percentage = percentage;
    }

    double Percentage;
}

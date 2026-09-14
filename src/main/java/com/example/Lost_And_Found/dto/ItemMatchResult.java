// ItemMatchResult.java

package com.example.Lost_And_Found.dto;

import com.example.Lost_And_Found.entity.ItemClass;

public class ItemMatchResult {

    private ItemClass item;
    private double confidence;
    private String reason;

    public ItemMatchResult(
            ItemClass item,
            double confidence,
            String reason) {

        this.item = item;
        this.confidence = confidence;
        this.reason = reason;
    }

    public ItemClass getItem() {
        return item;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getReason() {
        return reason;
    }
}
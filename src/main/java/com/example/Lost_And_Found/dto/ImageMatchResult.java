package com.example.Lost_And_Found.dto;

public class ImageMatchResult {

    private boolean match;
    private double confidence;
    private String reason;

    public ImageMatchResult() {
    }

    public ImageMatchResult(boolean match, double confidence, String reason) {
        this.match = match;
        this.confidence = confidence;
        this.reason = reason;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
package com.example.radiusClient.Dao;

import org.springframework.stereotype.Component;

@Component
public class RequirementEntity {

    private Long id;

    private Double latitude;

    private Double longitude;

    private Double minBudget;

    private Double maxBudget;

    private Integer minBedroom;

    private Integer maxBedroom;

    private Integer minBathroom;

    private Integer maxBathroom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(Double minBudget) {
        this.minBudget = minBudget;
    }

    public Double getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(Double maxBudget) {
        this.maxBudget = maxBudget;
    }

    public Integer getMinBedroom() {
        return minBedroom;
    }

    public void setMinBedroom(Integer minBedroom) {
        this.minBedroom = minBedroom;
    }

    public Integer getMaxBedroom() {
        return maxBedroom;
    }

    public void setMaxBedroom(Integer maxBedroom) {
        this.maxBedroom = maxBedroom;
    }

    public Integer getMinBathroom() {
        return minBathroom;
    }

    public void setMinBathroom(Integer minBathroom) {
        this.minBathroom = minBathroom;
    }

    public Integer getMaxBathroom() {
        return maxBathroom;
    }

    public void setMaxBathroom(Integer maxBathroom) {
        this.maxBathroom = maxBathroom;
    }

    public RequirementEntity(){};

    public RequirementEntity(Double latitude, Double longitude, Double minBudget, Double maxBudget,
                             Integer minBedroom, Integer maxBedroom, Integer minBathroom, Integer maxBathroom){
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.minBudget = minBudget;
        this.maxBudget = maxBudget;
        this.minBedroom = minBedroom;
        this.maxBedroom = maxBedroom;
        this.minBathroom = minBathroom;
        this.maxBathroom = maxBathroom;
    }

    @Override
    public String toString(){
        return "Requirement : { " +
                "lat: " + this.latitude + ", " +
                "lon: " + this.longitude + ", " +
                "min Budget: " + this.minBudget + ", " +
                "max Budget: " + this.maxBudget + ", " +
                "min no. of bedrooms: " + this.minBedroom + ", " +
                "max no. of bedrooms: " + this.maxBedroom + ", " +
                "min no. of bathrooms: " + this.minBathroom + ", " +
                "max no. of bathrooms: " + this.maxBathroom + " }";
    }
}

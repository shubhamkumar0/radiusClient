package com.example.radiusClient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "propertyEntity")
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double latitude;

    private Double longitude;

    private Double price;

    private Integer nBedroom;

    private Integer nBathroom;

    public PropertyEntity(){};

    public PropertyEntity(Double latitude, Double longitude, Double price, Integer nBedroom, Integer nBathroom){
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.nBedroom = nBedroom;
        this.nBathroom = nBathroom;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getnBedroom() {
        return nBedroom;
    }

    public void setnBedroom(Integer nBedroom) {
        this.nBedroom = nBedroom;
    }

    public Integer getnBathroom() {
        return nBathroom;
    }

    public void setnBathroom(Integer nBathroom) {
        this.nBathroom = nBathroom;
    }

    @Override
    public String toString(){
        return "Property : { " +
                    "lat: " + this.latitude + ", " +
                    "lon: " + this.longitude + ", " +
                    "price: " + this.price + ", " +
                    "no. of bedrooms: " + this.nBedroom + ", " +
                    "no. of bathrooms: " + this.nBathroom + " }";
    }
}

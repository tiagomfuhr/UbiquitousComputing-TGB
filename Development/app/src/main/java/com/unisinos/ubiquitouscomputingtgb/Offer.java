package com.unisinos.ubiquitouscomputingtgb;

import java.io.Serializable;

public class Offer implements Serializable{

    int id;
    String offer;
    String description;
    Double latitude;
    Double longitude;
    float distanceToUser;
    Double price;
    String url;
    String dealer;
    String offerType;

    public Offer(){

    }

    public Offer(String offer, String description, String url, Double latitude, Double longitude, Double price, String dealer, String offerType) {
        this.offer = offer;
        this.description = description;
        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.dealer = dealer;
        this.offerType = offerType;
    }

    public Offer(int id, String offer, String description, String url, Double latitude, Double longitude, Double price, String dealer, String offerType) {
        this.id = id;
        this.offer = offer;
        this.description = description;
        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.dealer = dealer;
        this.offerType = offerType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public float getDistanceToUser() {
        return distanceToUser;
    }

    public void setDistanceToUser(float distanceToUser) {
        this.distanceToUser = distanceToUser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getPrice() {

        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }
}


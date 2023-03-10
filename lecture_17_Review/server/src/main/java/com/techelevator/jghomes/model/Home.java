package com.techelevator.jghomes.model;

import java.math.BigDecimal;

public class Home {

    private int homeId;
    private String mlsNumber;
    private Address address;
    private double numberOfBedrooms;
    private double numberOfBathrooms;
    private BigDecimal price;
    private String shortDescription;

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public String getMlsNumber() {
        return mlsNumber;
    }

    public void setMlsNumber(String mlsNumber) {
        this.mlsNumber = mlsNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public void setNumberOfBedrooms(double numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public double getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(double numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

}

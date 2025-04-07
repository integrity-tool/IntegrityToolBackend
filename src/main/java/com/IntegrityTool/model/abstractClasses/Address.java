package com.IntegrityTool.model.abstractClasses;

public class Address {
    public String streetNo;
    public String city;
    public String state;
    public String zipCode;
    public String country;

    // Constructor for Address Class
    public Address(String streetNo, String city, String state, String zipCode, String country) {
        this.streetNo = streetNo;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }
}

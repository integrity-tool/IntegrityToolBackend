package com.IntegrityTool.model.abstractclass;

public class Address {
    public int streetNo;
    public String city;
    public String state;
    public int zipCode;
    public String country;

    // Constructor for Address Class
    public Address(int streetNo, String city, String state, int zipCode, String country) {
        this.streetNo = streetNo;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }
}

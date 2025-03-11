package com.IntegrityTool.model.abstractclass;

import java.sql.Date;
import java.util.ArrayList;

public abstract class Person<T> {
    // Person Class Attributes
    private String personId;
    public String firstName;
    public String lastName;
    public char gender;
    public Date dateOfBirth;
    public ArrayList<Address> address;

    // Constructors
    public Person(String personId, String firstName, String lastName, char gender, Date dateOfBirth, ArrayList<Address> address) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    // Getter and Setter Methods for Person Class
    public String getPersonId() {
        return personId;
    }
    
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    // Person class methods for resuablity purpose
    public abstract ArrayList<T> getAllPersons();
    public abstract T getPersonById(String personId);
    public abstract T getPersonByName(String fullName);
    public abstract T getPersonByGender(char gender);
}

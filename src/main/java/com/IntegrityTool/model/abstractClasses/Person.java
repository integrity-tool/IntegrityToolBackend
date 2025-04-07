package com.IntegrityTool.model.abstractClasses;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Person {
    // Attributes -> Person Class
    protected String personId;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String gender;
    protected Date dateOfBirth;
    @JsonProperty("address")
    protected Address address;

    public Person(String firstName, String lastName, String email, String gender, Date dateOfBirth, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public abstract void setPersonId();

    public abstract void setPersonStatus(boolean status);

    public abstract String getPersonId();

    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String getEmail();

    public abstract String getGender();

    public abstract Date getDateOfBirth();

    public abstract Address getAddress();

    public abstract boolean getPersonStatus();

    public abstract String getPassword();

}

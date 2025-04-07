package com.IntegrityTool.model.abstractClasses;

import java.sql.Date;
import java.util.UUID;

public class User extends Person {
    private String password;
    private boolean isActive;

    public User(String firstName, String lastName, String email, String gender, Date dateOfBirth, String password,
            boolean isActive,
            Address address) {
        super(firstName, lastName, email, gender, dateOfBirth, address);
        this.isActive = isActive;
        this.password = password;
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void setPersonId() {
        this.personId = generateUUID();
    }

    @Override
    public String getPersonId() {
        return this.personId;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getGender() {
        return this.gender;
    }

    @Override
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    @Override
    public void setPersonStatus(boolean status) {
        this.isActive = status;
    }

    @Override
    public boolean getPersonStatus() {
        return this.isActive;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Address getAddress() {
        return this.address;
    }
}

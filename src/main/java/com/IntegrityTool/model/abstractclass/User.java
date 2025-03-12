package com.IntegrityTool.model.abstractclass;

import java.sql.Date;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="User")
public abstract class User extends Person {
    private String password;
    private String newPassword;
    private boolean isActive;

    public User(String personId, String firstName, String lastName , char gender, Date dateOfBirth, ArrayList<Address> address, String password, String newPassword, boolean isActive) {
        super(personId, firstName, lastName,gender, dateOfBirth, address);
    }

    @Override
    public ArrayList<User> getAllPersons() {
        return null;
    }

    @Override
    public User getPersonById(String personId) {
        return null;
    }

    @Override
    public User getPersonByName(String name) {
        return null;
    }

    @Override
    public User getPersonByGender(char gender) {
        return null;
    }
}

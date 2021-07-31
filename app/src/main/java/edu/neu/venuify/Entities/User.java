package edu.neu.venuify.Entities;

public class User {

    public String uid;
    public String firstName;
    public String lastName;
    public Boolean isAdmin;

    public User() {}

    public User (String uid, String firstName, String lastName, Boolean isAdmin) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;

    }

    @Override
    public String toString() {
        return firstName;
    }
}

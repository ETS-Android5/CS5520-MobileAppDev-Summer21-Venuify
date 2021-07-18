package edu.neu.venuify.Entities;

public class User {

    public String uid;
    public String firstName;
    public String lastName;

    public User() {}

    public User (String uid, String firstName, String lastName) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName;
    }
}

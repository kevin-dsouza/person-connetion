package com.kevin.personconnections.Model;

public class Person {
    int id;
    String firstName;

    public Person (int id , String firstname) {
        this.id = id;
        this.firstName = firstname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}

package com.example.learnquest.model.user;

import com.example.learnquest.model.group.Group;

public class User {

    Integer userID;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public static User demoUser(){
        return new User("Ben","Tennyson", "0401195092087","ben10@gmail.com", "ben10", "#003eb3");
    }

    String firstName;
    String lastName;
    String nationalID;
    String email;
    String username;
    String password;


    public static User demoUser(){
        return new User(0,"Mathew","Maartens","0303025049089","maartens.mathew@gmail.com","Maartens.Mathew","qwerty");
    }

    private User(Integer userID, String firstName, String lastName, String nationalID, String email, String username, String password){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalID = nationalID;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String national_ID) {
        this.nationalID = national_ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public User() {
    }

    public User(String firstName, String lastName, String nationalID, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalID = nationalID;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "Student{" +
                "first_Name='" + firstName + '\'' +
                ", last_Name='" + lastName + '\'' +
                ", national_ID='" + nationalID + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void clear(){
        firstName = "";
        lastName = "";
        email = "";
        nationalID = "";
        username = "";
        password = "";
    }
}

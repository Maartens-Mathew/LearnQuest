package com.example.learnquest.model.user;

public class Moderator extends User implements ElevatedPermissions {
    public Moderator(String firstName, String lastName, String nationalID, String email, String username, String password) {
        super(firstName, lastName, nationalID, email, username, password);
    }
}

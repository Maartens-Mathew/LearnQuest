package com.example.learnquest.user;

import com.example.learnquest.user.ElevatedPermissions;

public class Moderator extends User implements ElevatedPermissions {
    public Moderator(String firstName, String lastName, String nationalID, String email, String username, String password) {
        super(firstName, lastName, nationalID, email, username, password);
    }
}

package com.example.learnquest;

public class Student extends User {
    @Override
    public String toString() {
        return "Student{" +
                "first_Name='" + first_Name + '\'' +
                ", last_Name='" + last_Name + '\'' +
                ", national_ID='" + national_ID + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

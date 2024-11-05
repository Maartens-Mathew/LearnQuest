package com.example.learnquest.model.studyResource;

public class Message {

    public Owner owner;
    public String message;

    public enum Owner{
        ME, AI
    }

    public static Message fromMe(String message){
        Message temp = new Message();
        temp.owner = Owner.ME;
        temp.message = message;
        return temp;

    }

    public static Message fromAI(String message){
        Message temp = new Message();
        temp.owner = Owner.AI;
        temp.message = message;
        return temp;

    }
}

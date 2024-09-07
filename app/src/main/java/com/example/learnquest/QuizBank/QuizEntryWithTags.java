package com.example.learnquest.QuizBank;

import java.util.List;

import java.util.List;

public class QuizEntryWithTags {
    private int quizEntryID;
    private String question;
    private String answer;
    private String description;
    private int groupID;
    private boolean isValid;

    //wheres tags?
//i remvboed it eish

    //Okay what we can do is once we get the result set, each QuizEntry can have a list<Tag>? that fine

    //For now, I have a TaggedQuiz class that is like a temporary class to hold the results
    // Constructors
    public QuizEntryWithTags(int quizEntryID, String question, String answer, String description, Integer groupID ,boolean isV) {
        this.quizEntryID = quizEntryID;
        this.question = question;
        this.answer = answer;
        this.description = description;
        this.groupID = 1;
        this.isValid = isV;
    }

    // Getters and Setters
    public int getQuizEntryID() { return quizEntryID; }
    public void setQuizEntryID(int quizEntryID) { this.quizEntryID = quizEntryID; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getGroupID() { return groupID; }
    public void setGroupID(int groupID) { this.groupID = groupID; }


}

package com.example.learnquest.QuizBank;

import java.util.List;

import java.util.List;

public class QuizEntryWithTags {
    private int quizEntryID;
    private String question;
    private String answer;
    private String description;
    private int groupID;
    private List<String> tags; // Updated to a list of tags

    // Constructors
    public QuizEntryWithTags(int quizEntryID, String question, String answer, String description, int groupID, List<String> tags) {
        this.quizEntryID = quizEntryID;
        this.question = question;
        this.answer = answer;
        this.description = description;
        this.groupID = groupID;
        this.tags = tags;
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

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}

package com.example.learnquest.QuizBank;

import java.util.List;

public class QuizEntryWithTags {
    private int quizEntryID;
    private String question;
    private String answer;
    private String description;
    private int groupID;
    private List<String> tags;

    public QuizEntryWithTags(int quizEntryID, String question, String answer, String description, int groupID, List<String> tags) {
        this.quizEntryID = quizEntryID;
        this.question = question;
        this.answer = answer;
        this.description = description;
        this.groupID = groupID;
        this.tags = tags;
    }

    // Getters
    public int getQuizEntryID() { return quizEntryID; }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public String getDescription() { return description; }
    public int getGroupID() { return groupID; }
    public List<String> getTags() { return tags; }
}

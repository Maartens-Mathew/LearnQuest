package com.example.learnquest.QuizBank;

public class QuizEntry {
    private int quizEntryID;
    private String question;
    private String answer;
    private String description;
    private int groupID;

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

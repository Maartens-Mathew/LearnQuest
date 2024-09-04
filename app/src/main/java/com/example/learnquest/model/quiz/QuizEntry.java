package com.example.learnquest.model.quiz;

import java.util.List;

public class QuizEntry {
    Short quizEntryID;
    String question;
    String answer;
    String description;
    Short groupID;
    Boolean isValidated;
    List<Tag> tags;

    public Short getQuizEntryID() {
        return quizEntryID;
    }

    public void setQuizEntryID(Short quizEntryID) {
        this.quizEntryID = quizEntryID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getGroupID() {
        return groupID;
    }

    public void setGroupID(Short groupID) {
        this.groupID = groupID;
    }

    public Boolean getValidated() {
        return isValidated;
    }

    public void setValidated(Boolean validated) {
        isValidated = validated;
    }

    public QuizEntry(Short quizEntryID, String question, String answer, String description, Short groupID, Boolean isValidated) {
        this.quizEntryID = quizEntryID;
        this.question = question;
        this.answer = answer;
        this.description = description;
        this.groupID = groupID;
        this.isValidated = isValidated;
    }
}

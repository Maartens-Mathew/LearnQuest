package com.example.learnquest.model.wrappers;

import com.google.gson.annotations.SerializedName;

import java.io.Serial;

public class TaggedQuiz {

    @SerializedName("quizEntryID")
    Integer quizEntryID;

    @SerializedName("tagID")
    Integer tagID;


    @SerializedName("question")
    String question;

    @SerializedName("answer")
    String answer;


    @SerializedName("description")
    String description;

    @SerializedName("tagName")
    String tagName;

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "TaggedQuiz{" +
                "answer='" + answer + '\'' +
                ", quizEntryID=" + quizEntryID +
                ", tagID=" + tagID +
                ", question='" + question + '\'' +
                ", description='" + description + '\'' +
                ", tagName='" + tagName + '\'' +
                '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getQuizEntryID() {
        return quizEntryID;
    }

    public void setQuizEntryID(Integer quizEntryID) {
        this.quizEntryID = quizEntryID;
    }

    public Integer getTagID() {
        return tagID;
    }

    public void setTagID(Integer tagID) {
        this.tagID = tagID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}

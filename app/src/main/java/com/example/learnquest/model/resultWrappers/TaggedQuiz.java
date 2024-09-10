package com.example.learnquest.model.resultWrappers;

import com.google.gson.annotations.SerializedName;

public class TaggedQuiz{

    @SerializedName("quizEntryID")
    public Integer quizEntryID;

    @SerializedName("tagID")
    public Integer tagID;

    @SerializedName("question")
    public String question;

    @SerializedName("answer")
    public String answer;

    @SerializedName("description")
    public String description;


    @SerializedName("tagName")
    public String tagName;

    @Override
    public String toString() {
        return "TaggedQuiz{" +
                "quizEntryID=" + quizEntryID +
                ", tagID=" + tagID +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", tagName='" + tagName + '\'' +
                '}';
    }

}
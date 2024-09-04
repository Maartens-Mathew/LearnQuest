package com.example.learnquest.model.resultWrappers;

public class TaggedQuiz{
    public short quizEntryID;
    public short tagID;
    public String question;
    public String answer;
    public String description;
    public boolean isValidated;

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
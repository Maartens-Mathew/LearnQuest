package com.example.learnquest.model.wrappers;

public class TaggedQuiz {

    Short quizEntryID;
    Short tagID;
    String question;
    String answer;
    String description;

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

    String tagName;

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

    public Short getQuizEntryID() {
        return quizEntryID;
    }

    public void setQuizEntryID(Short quizEntryID) {
        this.quizEntryID = quizEntryID;
    }

    public Short getTagID() {
        return tagID;
    }

    public void setTagID(Short tagID) {
        this.tagID = tagID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}

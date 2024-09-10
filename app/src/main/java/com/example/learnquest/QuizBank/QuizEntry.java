package com.example.learnquest.QuizBank;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuizEntry{
    private Integer quizEntryID;
    private String question;
    private String answer;
    private String description;

    public QuizEntry(){
        tags = new HashMap<>();
    }

    
    private HashMap<Integer, Tag> tags;

    @Override
    public String toString() {
        return "QuizEntry{" +
                "answer='" + answer + '\'' +
                ", quizEntryID=" + quizEntryID +
                ", question='" + question + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                '}';
    }

    public void addTag(Tag tag){
        tags.put(tag.getTagID(),tag);

    }

    public String getTags(){
        List<String> tagNames = new ArrayList<>();
        tags.forEach((tagID, tag) -> tagNames.add(tag.getTagName()));
        return TextUtils.join(",",tagNames);
    }

    public void setTags(List<Tag> tags){
        for(Tag tag: tags){
            this.tags.put(tag.getTagID(),tag);
        }
    }

    public void removeTag(Tag tag){
        tags.remove(tag.getTagID());
    }

    // Getters and Setters
    public Integer getQuizEntryID() { return quizEntryID; }
    public void setQuizEntryID(Integer quizEntryID) { this.quizEntryID = quizEntryID; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public QuizEntry(Integer quizEntryID,String question, String answer, String description ) {
        this.quizEntryID = quizEntryID;
        this.question = question;
        this.answer = answer;
        this.description = description;

        tags = new HashMap<>();


    }

}

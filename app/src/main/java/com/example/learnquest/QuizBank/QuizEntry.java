package com.example.learnquest.QuizBank;

import android.text.TextUtils;

import com.example.learnquest.AppState.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class QuizEntry{
    private Integer quizEntryID;
    private String question;
    private String answer;
    private String description;
    private Boolean inContention;
    private Boolean isValidated;


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

    public Boolean getInContention() {
        return inContention;
    }

    public void setInContention(Boolean inContention) {
        this.inContention = inContention;
    }

    public Boolean getIsValid() {
        return isValidated;
    }

    public void setValidated(Boolean validated) {
        isValidated = validated;
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

    public QuizEntry(Integer quizEntryID,String question, String answer, String description, Boolean inContention, Boolean isValidated ) {
        this.quizEntryID = quizEntryID;
        this.question = question;
        this.answer = answer;

        this.description = description;
        this.isValidated = false;
        this.inContention = false;




        tags = new HashMap<>();


    }

    public List<Integer> getTagsAsList(){
        return tags.keySet().stream().collect(Collectors.toList());
    }

    public void setGroupID(Integer integer) {

    }

    public String getGroupID() {
        return App.groupID.toString();//hmmmmmm
    }

//    public Integer[] getTagsAsList() {
//        return tags;
//    }
}

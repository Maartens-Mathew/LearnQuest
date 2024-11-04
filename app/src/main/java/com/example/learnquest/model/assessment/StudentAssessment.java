package com.example.learnquest.model.assessment;

public class StudentAssessment {

    Integer userID;
    Integer assessmentID;
    Float markObtained;
    Float idealMark;
    String feedBack;

    public Integer getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(Integer assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public Float getIdealMark() {
        return idealMark;
    }

    public void setIdealMark(Float idealMark) {
        this.idealMark = idealMark;
    }

    public Float getMarkObtained() {
        return markObtained;
    }

    public void setMarkObtained(Float markObtained) {
        this.markObtained = markObtained;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public StudentAssessment() {
    }

    public StudentAssessment(String feedBack, Float idealMark, Float markObtained, Integer userID) {
        this.feedBack = feedBack;
        this.idealMark = idealMark;
        this.markObtained = markObtained;
        this.userID = userID;
    }

    public StudentAssessment(Integer assessmentID, String feedBack, Float idealMark, Float markObtained, Integer userID) {
        this.assessmentID = assessmentID;
        this.feedBack = feedBack;
        this.idealMark = idealMark;
        this.markObtained = markObtained;
        this.userID = userID;
    }
}

package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

public class StudentAssessment {
    private int userID, assessmentID;
    private Float markObtained;
    private Float idealMark;
    private String feedback;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public Float getMarkObtained() {
        return markObtained;
    }

    public void setMarkObtained(Float markObtained) {
        this.markObtained = markObtained;
    }

    public Float getIdealMark() {
        return idealMark;
    }

    public void setIdealMark(Float idealMark) {
        this.idealMark = idealMark;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public StudentAssessment(int userID, int assessmentID, Float markObtained, Float idealMark, String feedback) {
        this.userID = userID;
        this.assessmentID = assessmentID;
        this.markObtained = markObtained;
        this.idealMark = idealMark;
        this.feedback = feedback;
    }
}

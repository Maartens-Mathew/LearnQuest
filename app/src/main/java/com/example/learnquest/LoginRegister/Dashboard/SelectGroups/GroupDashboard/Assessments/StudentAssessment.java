package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

public class StudentAssessment {
    private int userID, assessmentID;
    private float markObtained, idealMark;
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

    public float getMarkObtained() {
        return markObtained;
    }

    public void setMarkObtained(float markObtained) {
        this.markObtained = markObtained;
    }

    public float getIdealMark() {
        return idealMark;
    }

    public void setIdealMark(float idealMark) {
        this.idealMark = idealMark;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public StudentAssessment(int userID, int assessmentID, float markObtained, float idealMark, String feedback) {
        this.userID = userID;
        this.assessmentID = assessmentID;
        this.markObtained = markObtained;
        this.idealMark = idealMark;
        this.feedback = feedback;
    }
}

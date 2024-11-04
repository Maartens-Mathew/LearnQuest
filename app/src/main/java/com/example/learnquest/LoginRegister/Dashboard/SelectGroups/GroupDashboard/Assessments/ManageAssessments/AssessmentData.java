package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageAssessments;


import java.io.Serializable;

//class that will store the data from an SQL query that gets the weighting, ideal mark and mark obtained with a specific student id
public class AssessmentData implements Serializable {


    private float weighting, idealMark, markObtained;
    private String assessmentName;

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public AssessmentData(){}

    public AssessmentData(float weighting, float idealMark, float markObtained, String assessmentName) {
        this.weighting = weighting;
        this.idealMark = idealMark;
        this.markObtained = markObtained;
        this.assessmentName = assessmentName;
    }

    public float getWeighting() {
        return weighting;
    }

    public void setWeighting(float weighting) {
        this.weighting = weighting;
    }

    public float getIdealMark() {
        return idealMark;
    }

    public void setIdealMark(float idealMark) {
        this.idealMark = idealMark;
    }

    public float getMarkObtained() {
        return markObtained;
    }

    public void setMarkObtained(float markObtained) {
        this.markObtained = markObtained;
    }
}

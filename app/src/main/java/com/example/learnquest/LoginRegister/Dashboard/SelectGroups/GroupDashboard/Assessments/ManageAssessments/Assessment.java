package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageAssessments;

public class Assessment {
    private int assessmentID, groupID;
    private String name, dueDate;
    private float weighting;

    public Assessment(int assessmentID, int groupID, String name, String dueDate, float weighting) {
        this.assessmentID = assessmentID;
        this.groupID = groupID;
        this.name = name;
        this.dueDate = dueDate;
        this.weighting = weighting;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public float getWeighting() {
        return weighting;
    }

    public void setWeighting(float weighting) {
        this.weighting = weighting;
    }
}

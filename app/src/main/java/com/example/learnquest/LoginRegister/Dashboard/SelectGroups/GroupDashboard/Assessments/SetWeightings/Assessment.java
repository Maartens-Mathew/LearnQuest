package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.SetWeightings;
import java.io.Serializable;

public class Assessment implements Serializable {


    private String name;
    private String dueDate;
    private float weighting;
    private Integer groupID;

   // private int assessmentID; // Add this field



    public Assessment(String name, String date, float weighting, Integer groupID) {
        this.name = name;
        this.dueDate = date;
        this.weighting = weighting;
        this.groupID = groupID; // Example default groupID for testing

    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return dueDate;
    }

    public float getWeighting() {
        return weighting;
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

    public void setWeighting(float weighting) {
        this.weighting = weighting;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    @Override
    public String toString() {
        return name + " (" + weighting + "%)";
    }
}


package com.example.learnquest.model.assessment;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

public class Assessment implements Serializable {

    Integer assessmentID;
    String name;
    Date dueDate;
    Float weighting;
    Integer groupID;

    @Override
    public String toString() {
        return name + " " + "("+weighting+ ")";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Assessment){
            Assessment t = (Assessment) obj;
            return this.assessmentID.intValue() == t.assessmentID.intValue();
        }
        else{
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        int result = 9;
        result = 37*result + assessmentID;
        return result;
    }

    public Assessment(Date dueDate, Integer groupID, String name, Float weighting) {
        this.dueDate = dueDate;
        this.groupID = groupID;
        this.name = name;
        this.weighting = weighting;
    }

    public Assessment(Integer assessmentID, Date dueDate, Integer groupID, String name, Float weighting) {
        this.assessmentID = assessmentID;
        this.dueDate = dueDate;
        this.groupID = groupID;
        this.name = name;
        this.weighting = weighting;
    }

    public Assessment() {
    }

    public Integer getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(Integer assessmentID) {
        this.assessmentID = assessmentID;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeighting() {
        return weighting;
    }

    public void setWeighting(Float weighting) {
        this.weighting = weighting;
    }
}

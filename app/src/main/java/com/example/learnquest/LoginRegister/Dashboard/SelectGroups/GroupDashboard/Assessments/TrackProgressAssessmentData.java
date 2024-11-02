package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import java.io.Serializable;
import java.util.Date;

public class TrackProgressAssessmentData implements Serializable {
    private int assessment_id;
    private String name;
    private Double mark_obtained, ideal_mark;
    private float weight;
    private Date date_due;

    public int getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(int assessment_id) {
        this.assessment_id = assessment_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMark_obtained() {
        return mark_obtained;
    }

    public void setMark_obtained(Double mark_obtained) {
        this.mark_obtained = mark_obtained;
    }

    public Double getIdeal_mark() {
        return ideal_mark;
    }

    public void setIdeal_mark(Double ideal_mark) {
        this.ideal_mark = ideal_mark;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Date getDate_due() {
        return date_due;
    }

    public void setDate_due(Date date_due) {
        this.date_due = date_due;
    }

    public TrackProgressAssessmentData(int assessment_id, String name, Double mark_obtained, Double ideal_mark, float weight, Date date_due) {
        this.assessment_id = assessment_id;
        this.name = name;
        this.mark_obtained = mark_obtained;
        this.ideal_mark = ideal_mark;
        this.weight = weight;
        this.date_due = date_due;
    }
}

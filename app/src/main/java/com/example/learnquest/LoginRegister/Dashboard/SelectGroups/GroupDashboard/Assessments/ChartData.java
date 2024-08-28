package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;


//class that will store the data from an SQL query that gets the weighting, ideal mark and mark obtained with a specific student id
public class ChartData {
    private float weighting, idealMark, markObtained;

    public ChartData(){}

    public ChartData(float weighting, float idealMark, float markObtained) {
        this.weighting = weighting;
        this.idealMark = idealMark;
        this.markObtained = markObtained;
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

package com.example.learnquest.model.assessment;

public class StudentAssessment extends Assessment {
    private double idealMark, markObtained;
    private String feedback;

    public StudentAssessment(String name, String date, int weighting) {
        super(name, date, weighting);
    }
    //not sure what to do with keys
}

package com.example.learnquest.SetWeightings;

import java.util.ArrayList;
import java.util.List;

public class AssessmentManager {
    private static AssessmentManager instance;
    private List<Assessment> assessments;

    private AssessmentManager() {
        assessments = new ArrayList<>();
    }

    public static AssessmentManager getInstance() {
        if (instance == null) {
            instance = new AssessmentManager();
        }
        return instance;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    public void removeAssessment(int index) {
        assessments.remove(index);
    }

    public void clearAssessments() {
        assessments.clear();
    }
}

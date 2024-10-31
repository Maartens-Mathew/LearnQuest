package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.SetWeightings;

import java.util.ArrayList;
import java.util.List;

public class AssessmentManager {
    private List<Assessment> assessments = new ArrayList<>();
    private static final AssessmentManager instance = new AssessmentManager();

    private AssessmentManager() {}

    public static AssessmentManager getInstance() {
        return instance;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> newAssessments) {
        assessments.clear();
        assessments.addAll(newAssessments);
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    public void removeAssessment(int index) {
        if (index >= 0 && index < assessments.size()) {
            assessments.remove(index);
        }
    }
}


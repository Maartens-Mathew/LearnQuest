package com.example.learnquest.SetWeightings;
import java.io.Serializable;

public class Assessment implements Serializable {
    private String name;
    private String dueDate;
    private float weighting;
    private int groupID;

   // private int assessmentID; // Add this field



    public Assessment(String name, String date, float weighting) {
        this.name = name;
        this.dueDate = date;
        this.weighting = weighting;
        this.groupID = 1; // Example default groupID for testing

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


    @Override
    public String toString() {
        return name + " (" + weighting + "%)";
    }
}


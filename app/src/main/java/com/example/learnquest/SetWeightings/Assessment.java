package com.example.learnquest.SetWeightings;
import java.io.Serializable;

public class Assessment implements Serializable {
    private String name;
    private String date;
    private int weighting;

    public Assessment(String name, String date, int weighting) {
        this.name = name;
        this.date = date;
        this.weighting = weighting;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getWeighting() {
        return weighting;
    }

    @Override
    public String toString() {
        return name + " (" + weighting + "%)";
    }
}


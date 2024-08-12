package com.example.learnquest.SetWeightings;

public class Assessment {
    private String name;
    private String date;
    private double weighting;

    public Assessment(String name, String date, double weighting) {
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

    public double getWeighting() {
        return weighting;
    }

    @Override
    public String toString() {
        return name + " (" + date + ") - " + weighting + "%";
    }
}

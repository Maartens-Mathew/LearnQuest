package com.example.learnquest.model.assessment;

public class Assessment {
    protected String name;

    //Try to record as date
    protected String date;


    protected int weighting;

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
        return name + " (" + date + ") - " + weighting + "%";
    }
}

package com.example.learnquest.QuizBank;

public class TaggedQuizEntry {
    private int quizEntryID;
    private int tagID;

    // Constructor
    public TaggedQuizEntry(int quizEntryID, int tagID) {
        this.quizEntryID = quizEntryID;
        this.tagID = tagID;
    }

    // Getters
    public int getQuizEntryID() {
        return quizEntryID;
    }

    public int getTagID() {
        return tagID;
    }
}

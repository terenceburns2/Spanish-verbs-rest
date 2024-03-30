package com.terenceapps.spanishverbs.model;

public class Verb {
    private String infinitive;
    private String mood;
    private String tense;

    @Override
    public String toString() {
        return "Verb{" +
                "infinitive='" + infinitive + '\'' +
                ", mood='" + mood + '\'' +
                ", tense='" + tense + '\'' +
                '}';
    }

    public Verb(String infinitive, String mood, String tense) {
        this.infinitive = infinitive;
        this.mood = mood;
        this.tense = tense;
    }

    public String getInfinitive() {
        return infinitive;
    }

    public void setInfinitive(String infinitive) {
        this.infinitive = infinitive;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getTense() {
        return tense;
    }

    public void setTense(String tense) {
        this.tense = tense;
    }
}

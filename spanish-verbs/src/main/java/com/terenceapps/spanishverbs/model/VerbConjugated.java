package com.terenceapps.spanishverbs.model;

public class VerbConjugated {
    private String infinitive;
    private String mood;
    private String tense;
    private String verbEnglish;
    private String form1s;
    private String form2s;
    private String form3s;
    private String form1p;
    private String form2p;
    private String form3p;

    @Override
    public String toString() {
        return "VerbConjugated{" +
                "infinitive='" + infinitive + '\'' +
                ", mood='" + mood + '\'' +
                ", tense='" + tense + '\'' +
                ", verbEnglish='" + verbEnglish + '\'' +
                ", form1s='" + form1s + '\'' +
                ", form2s='" + form2s + '\'' +
                ", form3s='" + form3s + '\'' +
                ", form1p='" + form1p + '\'' +
                ", form2p='" + form2p + '\'' +
                ", form3p='" + form3p + '\'' +
                '}';
    }

    public VerbConjugated() {
    }

    public VerbConjugated(String infinitive, String mood, String tense, String verbEnglish, String form1s, String form2s, String form3s, String form1p, String form2p, String form3p) {
        this.infinitive = infinitive;
        this.mood = mood;
        this.tense = tense;
        this.verbEnglish = verbEnglish;
        this.form1s = form1s;
        this.form2s = form2s;
        this.form3s = form3s;
        this.form1p = form1p;
        this.form2p = form2p;
        this.form3p = form3p;
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

    public String getVerbEnglish() {
        return verbEnglish;
    }

    public void setVerbEnglish(String verbEnglish) {
        this.verbEnglish = verbEnglish;
    }

    public String getForm1s() {
        return form1s;
    }

    public void setForm1s(String form1s) {
        this.form1s = form1s;
    }

    public String getForm2s() {
        return form2s;
    }

    public void setForm2s(String form2s) {
        this.form2s = form2s;
    }

    public String getForm3s() {
        return form3s;
    }

    public void setForm3s(String form3s) {
        this.form3s = form3s;
    }

    public String getForm1p() {
        return form1p;
    }

    public void setForm1p(String form1p) {
        this.form1p = form1p;
    }

    public String getForm2p() {
        return form2p;
    }

    public void setForm2p(String form2p) {
        this.form2p = form2p;
    }

    public String getForm3p() {
        return form3p;
    }

    public void setForm3p(String form3p) {
        this.form3p = form3p;
    }
}
package com.example.intrims;

public class Candidature {

    private String title;
    private String company;
    private String description;
    private String date;
    private String lieu;
    private String etat;

    // Constructeursss
    public Candidature(String title, String company, String description , String date , String lieu , String etat) {
        this.title = title;
        this.company = company;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.etat = etat;

    }

    public Candidature(String nomOffre, String etat) {
        this.title = nomOffre;
        this.etat = etat;

    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }
    public String getDate() {
        return date;
    }
    public String getLieu() {
        return lieu;
    }

    public String getEtat() {
        return etat;
    }

}

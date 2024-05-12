package com.example.intrims;

public class Candidature {

    private String title;
    private String company;
    private String description;

    // Constructeur
    public Candidature(String title, String company, String description) {
        this.title = title;
        this.company = company;
        this.description = description;
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
}

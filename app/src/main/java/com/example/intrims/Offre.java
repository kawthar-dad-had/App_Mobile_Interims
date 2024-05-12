package com.example.intrims;

public class Offre {
    private String title;
    private String company;
    private String description;

    // Constructeur
    public Offre(String title, String company, String description) {
        this.title = title;
        this.company = company;
        this.description = description;
    }

    // Méthode pour obtenir le titre de l'offre
    public String getTitle() {
        return title;
    }

    // Méthode pour obtenir le nom de l'entreprise
    public String getCompany() {
        return company;
    }

    // Méthode pour obtenir la description de l'offre
    public String getDescription() {
        return description;
    }
}

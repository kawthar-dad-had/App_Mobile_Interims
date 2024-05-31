package com.example.intrims;

public class Offre {
    private String title;
    private String subtitle;

    private String company;
    private String date;
    private String description;

    // Constructeur
    public Offre(String title, String company, String description , String subtitle , String date) {
        this.title = title;
        this.company = company;
        this.description = description;
        this.subtitle = subtitle;
        this.date = date;
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
    public String getSubtitle() {
        return subtitle;
    }
    public String getDate() {
        return date;
    }
}

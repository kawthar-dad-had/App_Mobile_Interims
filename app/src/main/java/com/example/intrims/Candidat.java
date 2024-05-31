package com.example.intrims;

public class Candidat {
    private String email;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String ville;
    private String nationalite;
    private String phone;
    private String cv;
    private String lettreMotivation;

    public Candidat(String email, String nom, String prenom, String dateNaissance, String ville, String nationalite, String phone, String cv, String lettreMotivation) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.ville = ville;
        this.nationalite = nationalite;
        this.phone = phone;
        this.cv = cv;
        this.lettreMotivation = lettreMotivation;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public String getNationalite() {
        return nationalite;
    }

    public String getVille() {
        return ville;
    }

    public String getPhone() {
        return phone;
    }

    public String getCv() {
        return cv;
    }

    public String getLettreMotivation() {
        return lettreMotivation;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public void setLettreMotivation(String lettreMotivation) {
        this.lettreMotivation = lettreMotivation;
    }
}

    // Getters et setters
    // ...


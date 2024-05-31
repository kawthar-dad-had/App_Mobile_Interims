package com.example.intrims;

public class Employee {
    private String email;
    private String entreprise;
    private String phone;
    private String adresse;
    private String lienPublic;

    // Constructor
    public Employee(String email, String entreprise, String phone, String adresse, String lienPublic) {
        this.email = email;
        this.entreprise = entreprise;
        this.phone = phone;
        this.adresse = adresse;
        this.lienPublic = lienPublic;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getLienPublic() {
        return lienPublic;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setLienPublic(String lienPublic) {
        this.lienPublic = lienPublic;
    }
}

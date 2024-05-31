package com.example.intrims;

public class CandidatureEmployee {
    private String nomPrenom;
    private String nomOffre;
    private String dateCandidature;
    private String etatCandidature;
    // Ajout de l'état de la candidature
    private String cv;

    public CandidatureEmployee(String nomPrenom, String nomOffre, String dateCandidature, String etatCandidature , String cv) {
        this.nomPrenom = nomPrenom;
        this.nomOffre = nomOffre;
        this.dateCandidature = dateCandidature;
        this.etatCandidature = etatCandidature;
        this.cv = cv;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public String getNomOffre() {
        return nomOffre;
    }

    public void setNomOffre(String nomOffre) {
        this.nomOffre = nomOffre;
    }

    public String getDateCandidature() {
        return dateCandidature;
    }

    public void setDateCandidature(String dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    public String getEtatCandidature() {
        return etatCandidature;
    }
    public String getEtatCV() {
        return cv;
    }

    public void setEtatCandidature(String etatCandidature) {
        this.etatCandidature = etatCandidature;
    }
}

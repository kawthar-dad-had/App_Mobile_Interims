package com.example.intrims;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 8;
    public static int test;

    // Tables and columns
    public static final String TABLE_COMPTE = "compte";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";

    public static final String TABLE_CANDIDAT = "candidat";
    public static final String COLUMN_NOM = "nom";
    public static final String COLUMN_PRENOM = "prenom";
    public static final String COLUMN_DATE_NAISSANCE = "date_naissance";
    public static final String COLUMN_VILLE= "ville";
    public static final String COLUMN_NATIONALITE = "nationalite";
    public static final String COLUMN_PHONE= "phone";
    public static final String COLUMN_CV = "cv";
    public static final String COLUMN_LETTRE_MOTIVATION = "lettre_motivation";

    public static final String TABLE_EMPLOYEE = "employee";
    public static final String COLUMN_ENTREPRISE = "nom_entreprise";
    public static final String COLUMN_ADRESSE = "adresse";
    public static final String COLUMN_LIEN_PUBLIC = "lien_public";

    public static final String TABLE_OFFRE = "offre";
    public static final String COLUMN_NOM_OFFRE = "nom_offre";
    public static final String COLUMN_SOUS_TITRE = "sous_titre";
    public static final String COLUMN_DATE_PUBLICATION = "date_publication";
    public static final String COLUMN_DESCRIPTION = "description";

    public static final String TABLE_CANDIDATURE = "candidature";
    public static final String COLUMN_ETAT = "etat";


    public static final String TABLE_FAVORIS = "favoris";

    public static final String TABLE_NOTIFICATION = "notification";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DESCRIPTION_NOTIFICATION = "description";
    public static final String COLUMN_EMAIL_EMPLOYEE = "email_employee";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créer les tables
        String createTableCompte = "CREATE TABLE " + TABLE_COMPTE + " (" +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ROLE + " TEXT)";
        db.execSQL(createTableCompte);

        String createTableCandidat = "CREATE TABLE " + TABLE_CANDIDAT + " (" +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                COLUMN_NOM + " TEXT, " +
                COLUMN_PRENOM + " TEXT, " +
                COLUMN_DATE_NAISSANCE + " TEXT, " +
                COLUMN_NATIONALITE + " TEXT, " +
                COLUMN_VILLE + " TEXT, " +
                COLUMN_PHONE+ " TEXT, " +
                COLUMN_CV + " TEXT, " +
                COLUMN_LETTRE_MOTIVATION + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_EMAIL + ") REFERENCES " + TABLE_COMPTE + "(" + COLUMN_EMAIL + "))";
        db.execSQL(createTableCandidat);

        String createTableEmployee = "CREATE TABLE " + TABLE_EMPLOYEE + " (" +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                COLUMN_ENTREPRISE + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_ADRESSE + " TEXT, " +
                COLUMN_LIEN_PUBLIC + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_EMAIL + ") REFERENCES " + TABLE_COMPTE + "(" + COLUMN_EMAIL + "))";
        db.execSQL(createTableEmployee);

        String createTableOffre = "CREATE TABLE " + TABLE_OFFRE + " (" +
                COLUMN_NOM_OFFRE + " TEXT PRIMARY KEY, " +
                COLUMN_SOUS_TITRE + " TEXT, " +
                COLUMN_DATE_PUBLICATION + " TEXT, " +
                COLUMN_ENTREPRISE + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_EMAIL + ") REFERENCES " + TABLE_EMPLOYEE + "(" + COLUMN_EMAIL + "))";
        db.execSQL(createTableOffre);

        String createTableCandidature = "CREATE TABLE " + TABLE_CANDIDATURE + " (" +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_NOM_OFFRE + " TEXT, " +
                COLUMN_ETAT + " TEXT, " +
                "date_candidature TEXT DEFAULT CURRENT_DATE, " + // Nouvelle colonne pour la date de candidature
                "FOREIGN KEY(" + COLUMN_EMAIL + ") REFERENCES " + TABLE_CANDIDAT + "(" + COLUMN_EMAIL + "), " +
                "FOREIGN KEY(" + COLUMN_NOM_OFFRE + ") REFERENCES " + TABLE_OFFRE + "(" + COLUMN_NOM_OFFRE + "))";
        db.execSQL(createTableCandidature);


        String createTableFavoris = "CREATE TABLE " + TABLE_FAVORIS + " (" +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_NOM_OFFRE + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_EMAIL + ") REFERENCES " + TABLE_CANDIDAT + "(" + COLUMN_EMAIL + "), " +
                "FOREIGN KEY(" + COLUMN_NOM_OFFRE + ") REFERENCES " + TABLE_OFFRE + "(" + COLUMN_NOM_OFFRE + "))";
        db.execSQL(createTableFavoris);
        String createTableNotification = "CREATE TABLE " + TABLE_NOTIFICATION + " (" +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_DESCRIPTION_NOTIFICATION + " TEXT, " +
                COLUMN_EMAIL_EMPLOYEE + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_EMAIL_EMPLOYEE + ") REFERENCES " + TABLE_EMPLOYEE + "(" + COLUMN_EMAIL + "))";
        db.execSQL(createTableNotification);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Supprimer les anciennes tables si elles existent
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDIDATURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFRE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDIDAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPTE);
        onCreate(db);
    }

    // Ajoutez ici les fonctions d'insertion, de mise à jour et de suppression pour chaque table.
    // Exemple pour la table Compte :
    public boolean insertCompte(String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_ROLE, role);
        long result = db.insert(TABLE_COMPTE, null, contentValues);
        return result != -1; // retourne true si l'insertion est réussie, sinon false
    }

    public boolean insertCandidat(String email, String nom, String prenom, String dateNaissance, String nationalite, String ville, String phone, String cv, String lettre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_NOM, nom);
        contentValues.put(COLUMN_PRENOM, prenom);
        contentValues.put(COLUMN_DATE_NAISSANCE, dateNaissance);
        contentValues.put(COLUMN_NATIONALITE, nationalite);
        contentValues.put(COLUMN_VILLE, ville);
        contentValues.put(COLUMN_PHONE, phone);
        contentValues.put(COLUMN_CV, cv);
        contentValues.put(COLUMN_LETTRE_MOTIVATION, lettre);

        long result = db.insert(TABLE_CANDIDAT, null, contentValues);


        return result != -1; // retourne true si l'insertion est réussie, sinon false
    }

    public boolean checkUserCredentials(String email, String password, String role) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_EMAIL };
        String selection = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ? AND " + COLUMN_ROLE + " = ?";
        String[] selectionArgs = { email, password, role };

        Cursor cursor = db.query(TABLE_COMPTE, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }

    public List<Offre> getAllOffres() {
        List<Offre> offres = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_OFFRE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nomOffre = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_OFFRE));
                @SuppressLint("Range") String sousTitre = cursor.getString(cursor.getColumnIndex(COLUMN_SOUS_TITRE));
                @SuppressLint("Range") String datePublication = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_PUBLICATION));
                @SuppressLint("Range") String entreprise = cursor.getString(cursor.getColumnIndex(COLUMN_ENTREPRISE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));

                Offre offre = new Offre(nomOffre, entreprise, description , sousTitre , datePublication);
                offres.add(offre);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return offres;
    }


    public boolean insertOffre(String nomOffre, String sousTitre, String datePublication, String entreprise,String email, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOM_OFFRE, nomOffre);
        contentValues.put(COLUMN_SOUS_TITRE, sousTitre);
        contentValues.put(COLUMN_DATE_PUBLICATION, datePublication);
        contentValues.put(COLUMN_ENTREPRISE, entreprise);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_DESCRIPTION, description);
        long result = db.insert(TABLE_OFFRE, null, contentValues);
        return result != -1; // retourne true si l'insertion est réussie, sinon false
    }

    public Candidat getCandidatByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Candidat candidat = null;
        String selectQuery = "SELECT * FROM " + TABLE_CANDIDAT + " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String nom = cursor.getString(cursor.getColumnIndex(COLUMN_NOM));
            @SuppressLint("Range") String prenom = cursor.getString(cursor.getColumnIndex(COLUMN_PRENOM));
            @SuppressLint("Range") String dateNaissance = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_NAISSANCE));
            @SuppressLint("Range") String ville = cursor.getString(cursor.getColumnIndex(COLUMN_VILLE));
            @SuppressLint("Range") String nationalite = cursor.getString(cursor.getColumnIndex(COLUMN_NATIONALITE));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
            @SuppressLint("Range") String cv = cursor.getString(cursor.getColumnIndex(COLUMN_CV));
            @SuppressLint("Range") String lettreMotivation = cursor.getString(cursor.getColumnIndex(COLUMN_LETTRE_MOTIVATION));

            candidat = new Candidat(email, nom, prenom, dateNaissance, ville, nationalite, phone, cv, lettreMotivation);
        }

        cursor.close();
        db.close();

        return candidat;
    }

    public boolean insertEmployee(String email, String nomEntreprise, String phone, String adresse, String lienPublic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_ENTREPRISE, nomEntreprise);
        contentValues.put(COLUMN_PHONE, phone);
        contentValues.put(COLUMN_ADRESSE, adresse);
        contentValues.put(COLUMN_LIEN_PUBLIC, lienPublic);

        long result = db.insert(TABLE_EMPLOYEE, null, contentValues);
        return result != -1; // retourne true si l'insertion est réussie, sinon false
    }

    public Employee getEmployeeByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Employee employee = null;
        String selectQuery = "SELECT * FROM " + TABLE_EMPLOYEE + " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String nomEntreprise = cursor.getString(cursor.getColumnIndex(COLUMN_ENTREPRISE));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE));
            @SuppressLint("Range") String adresse = cursor.getString(cursor.getColumnIndex(COLUMN_ADRESSE));
            @SuppressLint("Range") String lienPublic = cursor.getString(cursor.getColumnIndex(COLUMN_LIEN_PUBLIC));

            // Créez un objet Employee avec les données récupérées
            employee = new Employee(email, nomEntreprise, phone, adresse, lienPublic);
        }

        cursor.close();
        db.close();

        return employee;
    }

    public List<Offre> getOffresByEntreprise(String nomEntreprise) {
        List<Offre> offres = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_OFFRE + " WHERE " + COLUMN_ENTREPRISE + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{nomEntreprise});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nomOffre = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_OFFRE));
                @SuppressLint("Range") String sousTitre = cursor.getString(cursor.getColumnIndex(COLUMN_SOUS_TITRE));
                @SuppressLint("Range") String datePublication = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_PUBLICATION));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));

                // Créez un objet Offre avec les données récupérées
                Offre offre = new Offre(nomOffre, nomEntreprise, description, sousTitre, datePublication);
                // Ajoutez l'offre à la liste des offres
                offres.add(offre);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return offres;
    }
    public boolean insertCandidature(String email, String nomOffre, String etat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_NOM_OFFRE, nomOffre);
        contentValues.put(COLUMN_ETAT, etat); // Ajoutez l'état de la candidature
        long result = db.insert(TABLE_CANDIDATURE, null, contentValues);
        return result != -1;
    }
    public List<Candidature> getCandidaturesByEmailWithOffres(String email) {
        List<Candidature> candidaturesByEmail = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectCandidaturesQuery = "SELECT * FROM " + TABLE_CANDIDATURE + " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursorCandidatures = db.rawQuery(selectCandidaturesQuery, new String[]{email});

        if (cursorCandidatures.moveToFirst()) {
            do {
                String nomOffre = cursorCandidatures.getString(cursorCandidatures.getColumnIndexOrThrow(COLUMN_NOM_OFFRE));
                String etat = cursorCandidatures.getString(cursorCandidatures.getColumnIndexOrThrow(COLUMN_ETAT));

                Offre offre = getOffreByNom(db, nomOffre);

                Candidature candidature = new Candidature(nomOffre, offre.getSubtitle(), offre.getDate(), offre.getCompany(), offre.getDescription(), etat);
                candidaturesByEmail.add(candidature);
            } while (cursorCandidatures.moveToNext());
        }

        cursorCandidatures.close();
        db.close();

        return candidaturesByEmail;
    }

    public List<Candidature> getFavorisByEmail(String email) {
        List<Candidature> favorisByEmail = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectFavorisQuery = "SELECT * FROM " + TABLE_FAVORIS + " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursorFavoris = db.rawQuery(selectFavorisQuery, new String[]{email});

        if (cursorFavoris.moveToFirst()) {
            do {
                String nomOffre = cursorFavoris.getString(cursorFavoris.getColumnIndexOrThrow(COLUMN_NOM_OFFRE));

                Offre offre = getOffreByNom(db, nomOffre);

                Candidature candidature = new Candidature(nomOffre, offre.getSubtitle(), offre.getDate(), offre.getCompany(), offre.getDescription(), "");
                favorisByEmail.add(candidature);
            } while (cursorFavoris.moveToNext());
        }

        cursorFavoris.close();
        db.close();

        return favorisByEmail;
    }

    private Offre getOffreByNom(SQLiteDatabase db, String nomOffre) {
        String selectOffreQuery = "SELECT * FROM " + TABLE_OFFRE + " WHERE " + COLUMN_NOM_OFFRE + " = ?";
        Cursor cursorOffre = db.rawQuery(selectOffreQuery, new String[]{nomOffre});

        Offre offre = null;
        if (cursorOffre.moveToFirst()) {
            String sousTitre = cursorOffre.getString(cursorOffre.getColumnIndexOrThrow(COLUMN_SOUS_TITRE));
            String datePublication = cursorOffre.getString(cursorOffre.getColumnIndexOrThrow(COLUMN_DATE_PUBLICATION));
            String entreprise = cursorOffre.getString(cursorOffre.getColumnIndexOrThrow(COLUMN_ENTREPRISE));
            String description = cursorOffre.getString(cursorOffre.getColumnIndexOrThrow(COLUMN_DESCRIPTION));

            offre = new Offre(nomOffre, sousTitre, datePublication, entreprise, description);
        }

        cursorOffre.close();
        return offre;
    }

    public boolean insertFavoris(String email, String nomOffre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_NOM_OFFRE, nomOffre);
        long result = db.insert(TABLE_FAVORIS, null, contentValues);
        return result != -1;
    }

    public List<String> getNomOffresByEmail(String email) {
        List<String> nomOffres = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " + COLUMN_NOM_OFFRE + " FROM " + TABLE_OFFRE + " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nomOffre = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_OFFRE));
                nomOffres.add(nomOffre);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return nomOffres;
    }

    public List<Candidature> getCandidatureByOffre(String nomOffre) {
        List<Candidature> candidatures = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CANDIDATURE + " WHERE " + COLUMN_NOM_OFFRE + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{nomOffre});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                @SuppressLint("Range") String etat = cursor.getString(cursor.getColumnIndex(COLUMN_ETAT));
                @SuppressLint("Range") String dateCandidature = cursor.getString(cursor.getColumnIndex("date_candidature"));

                Candidat candidat = getCandidatByEmail(email);

                if (candidat != null) {
                    Candidature candidature = new Candidature(nomOffre, candidat.getNom(), candidat.getPrenom(), dateCandidature, etat, email);
                    candidatures.add(candidature);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return candidatures;
    }


    public boolean deleteCompte(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_COMPTE, COLUMN_EMAIL + " = ?", new String[]{email}) > 0;
    }

    public List<CandidatureEmployee> getAllCandidaturesWithCandidates() {
        List<CandidatureEmployee> candidaturesWithCandidates = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CANDIDATURE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                @SuppressLint("Range") String nomOffre = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_OFFRE));
                @SuppressLint("Range") String etat = cursor.getString(cursor.getColumnIndex(COLUMN_ETAT));
                @SuppressLint("Range") String dateCandidature = cursor.getString(cursor.getColumnIndex("date_candidature")); // Obtenez la date de candidature

                Candidat candidat = getCandidatByEmail(email);

                if (candidat != null) {
                    CandidatureEmployee candidatureEmployee = new CandidatureEmployee(candidat.getNom() + " " + candidat.getPrenom(), nomOffre, dateCandidature, etat , candidat.getCv());
                    candidaturesWithCandidates.add(candidatureEmployee);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return candidaturesWithCandidates;
    }


    public boolean updateEtatCandidatureByOffre(String nomOffre, String nouvelEtat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ETAT, nouvelEtat);
        String selection = COLUMN_NOM_OFFRE + " = ?";
        String[] selectionArgs = { nomOffre };
        int rowsAffected = db.update(TABLE_CANDIDATURE, contentValues, selection, selectionArgs);
        return rowsAffected > 0;
    }

    public boolean insertNotificationWithEmployeeEmail(String title, String date, String description, String employeeEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_DESCRIPTION, description);
        contentValues.put(COLUMN_EMAIL_EMPLOYEE, employeeEmail);
        long result = db.insert(TABLE_NOTIFICATION, null, contentValues);
        return result != -1;
    }

    public List<Notification> getAllNotificationsWithEmployeeEmail() {
        List<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NOTIFICATION +"";
        Cursor cursor = db.rawQuery(selectQuery , null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                Notification notification = new Notification(title, date, description);
                notifications.add(notification);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notifications;
    }



    // Ajoutez des fonctions similaires pour les autres tables.
}

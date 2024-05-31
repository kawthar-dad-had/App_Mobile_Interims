package com.example.intrims;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class InscriptionEmployee extends AppCompatActivity {

    String email;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscription_employee);
        db = new DatabaseHelper(this);

        // Récupérer l'email de l'activité précédente
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        EditText entreprise = findViewById(R.id.entreprise);
        EditText adresse = findViewById(R.id.adresse);
        EditText lien = findViewById(R.id.lien);
        EditText phone = findViewById(R.id.phone);
        Button terminer = findViewById(R.id.terminerButton);
        terminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entreprise1 = entreprise.getText().toString().trim();
                String adresse1 = adresse.getText().toString().trim();
                String lien1 = lien.getText().toString().trim();
                String phone1 = phone.getText().toString().trim();


                if (entreprise1.isEmpty() || adresse1.isEmpty() || lien1.isEmpty() || phone1.isEmpty()) {
                    Toast.makeText(InscriptionEmployee.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = db.insertEmployee(email, entreprise1 , adresse1 , lien1 , phone1);
                    //boolean isInserted = db.insertCandidat(email1, nom, prenom, dateNaissance, nationalite, ville, phone, cv, lettre);

                    if (isInserted) {
                        Toast.makeText(InscriptionEmployee.this, "Inscription du candidat réussie", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(InscriptionEmployee.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(InscriptionEmployee.this, "Erreur lors de l'inscription du candidat ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
package com.example.intrims;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intrims.DatabaseHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class    InscriptionCandidat extends AppCompatActivity {

    DatabaseHelper db;
    String email;
    private static final int PICK_CV_REQUEST = 1;
    private static final int PICK_LETTER_REQUEST = 2;
    private Uri cvUri;
    private Uri lettreUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_candidat);

        db = new DatabaseHelper(this);

        // Récupérer l'email de l'activité précédente
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        EditText nomEditText = findViewById(R.id.nom);
        EditText prenomEditText = findViewById(R.id.prenom);
        EditText dateEditText = findViewById(R.id.date);
        EditText nationaliteEditText = findViewById(R.id.nationalite);
        EditText villeEditText = findViewById(R.id.ville);
        EditText phoneEditText = findViewById(R.id.phone);
        Button buttonLoadCV = findViewById(R.id.buttonLoadCV);
        Button buttonLoadLettre = findViewById(R.id.buttonLoadLettre);
        Button terminer = findViewById(R.id.terminerButton);

        buttonLoadCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_CV_REQUEST);
            }
        });

        buttonLoadLettre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_LETTER_REQUEST);
            }
        });

        terminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = nomEditText.getText().toString().trim();
                String prenom = prenomEditText.getText().toString().trim();
                String dateNaissance = dateEditText.getText().toString().trim();
                String nationalite = nationaliteEditText.getText().toString().trim();
                String ville = villeEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String cv = "";
                String lettre = "";

                try {
                    if (cvUri != null) {
                        cv = readTextFromUri(cvUri);
                        Toast.makeText(InscriptionCandidat.this, cv, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(InscriptionCandidat.this, (CharSequence) cvUri, Toast.LENGTH_SHORT).show();


                    }
                    if (lettreUri != null) {
                        lettre = readTextFromUri(lettreUri);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(InscriptionCandidat.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

                if (nom.isEmpty() || prenom.isEmpty() || dateNaissance.isEmpty() || nationalite.isEmpty() || ville.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(InscriptionCandidat.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = db.insertCandidat(email, nom, prenom, dateNaissance, nationalite, ville, phone, String.valueOf(cvUri), String.valueOf(lettreUri));
                    //boolean isInserted = db.insertCandidat(email1, nom, prenom, dateNaissance, nationalite, ville, phone, cv, lettre);

                    Toast.makeText(InscriptionCandidat.this, isInserted+email+nom+ prenom+ dateNaissance+ nationalite+ville+phone+ cv+ lettre, Toast.LENGTH_SHORT).show();

                    if (isInserted) {
                        Toast.makeText(InscriptionCandidat.this, "Inscription du candidat réussie", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(InscriptionCandidat.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(InscriptionCandidat.this, "Erreur lors de l'inscription du candidat kawthar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void openFileChooser(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == PICK_CV_REQUEST) {
                cvUri = data.getData();
            } else if (requestCode == PICK_LETTER_REQUEST) {
                lettreUri = data.getData();
            }
        }
    }

    private String readTextFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        inputStream.close();
        return stringBuilder.toString();
    }
}


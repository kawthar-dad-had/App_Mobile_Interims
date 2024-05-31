package com.example.intrims;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    Spinner spinner;
    String selectedOption;
    public static String roleVerifie;

    public static String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        spinner = findViewById(R.id.spinner);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        dbHelper.insertOffre("Développeur Android", "Développement Mobile", "2023-05-01", "Entreprise XYZ","dadouahadriak@gmail.com", "Nous recherchons un développeur Android qualifié pour rejoindre notre équipe.");
        dbHelper.insertOffre("Ingénieur DevOps", "Infrastructure", "2023-06-01","dadouahadriak@gmail.com", "Entreprise ABC", "Nous recherchons un ingénieur DevOps pour maintenir nos infrastructures et automatiser nos processus.");
        dbHelper.insertOffre("Développeur Web Frontend", "Développement Web", "2023-07-01", "dadouahadriak@gmail.com","Startup XYZ", "Nous recherchons un développeur web frontend pour travailler sur nos projets clients.");
        dbHelper.insertOffre("Data Scientist", "Analyse de Données", "2023-08-01","dadouahadriak@gmail.com", "Entreprise ABC", "Nous recherchons un data scientist pour analyser et interpréter nos données.");
        dbHelper.insertOffre("Chef de Projet IT", "Gestion de Projet", "2023-09-01", "dadouahadriak@gmail.com","Entreprise XYZ", "Nous recherchons un chef de projet IT pour coordonner nos équipes de développement et assurer la livraison des projets.");


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = (String) parent.getItemAtPosition(position);
                if (selectedOption.equals("Anonyme")) {
                    username.setEnabled(false);
                    password.setEnabled(false);
                    username.setHintTextColor(getResources().getColor(R.color.gray));
                    password.setHintTextColor(getResources().getColor(R.color.gray));
                } else {
                    username.setEnabled(true);
                    password.setEnabled(true);
                    username.setHintTextColor(getResources().getColor(R.color.purple));
                    password.setHintTextColor(getResources().getColor(R.color.purple));
                    if(selectedOption.equals("Candidat")){
                        username.setHint("Email");
                    }else {
                        username.setHint("Entreprise");
                    };
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Faites quelque chose ici si aucune option n'est sélectionnée.
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    handleLogin();
            }
        });

        TextView signupNowText = findViewById(R.id.signupText);
        signupNowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Activer la localisation")
                .setMessage("La localisation est désactivée. Veuillez l'activer pour continuer.")
                .setPositiveButton("Activer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 1);
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void handleLogin() {
        DatabaseHelper db = new DatabaseHelper(this);

        if (selectedOption.equals("Anonyme")) {
            roleVerifie = "Anonyme";
            if (!isLocationEnabled()) {
                showLocationDialog();
            } else {
                Toast.makeText(LoginActivity.this, selectedOption, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivityAnonymes.class);
                startActivity(intent);
            }
        } else {
            email = username.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String role = selectedOption;
            roleVerifie = role;

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isAuthenticated = db.checkUserCredentials(email, pass, role);

            if (isAuthenticated) {
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                navigateToMainActivity();
            } else {
                Toast.makeText(LoginActivity.this, "Email, mot de passe ou rôle incorrect", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToMainActivity() {
        Intent intent;
        if (selectedOption.equals("Candidat")) {
            intent = new Intent(LoginActivity.this, MainActivityCandidat.class);
        } else if (selectedOption.equals("Employeur")) {
            intent = new Intent(LoginActivity.this, MainActivityEmployeur.class);
        } else {
            intent = new Intent(LoginActivity.this, MainActivityAnonymes.class);
        }
        startActivity(intent);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (isLocationEnabled()) {
                handleLogin();
            } else {
                Toast.makeText(this, "Localisation non activée, impossible de continuer", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

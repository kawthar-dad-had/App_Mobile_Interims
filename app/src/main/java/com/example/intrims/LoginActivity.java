package com.example.intrims;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    Spinner spinner;
    String selectedOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        spinner = findViewById(R.id.spinner);

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
                    // Changer la couleur du hint du EditText username
                    username.setHintTextColor(getResources().getColor(R.color.gray));
                    // Changer la couleur du hint du EditText password
                    password.setHintTextColor(getResources().getColor(R.color.gray));
                } else {
                    username.setEnabled(true);
                    password.setEnabled(true);
                    // Changer la couleur du hint du EditText username
                    username.setHintTextColor(getResources().getColor(R.color.purple));
                    // Changer la couleur du hint du EditText password
                    password.setHintTextColor(getResources().getColor(R.color.purple));
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
                if (username.isEnabled() && password.isEnabled() && username.getText().toString().equals("kawthar") && password.getText().toString().equals("kawthar")) {
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    if (selectedOption.equals("Anonyme")) {
                        Intent intent = new Intent(LoginActivity.this, MainActivityAnonymes.class);
                        startActivity(intent);
                    } else if(selectedOption.equals("Candidat")){
                        Intent intent = new Intent(LoginActivity.this, MainActivityCandidat.class);
                        startActivity(intent);
                    } else if(selectedOption.equals("Employeur")){
                        Intent intent = new Intent(LoginActivity.this, MainActivityEmployeur.class);
                        startActivity(intent);
                    }

                } else {
                    if (selectedOption.equals("Anonyme")) {
                        Toast.makeText(LoginActivity.this, selectedOption, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivityAnonymes.class);
                        startActivity(intent);
                    } else if(selectedOption.equals("Candidat")){
                        Toast.makeText(LoginActivity.this, selectedOption, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivityCandidat.class);
                        startActivity(intent);
                    } else if(selectedOption.equals("Employeur")){
                        Toast.makeText(LoginActivity.this, selectedOption, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivityEmployeur.class);
                        startActivity(intent);
                    }
                }
            }
        });
        TextView signupNowText = findViewById(R.id.signupText);
        signupNowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajoutez ici le code que vous souhaitez exécuter lorsque "SignUp Now" est cliqué
                // Par exemple, vous pouvez lancer une nouvelle activité pour l'inscription
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

}

package com.example.intrims;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InscriptionCandidat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscription_candidat);

        Button terminer = findViewById(R.id.terminerButton);
        terminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ajoutez ici le code que vous souhaitez exécuter lorsque "SignUp Now" est cliqué
                // Par exemple, vous pouvez lancer une nouvelle activité pour l'inscription
                Intent intent = new Intent(InscriptionCandidat.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
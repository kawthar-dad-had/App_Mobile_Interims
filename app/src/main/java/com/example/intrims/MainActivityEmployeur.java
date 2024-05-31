package com.example.intrims;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.intrims.databinding.ActivityMainBinding;
import com.example.intrims.databinding.ActivityMainEmployeurBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivityEmployeur extends AppCompatActivity {
    DatabaseHelper dbHelper= new DatabaseHelper(MainActivityEmployeur.this);
    ActivityMainEmployeurBinding binding; // Declare binding variable
    Date currentDate = new Date();

    // Créer un format de date souhaité (par exemple "dd-MM-yyyy")
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    // Formater la date actuelle selon le format souhaité
    String formattedDate = sdf.format(currentDate);

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainEmployeurBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new OffreEmployeeFragment());

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.offre){
                replaceFragment(new OffreEmployeeFragment());
            } else if (item.getItemId() == R.id.profil) {
                replaceFragment(new ProfilFragment());
            }else if (item.getItemId() == R.id.candidature) {
                replaceFragment(new CandidatureEmployeeFragment());
            }else if (item.getItemId() == R.id.notification) {
                replaceFragment(new NotificationFragment());
            }

            return true;
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout_offre);

        Button loginButton = dialog.findViewById(R.id.loginButton);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        EditText titre = dialog.findViewById(R.id.titre);
        EditText subtitle = dialog.findViewById(R.id.subtitle);
        EditText entreprise = dialog.findViewById(R.id.entreprise);
        EditText description = dialog.findViewById(R.id.description);

        entreprise.setHint(dbHelper.getEmployeeByEmail(LoginActivity.email).getEntreprise());



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs des champs de texte
                String titreText = titre.getText().toString();
                String subtitleText = subtitle.getText().toString();
                String entrepriseText = dbHelper.getEmployeeByEmail(LoginActivity.email).getEntreprise();
                String dateText = formattedDate;
                String descriptionText = description.getText().toString();

                Toast.makeText(MainActivityEmployeur.this,dbHelper.getEmployeeByEmail(LoginActivity.email).getEntreprise(), Toast.LENGTH_SHORT).show();

                // Insérer l'offre dans la base de données

                boolean insertionSuccess = dbHelper.insertOffre(titreText, subtitleText, dateText, entrepriseText,LoginActivity.email, descriptionText);

                if (insertionSuccess) {
                    Toast.makeText(MainActivityEmployeur.this, "Offre insérée avec succès", Toast.LENGTH_SHORT).show();
                    OffreFragment.offres = dbHelper.getAllOffres();
                    replaceFragment(new OffreFragment());
                    dialog.dismiss(); // Fermer le dialog
                } else {
                    Toast.makeText(MainActivityEmployeur.this, "Échec de l'insertion de l'offre", Toast.LENGTH_SHORT).show();
                }
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
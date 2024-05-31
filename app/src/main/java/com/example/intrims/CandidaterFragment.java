package com.example.intrims;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CandidaterFragment extends Fragment {

    private EditText editTextNom;
    private EditText editTextPrenom;
    private EditText editTextEmail;
    private EditText editTextDateNaissance;
    private EditText editTextVille;
    private EditText editTextNationalite;
    private EditText editTextPhone;
    private Button btnCandidater;
    private DatabaseHelper databaseHelper;
    String titre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidater, container, false);

        editTextNom = view.findViewById(R.id.nom);
        editTextPrenom = view.findViewById(R.id.prenom);
        editTextEmail = view.findViewById(R.id.email);
        editTextDateNaissance = view.findViewById(R.id.date);
        editTextVille = view.findViewById(R.id.ville);
        editTextNationalite = view.findViewById(R.id.nationalite);
        editTextPhone = view.findViewById(R.id.phone);
        btnCandidater = view.findViewById(R.id.btn_candidater);
        TextView titreText = view.findViewById(R.id.titre);
        TextView sousText = view.findViewById(R.id.soustitre);
        TextView descriptionText = view.findViewById(R.id.description);
        TextView entrepriseText = view.findViewById(R.id.ent);
        TextView dateText = view.findViewById(R.id.datePublication);




        Bundle bundle = getArguments();
        if (bundle != null) {
            titre = bundle.getString("titre");
            String soustitre = bundle.getString("soustitre");
            String entreprise = bundle.getString("entreprise");
            String date = bundle.getString("date");
            String description = bundle.getString("description");

            // Utilisez ces informations pour remplir les champs de texte
            titreText.setText(titre);
            sousText.setText(soustitre);
            descriptionText.setText(description);
            entrepriseText.setText(entreprise);
            dateText.setText(date);

        }
        databaseHelper = new DatabaseHelper(getContext());

        // Récupérer les informations du candidat connecté
        Candidat candidat = databaseHelper.getCandidatByEmail(LoginActivity.email);

        // Afficher les informations du candidat dans les champs de texte
        if (candidat != null) {
            editTextNom.setText(candidat.getNom());
            editTextPrenom.setText(candidat.getPrenom());
            editTextEmail.setText(candidat.getEmail());
            editTextDateNaissance.setText(candidat.getDateNaissance());
            editTextVille.setText(candidat.getVille());
            editTextNationalite.setText(candidat.getNationalite());
            editTextPhone.setText(candidat.getPhone());
        }

        // Ajouter un OnClickListener pour le bouton Candidater
        btnCandidater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les données du formulaire
                String nomOffre = titre ;// Remplacez ceci par la valeur de votre choix
                String etatCandidature = "En attente"; // État initial de la candidature
                Date dateActuelle = new Date();
                // Formater la date selon vos besoins
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateFormatee = dateFormat.format(dateActuelle);

                // Insérer la candidature dans la base de données
                boolean candidatureInserted = databaseHelper.insertCandidature(LoginActivity.email, nomOffre, etatCandidature);

                // Vérifier si l'insertion a réussi
                if (candidatureInserted) {

                    boolean notificationInserted = databaseHelper.insertNotificationWithEmployeeEmail(nomOffre, dateFormatee, "Une nouvelle candidature a été enregistrée. Veuillez examiner cette candidature." + titre, LoginActivity.email);
                    if (notificationInserted) {
                        Toast.makeText(getContext(), "Candidature enregistrée avec succès", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Échec de l'ajout de la notification", Toast.LENGTH_SHORT).show();
                    }

                    MainActivityCandidat activity = (MainActivityCandidat) requireActivity();
                    activity.replaceFragment(new CandidatureFragment());
                    Toast.makeText(getContext(), "Candidature enregistrée avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Échec de l'enregistrement de la candidature", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}

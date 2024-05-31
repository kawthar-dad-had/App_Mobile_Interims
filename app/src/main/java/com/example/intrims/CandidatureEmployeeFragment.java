package com.example.intrims;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import com.google.android.material.chip.Chip;
import java.util.ArrayList;
import java.util.List;

public class CandidatureEmployeeFragment extends Fragment implements SearchView.OnQueryTextListener, CandidatureEmployeeAdapter.OnClickListener {

    private RecyclerView recyclerView;
    private CandidatureEmployeeAdapter adapter;
    public static List<CandidatureEmployee> candidatureList;
    private SearchView searchView;
    private Chip chip;
    private DatabaseHelper databaseHelper;
    List<CandidatureEmployee> candidatureList2;

    public CandidatureEmployeeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidature_employee, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewCandidatures);
        chip = view.findViewById(R.id.chip);
        searchView = view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        candidatureList = new ArrayList<>();
        // Vous pouvez remplir la liste à partir de la base de données ici
        databaseHelper = new DatabaseHelper(getContext());
        candidatureList2 = databaseHelper.getAllCandidaturesWithCandidates();

        adapter = new CandidatureEmployeeAdapter(candidatureList2);
        adapter.setOnClickListener(this);
        chip.setText(String.format("%d Résultats", candidatureList2.size()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<CandidatureEmployee> newList = new ArrayList<>();

        for (CandidatureEmployee candidature : candidatureList2) {
            if (candidature.getNomPrenom().toLowerCase().contains(userInput) ||
                    candidature.getNomOffre().toLowerCase().contains(userInput) ||
                    candidature.getDateCandidature().toLowerCase().contains(userInput) ||
                    candidature.getEtatCandidature().toLowerCase().contains(userInput)) {
                newList.add(candidature);
            }
        }

        adapter.updateList(newList);
        chip.setText(String.format("%d Résultats", newList.size()));

        return true;
    }

    @Override
    public void onAcceptClick(int position) {
        Toast.makeText(getContext(), "Accepter", Toast.LENGTH_SHORT).show();
        CandidatureEmployee candidature = candidatureList2.get(position);

        // Appeler la méthode d'update dans la base de données
        boolean updated = databaseHelper.updateEtatCandidatureByOffre(candidature.getNomOffre(), "Accepté");

        if (updated) {
            // Mettre à jour l'état de la candidature dans la liste affichée
            candidature.setEtatCandidature("Accepté");
            adapter.notifyItemChanged(position);
        } else {
            // Afficher un message d'erreur si la mise à jour a échoué
            Toast.makeText(getContext(), "Échec de la mise à jour de l'état de la candidature", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefuseClick(int position) {
        Toast.makeText(getContext(), "Refuser", Toast.LENGTH_SHORT).show();
        CandidatureEmployee candidature = candidatureList2.get(position);

        // Appeler la méthode d'update dans la base de données
        boolean updated = databaseHelper.updateEtatCandidatureByOffre(candidature.getNomOffre(), "Refusé");

        if (updated) {
            // Mettre à jour l'état de la candidature dans la liste affichée
            candidature.setEtatCandidature("Refusé");
            adapter.notifyItemChanged(position);
        } else {
            // Afficher un message d'erreur si la mise à jour a échoué
            Toast.makeText(getContext(), "Échec de la mise à jour de l'état de la candidature", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseClick(int position) {
        Toast.makeText(getContext(), "Répondre", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConsulterClick(int position) {
        // Récupère l'URI du CV à partir de la liste de candidatures
        CandidatureEmployee candidature = candidatureList2.get(position);

        Toast.makeText(getContext(), "kawthar"+candidature.getEtatCV(), Toast.LENGTH_SHORT).show();

        if (candidature.getEtatCV() != null) {
            // Crée l'intention pour ouvrir le fichier à l'aide d'une application externe
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(candidature.getEtatCV()), "application/pdf"); // Remplace "application/pdf" par le type MIME approprié pour ton CV
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                // Vérifie si une activité est disponible pour ouvrir le fichier
                if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                    // Ouvre le fichier
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "kawthar", Toast.LENGTH_SHORT).show();

                    // Aucune application disponible pour ouvrir le fichier
                    Toast.makeText(getContext(), "Aucune application disponible pour ouvrir le CV", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // Gère les exceptions
                e.printStackTrace();
                Toast.makeText(getContext(), "Erreur lors de l'ouverture du CV", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Aucun fichier sélectionné
            Toast.makeText(getContext(), "Aucun CV sélectionné", Toast.LENGTH_SHORT).show();
        }
    }

}

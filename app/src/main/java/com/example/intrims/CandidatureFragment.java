package com.example.intrims;

import android.os.Bundle;

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

public class CandidatureFragment extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private CandidatureAdapter candidatureAdapter;
    private List<Candidature> candidatures;
    private SearchView searchView;
    private Chip chip;
    private DatabaseHelper databaseHelper;

    List<Candidature> candidaturesWithOffres;
    public CandidatureFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidature, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        chip = view.findViewById(R.id.chip);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Ajouter l'espacement entre les éléments de la RecyclerView
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.space_between_cards);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        candidatures = new ArrayList<>();
        candidatures.add(new Candidature("Développeur Android", "Entreprise XYZ", "Nous recherchons un développeur Android qualifié pour rejoindre notre équipe.","27/05/2024","Montpellier","Accepté"));
        candidatures.add(new Candidature("Ingénieur DevOps", "Entreprise ABC", "Nous recherchons un ingénieur DevOps pour maintenir nos infrastructures et automatiser nos processus.","20/05/2024","Paris", "En attente"));
        candidatures.add(new Candidature("Développeur Web Frontend", "Startup XYZ", "Nous recherchons un développeur web frontend pour travailler sur nos projets clients.","01/05/2024","Toulouse", "Refusé"));
        databaseHelper = new DatabaseHelper(getContext());
        candidaturesWithOffres = databaseHelper.getCandidaturesByEmailWithOffres(LoginActivity.email);
        Toast.makeText(getContext(), candidaturesWithOffres.get(0).getEtat(), Toast.LENGTH_SHORT).show();


        candidatureAdapter = new CandidatureAdapter(candidaturesWithOffres);
        chip.setText(String.format("%d Résultats", candidaturesWithOffres.size()));

        recyclerView.setAdapter(candidatureAdapter);

        // Initialiser la SearchView
        searchView = view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);

        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<Candidature> newList = new ArrayList<>();

        for (Candidature candidature : candidaturesWithOffres) {
            if (candidature.getTitle().toLowerCase().contains(userInput) || candidature.getCompany().toLowerCase().contains(userInput) || candidature.getEtat().toLowerCase().contains(userInput)) {
                newList.add(candidature);
            }
        }

        candidatureAdapter.updateList(newList);
        chip.setText(String.format("%d Résultats", newList.size()));

        return true;
    }

}
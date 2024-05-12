package com.example.intrims;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;


public class OffreFragment extends Fragment implements SearchView.OnQueryTextListener , OffreAdapter.OnPostulerClickListener  {

    private RecyclerView recyclerView;
    private OffreAdapter offreAdapter;
    private List<Offre> offres;
    private SearchView searchView;
    private Chip chip;
    private Button postuler;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offre, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        chip = view.findViewById(R.id.chip);
        postuler = view.findViewById(R.id.buttonPostuler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Ajouter l'espacement entre les éléments de la RecyclerView
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.space_between_cards);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        offres = new ArrayList<>();
        offres.add(new Offre("Développeur Android", "Entreprise XYZ", "Nous recherchons un développeur Android qualifié pour rejoindre notre équipe."));
        offres.add(new Offre("Ingénieur DevOps", "Entreprise ABC", "Nous recherchons un ingénieur DevOps pour maintenir nos infrastructures et automatiser nos processus."));
        offres.add(new Offre("Développeur Web Frontend", "Startup XYZ", "Nous recherchons un développeur web frontend pour travailler sur nos projets clients."));
        offres.add(new Offre("Data Scientist", "Entreprise ABC", "Nous recherchons un data scientist pour analyser et interpréter nos données."));
        offres.add(new Offre("Chef de Projet IT", "Entreprise XYZ", "Nous recherchons un chef de projet IT pour coordonner nos équipes de développement et assurer la livraison des projets."));

        offreAdapter = new OffreAdapter(offres);
        chip.setText(String.format("%d Résultats", offres.size()));
        offreAdapter.setOnPostulerClickListener(this);
        recyclerView.setAdapter(offreAdapter);

        // Initialiser la SearchView
        searchView = view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);

        return view;
    }
    @Override
    public void onPostulerClick(int position) {

        MainActivityCandidat activity = (MainActivityCandidat) requireActivity();
        activity.replaceFragment(new CandidaterFragment());
    }

    // Méthode appelée lorsqu'une requête de recherche est soumise
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    // Méthode appelée lorsqu'il y a un changement de texte dans la SearchView
    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<Offre> newList = new ArrayList<>();

        for (Offre offre : offres) {
            if (offre.getTitle().toLowerCase().contains(userInput) || offre.getCompany().toLowerCase().contains(userInput)) {
                newList.add(offre);
            }
        }

        offreAdapter.updateList(newList);
        chip.setText(String.format("%d Résultats", newList.size()));

        return true;
    }
}

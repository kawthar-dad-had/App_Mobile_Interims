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


public class OffreEmployeeFragment extends Fragment implements SearchView.OnQueryTextListener, OffreEmployeeAdapter.OnPostulerClickListener {

    private RecyclerView recyclerView;
    private OffreEmployeeAdapter offreAdapter;
    public static List<Offre> offres;
    private SearchView searchView;
    private Chip chip;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offre, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        chip = view.findViewById(R.id.chip);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Ajouter l'espacement entre les éléments de la RecyclerView
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.space_between_cards);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spaceInPixels));



        // Récupérer les offres depuis la base de données
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());


        if(LoginActivity.roleVerifie.equals("Candidat")){
            offres = dbHelper.getAllOffres();
        } else if (LoginActivity.roleVerifie.equals("Employeur")) {
            //offres = dbHelper.getAllOffres();
            offres = dbHelper.getOffresByEntreprise(dbHelper.getEmployeeByEmail(LoginActivity.email).getEntreprise());

        }

        offreAdapter = new OffreEmployeeAdapter(offres);
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

    @Override
    public void onConsulterClick(int position) {
        MainActivityCandidat activity = (MainActivityCandidat) requireActivity();
        activity.replaceFragment(new ConsulterFragment());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<Offre> newList = new ArrayList<>();

        for (Offre offre : offres) {
            if (offre.getTitle().toLowerCase().contains(userInput) || offre.getCompany().toLowerCase().contains(userInput) || offre.getSubtitle().toLowerCase().contains(userInput) || offre.getDate().toLowerCase().contains(userInput)) {
                newList.add(offre);
            }
        }

        offreAdapter.updateList(newList);
        chip.setText(String.format("%d Résultats", newList.size()));

        return true;
    }
}

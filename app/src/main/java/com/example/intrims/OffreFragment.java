package com.example.intrims;

import android.annotation.SuppressLint;
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


public class OffreFragment extends Fragment implements SearchView.OnQueryTextListener, OffreAdapter.OnPostulerClickListener {

    private RecyclerView recyclerView;
    private OffreAdapter offreAdapter;
    public static List<Offre> offres;
    private SearchView searchView;
    private Chip chip;
    private DatabaseHelper databaseHelper;

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
        offres = dbHelper.getAllOffres();

        // Déterminer le rôle de l'utilisateur
        String role = LoginActivity.roleVerifie;

        // Adapter la liste d'offres en fonction du rôle de l'utilisateur
        if (role.equals("Candidat")) {
            offres = dbHelper.getAllOffres();
        } else if (role.equals("Employeur")) {
            offres = dbHelper.getOffresByEntreprise(dbHelper.getEmployeeByEmail(LoginActivity.email).getEntreprise());
        } else {
            // Role anonyme, pas besoin de changer les offres
        }

        offreAdapter = new OffreAdapter(offres, role);
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
        Offre offre = offres.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("titre", offre.getTitle());
        bundle.putString("soustitre", offre.getSubtitle());
        bundle.putString("entreprise", offre.getCompany());
        bundle.putString("date", offre.getDate());
        bundle.putString("description", offre.getDescription());

        CandidaterFragment candidaterFragment = new CandidaterFragment();
        candidaterFragment.setArguments(bundle);

        MainActivityCandidat activity = (MainActivityCandidat) requireActivity();
        activity.replaceFragment(candidaterFragment);
    }

    @Override
    public void onConsulterClick(int position) {
        MainActivityCandidat activity = (MainActivityCandidat) requireActivity();
        activity.replaceFragment(new ConsulterFragment());
    }

    @Override
    public void onSaveClick(int position) {
        Offre offre = offres.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("titre", offre.getTitle());
        bundle.putString("email", LoginActivity.email);

        String emailUtilisateur = LoginActivity.email;

        databaseHelper = new DatabaseHelper(getContext());

        boolean insertionReussie = databaseHelper.insertFavoris(emailUtilisateur, offre.getTitle());

        if (insertionReussie) {
            Toast.makeText(getContext(), "Offre ajoutée aux favoris", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Échec de l'ajout aux favoris", Toast.LENGTH_SHORT).show();
        }
        FavoriFragment favoriFragment = new FavoriFragment();

        MainActivityCandidat activity = (MainActivityCandidat) requireActivity();
        activity.replaceFragment(favoriFragment);
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

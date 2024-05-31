package com.example.intrims;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OffreAdapter extends RecyclerView.Adapter<OffreAdapter.OffreViewHolder> {

    private List<Offre> offres;
    private static OnPostulerClickListener postulerClickListener;

    public OffreAdapter(List<Offre> offres, String role) {
        this.offres = offres;
    }

    @NonNull
    @Override
    public OffreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offre, parent, false);
        return new OffreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffreViewHolder holder, int position) {
        Offre offre = offres.get(position);
        holder.bind(offre);
    }

    @Override
    public int getItemCount() {
        return offres.size();
    }

    public void updateList(List<Offre> newList) {
        offres = new ArrayList<>();
        offres.addAll(newList);
        notifyDataSetChanged();
    }

    static class OffreViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView subtitleTextView;
        private TextView entreprise;
        private TextView date;
        private TextView descriptionTextView;
        private Button buttonPostuler;
        private Button buttonEnregistrer;

        public OffreViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
            entreprise = itemView.findViewById(R.id.entrepsrise);
            date = itemView.findViewById(R.id.date);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            buttonPostuler = itemView.findViewById(R.id.buttonPostuler);
            buttonEnregistrer = itemView.findViewById(R.id.enrg);

            // Masquer les boutons pour le rôle anonyme
            if (LoginActivity.roleVerifie.equals("Anonyme")) {
                buttonPostuler.setVisibility(View.GONE);
                buttonEnregistrer.setVisibility(View.GONE);
            }

            buttonPostuler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Votre message ici", Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        postulerClickListener.onPostulerClick(position);
                    }
                }
            });

            buttonEnregistrer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Save", Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        postulerClickListener.onSaveClick(position);
                    }
                }
            });
        }

        public void bind(Offre offre) {
            titleTextView.setText(offre.getTitle());
            subtitleTextView.setText(offre.getSubtitle());
            entreprise.setText(offre.getCompany());
            date.setText(offre.getDate());
            descriptionTextView.setText(offre.getDescription());
        }
    }

    public interface OnPostulerClickListener {
        void onPostulerClick(int position);

        void onConsulterClick(int position);

        void onSaveClick(int position);
    }

    public void setOnPostulerClickListener(OnPostulerClickListener listener) {
        postulerClickListener = listener;
    }
}

package com.example.intrims;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CandidatureEmployeeAdapter extends RecyclerView.Adapter<CandidatureEmployeeAdapter.ViewHolder> {

    private List<CandidatureEmployee> candidatureList;
    private static OnClickListener onClickListener;

    public CandidatureEmployeeAdapter(List<CandidatureEmployee> candidatureList) {
        this.candidatureList = candidatureList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_candidature, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CandidatureEmployee candidature = candidatureList.get(position);
        holder.tvNomPrenom.setText(candidature.getNomPrenom());
        holder.tvNomOffre.setText(candidature.getNomOffre());
        holder.tvDateCandidature.setText(candidature.getDateCandidature());

        // Affichage de l'état de la candidature
        holder.tvEtatCandidature.setText(candidature.getEtatCandidature());

        // Gestion de l'affichage des boutons en fonction de l'état de la candidature
        if (candidature.getEtatCandidature().equalsIgnoreCase("En attente")) {
            holder.btnAccepter.setVisibility(View.VISIBLE);
            holder.btnRefuser.setVisibility(View.VISIBLE);
            holder.btnRepondre.setVisibility(View.VISIBLE);
            holder.btnConsulter.setVisibility(View.VISIBLE);
        } else {
            holder.btnAccepter.setVisibility(View.GONE);
            holder.btnRefuser.setVisibility(View.GONE);
            holder.btnRepondre.setVisibility(View.GONE);
            holder.btnConsulter.setVisibility(View.VISIBLE); // Seul le bouton "Consulter" est visible pour les états accepté et refusé
        }

        // Gestion des clics sur les boutons
        holder.btnAccepter.setOnClickListener(v -> onClickListener.onAcceptClick(position));
        holder.btnRefuser.setOnClickListener(v -> onClickListener.onRefuseClick(position));
        holder.btnRepondre.setOnClickListener(v -> onClickListener.onResponseClick(position));
        holder.btnConsulter.setOnClickListener(v -> onClickListener.onConsulterClick(position));
    }

    @Override
    public int getItemCount() {
        return candidatureList.size();
    }

    public void updateList(List<CandidatureEmployee> newList) {
        candidatureList = new ArrayList<>();
        candidatureList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomPrenom, tvNomOffre, tvDateCandidature, tvEtatCandidature;
        Button btnAccepter, btnRefuser, btnRepondre ;
        ImageButton btnConsulter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomPrenom = itemView.findViewById(R.id.tvNomPrenom);
            tvNomOffre = itemView.findViewById(R.id.tvNomOffre);
            tvDateCandidature = itemView.findViewById(R.id.tvDateCandidature);
            tvEtatCandidature = itemView.findViewById(R.id.tvEtatCandidature); // Nouveau TextView pour afficher l'état de la candidature
            btnAccepter = itemView.findViewById(R.id.btnAccepter);
            btnRefuser = itemView.findViewById(R.id.btnRefuser);
            btnRepondre = itemView.findViewById(R.id.btnRepondre);
            btnConsulter = itemView.findViewById(R.id.btnConsulter);

            btnAccepter.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onClickListener.onAcceptClick(position);
                }
            });

            btnRefuser.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onClickListener.onRefuseClick(position);
                }
            });

            btnRepondre.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onClickListener.onResponseClick(position);
                }
            });

            btnConsulter.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onClickListener.onConsulterClick(position);
                }
            });
        }
    }

    public interface OnClickListener {
        void onAcceptClick(int position);
        void onRefuseClick(int position);
        void onResponseClick(int position);
        void onConsulterClick(int position);

    }

    public void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
    }
}


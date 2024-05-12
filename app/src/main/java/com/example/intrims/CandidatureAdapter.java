package com.example.intrims;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CandidatureAdapter extends RecyclerView.Adapter<CandidatureAdapter.CandidatureViewHolder>  {

    private List<Candidature> candidatures;

    public CandidatureAdapter(List<Candidature> candidatures) {
        this.candidatures = candidatures;
    }

    @NonNull
    @Override
    public CandidatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidature, parent, false);
        return new CandidatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidatureAdapter.CandidatureViewHolder holder, int position) {
        Candidature candidature = candidatures.get(position);
        holder.bind(candidature);
    }

    @Override
    public int getItemCount() {
        return candidatures.size();
    }

    public void updateList(List<Candidature> newList) {
        candidatures.clear();
        candidatures.addAll(newList);
        notifyDataSetChanged();
    }

    static class CandidatureViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView subtitleTextView;
        private TextView descriptionTextView;

        public CandidatureViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }

        public void bind(Candidature candidature) {
            titleTextView.setText(candidature.getTitle());
            subtitleTextView.setText(candidature.getCompany());
            descriptionTextView.setText(candidature.getDescription());
        }
    }
}

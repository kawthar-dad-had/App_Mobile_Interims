package com.example.intrims;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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
    public void onBindViewHolder(@NonNull CandidatureViewHolder holder, int position) {
        Candidature candidature = candidatures.get(position);
        holder.bind(candidature);
    }

    @Override
    public int getItemCount() {
        return candidatures.size();
    }

    public void updateList(List<Candidature> newList) {
        candidatures = new ArrayList<>();
        candidatures.addAll(newList);
        notifyDataSetChanged();
    }

    static class CandidatureViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView subtitleTextView;
        private TextView descriptionTextView;
        private TextView date;
        private TextView lieu;
        private TextView etat;

        public CandidatureViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            date = itemView.findViewById(R.id.date);
            lieu = itemView.findViewById(R.id.lieu);
            etat = itemView.findViewById(R.id.etat);
        }

        public void bind(Candidature candidature) {
            titleTextView.setText(candidature.getTitle());
            subtitleTextView.setText(candidature.getCompany());
            descriptionTextView.setText(candidature.getDescription());
            date.setText(candidature.getDate());
            lieu.setText(candidature.getLieu());
            etat.setText(candidature.getEtat());

            // Change the background color of the status based on the state
            int statusColor;
            int backgroundResource = 0;

            switch (candidature.getEtat().toLowerCase()) {
                case "accepté":
                    statusColor = ContextCompat.getColor(itemView.getContext(), R.color.accepted_color);
                    backgroundResource = R.drawable.accepted_background;

                    break;
                case "en attente":
                    statusColor = ContextCompat.getColor(itemView.getContext(), R.color.in_progress_color);
                    backgroundResource = R.drawable.in_progress_background;

                    break;
                case "refusé":
                    statusColor = ContextCompat.getColor(itemView.getContext(), R.color.rejected_color);
                    backgroundResource = R.drawable.rejected_background;

                    break;
                default:
                    statusColor = ContextCompat.getColor(itemView.getContext(), R.color.default_color); // Optional: Default color
                    break;
            }
            etat.setBackgroundColor(statusColor);
            etat.setBackgroundResource(backgroundResource);

        }
    }
}

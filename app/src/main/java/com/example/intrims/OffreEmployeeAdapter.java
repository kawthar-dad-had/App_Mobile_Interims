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

public class OffreEmployeeAdapter extends RecyclerView.Adapter<OffreEmployeeAdapter.OffreViewHolder> {

    private List<Offre> offres;
    private static OnPostulerClickListener postulerClickListener;


    public OffreEmployeeAdapter(List<Offre> offres) {
        this.offres = offres;

    }


    @NonNull
    @Override
    public OffreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offre_employee, parent, false);
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

        public OffreViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
            entreprise = itemView.findViewById(R.id.entrepsrise);
            date = itemView.findViewById(R.id.date);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            Button consulter =  itemView.findViewById(R.id.consulter);
            Button modifier =  itemView.findViewById(R.id.modifier);
            Button supprimer =  itemView.findViewById(R.id.supprimer);
            consulter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "consulter", Toast.LENGTH_SHORT).show();


                }
            });
            modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "modifier", Toast.LENGTH_SHORT).show();


                }
            });

            supprimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "supprimer", Toast.LENGTH_SHORT).show();


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
    }
    public void setOnPostulerClickListener(OnPostulerClickListener listener) {
        postulerClickListener = listener;
    }

}

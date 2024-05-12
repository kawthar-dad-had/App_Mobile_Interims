package com.example.intrims;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OffreAdapter extends RecyclerView.Adapter<OffreAdapter.OffreViewHolder> {

    private List<Offre> offres;
    private static OnPostulerClickListener postulerClickListener;


    public OffreAdapter(List<Offre> offres) {
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
        offres.clear();
        offres.addAll(newList);
        notifyDataSetChanged();
    }

    static class OffreViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView subtitleTextView;
        private TextView descriptionTextView;

        public OffreViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            Button postulerButton = itemView.findViewById(R.id.buttonPostuler);
            postulerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Votre message ici", Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        postulerClickListener.onPostulerClick(position);
                    }

                }
            });
        }

        public void bind(Offre offre) {
            titleTextView.setText(offre.getTitle());
            subtitleTextView.setText(offre.getCompany());
            descriptionTextView.setText(offre.getDescription());
        }
    }
    public interface OnPostulerClickListener {
        void onPostulerClick(int position);
    }
    public void setOnPostulerClickListener(OnPostulerClickListener listener) {
        postulerClickListener = listener;
    }

}

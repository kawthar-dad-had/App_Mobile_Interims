package com.example.intrims;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Notification> notificationList;

    private DatabaseHelper databaseHelper;

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Handle fragment arguments if needed
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample data
        notificationList = new ArrayList<>();
        notificationList.add(new Notification("Offre d'emploi de développeur", "24/05/2024", "Bonjour, je suis intéressé par votre offre de poste de développeur. Pouvez-vous me donner plus d'informations sur le processus de candidature ?"));
        notificationList.add(new Notification("Offre de stage en marketing", "23/05/2024", "Bonjour, je suis étudiant en marketing et je suis très intéressé par votre offre de stage. Pouvez-vous me dire comment postuler ?"));
        notificationList.add(new Notification("Opportunité de carrière en finance", "22/05/2024", "Bonjour, j'ai vu votre annonce pour un poste en finance et je pense que mes compétences correspondent bien à ce que vous recherchez. Pouvons-nous discuter des détails du poste ?"));
        notificationList.add(new Notification("Proposition de collaboration freelance", "21/05/2024", "Bonjour, je suis un freelance spécialisé en design graphique et je suis intéressé par la possibilité de travailler avec vous sur des projets futurs. Êtes-vous ouvert à une collaboration ?"));
        notificationList.add(new Notification("Consultation pour un projet", "20/05/2024", "Bonjour, j'aimerais discuter de votre projet et voir comment je pourrais vous aider à le concrétiser. Êtes-vous disponible pour une réunion ?"));
        notificationList.add(new Notification("Demande de partenariat", "19/05/2024", "Bonjour, nous sommes une entreprise spécialisée dans le développement web et nous serions intéressés par un partenariat avec votre entreprise. Serait-il possible de discuter des opportunités de collaboration ?"));
        notificationList.add(new Notification("Demande de rendez-vous", "18/05/2024", "Bonjour, j'aimerais vous rencontrer pour discuter de la possibilité de rejoindre votre équipe en tant que responsable des ventes. Êtes-vous disponible pour un entretien ?"));
        notificationList.add(new Notification("Réponse à une offre d'emploi", "17/05/2024", "Bonjour, je vous remercie de m'avoir donné l'opportunité de postuler pour le poste de chef de projet. Je suis très enthousiaste à l'idée de rejoindre votre équipe. Quelle est la prochaine étape du processus de recrutement ?"));
        notificationList.add(new Notification("Demande de formation", "16/05/2024", "Bonjour, je suis intéressé par votre programme de formation interne et j'aimerais en savoir plus sur les sujets abordés et les modalités d'inscription. Pouvez-vous me fournir plus d'informations ?"));
        notificationList.add(new Notification("Demande de recommandation", "15/05/2024", "Bonjour, j'ai postulé pour un poste dans une autre entreprise et j'aurais besoin d'une recommandation de votre part pour appuyer ma candidature. Seriez-vous prêt à me fournir une lettre de recommandation ?"));
        databaseHelper = new DatabaseHelper(getContext());
        notificationList = databaseHelper.getAllNotificationsWithEmployeeEmail();

        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);

        // Add item decoration for spacing between items
        int verticalSpaceHeight = getResources().getDimensionPixelSize(R.dimen.vertical_space_height);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(verticalSpaceHeight));

        return view;
    }
}

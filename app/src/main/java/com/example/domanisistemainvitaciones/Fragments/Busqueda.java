package com.example.domanisistemainvitaciones.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.domanisistemainvitaciones.R;
import com.example.domanisistemainvitaciones.adapters.AdapterClientesInvitados;
import com.example.domanisistemainvitaciones.modelos.ClientesInvitados;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Busqueda extends Fragment {

    ArrayList<ClientesInvitados> listaInvitados=new ArrayList<>();
    RecyclerView recyclerViewClientesInvitados;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ClientesInvitados clientesInvitados;
    private ProgressDialog dialog;
    public Busqueda() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_busqueda, container, false);
        recyclerViewClientesInvitados = (RecyclerView) view.findViewById(R.id.recyclerListaInvitados);
        recyclerViewClientesInvitados.setLayoutManager(new LinearLayoutManager(getContext()));
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listaInvitados  = (ArrayList<ClientesInvitados>) getArguments().getSerializable("listaInvitados");
        }

        AdapterClientesInvitados adapter = new AdapterClientesInvitados(listaInvitados);
        recyclerViewClientesInvitados.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Selecciono "
                        +listaInvitados.get(recyclerViewClientesInvitados.getChildAdapterPosition(v)).
                        getNombre(),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }



}

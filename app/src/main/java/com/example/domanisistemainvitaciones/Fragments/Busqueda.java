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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.domanisistemainvitaciones.R;
import com.example.domanisistemainvitaciones.adapters.AdapterClientesInvitados;
import com.example.domanisistemainvitaciones.modelos.ClientesInvitados;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    AdapterClientesInvitados adapter;
    public Busqueda() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_busqueda, container, false);
        recyclerViewClientesInvitados = (RecyclerView) view.findViewById(R.id.recyclerListaInvitados);
        recyclerViewClientesInvitados.setLayoutManager(new LinearLayoutManager(getContext()));
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listaInvitados  = (ArrayList<ClientesInvitados>) getArguments().getSerializable("listaInvitados");
        }

        adapter = new AdapterClientesInvitados(listaInvitados);
        recyclerViewClientesInvitados.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acreditarClienteInvitado(v);
            }
        });
        return view;
    }

    private void acreditarClienteInvitado(View v) {
        dialog = (ProgressDialog) ProgressDialog.show(getContext(), "Cargando...", "espere por favor...",true);
        db.collection("clientesInvitados")
                .whereEqualTo("id", listaInvitados.get(recyclerViewClientesInvitados.getChildAdapterPosition(v)).getId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        String id="";
                        if (e != null) {
                            dialog.dismiss();
                            Toast.makeText(getContext(), "No se pudo establecer conexion, intente nuevamente", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            id =doc.getId();
                        }

                        db.collection("clientesInvitados")
                                .document(id)
                                .update("asistencia","acreditado")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialog.dismiss();
                                        Toast.makeText(getContext(),"Cliente Acreditado",Toast.LENGTH_LONG).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                        Toast.makeText(getContext(),"Error en la conexion, intente nuevamente.",Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_buscador,menu);

        MenuItem searchItem = menu.findItem(R.id.buscador);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}

package com.example.domanisistemainvitaciones;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.domanisistemainvitaciones.Fragments.Busqueda;
import com.example.domanisistemainvitaciones.modelos.ClientesInvitados;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;


import com.example.domanisistemainvitaciones.Fragments.anadirUsuario;
import com.example.domanisistemainvitaciones.Fragments.escanQR;
import com.example.domanisistemainvitaciones.Fragments.estadistica;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /*DECLARACION DE VARIABLES*/

    //DECLARACION DE FRAGMENTS
    private escanQR fragmentEscanQr = new escanQR();
    private anadirUsuario fragmentAnadirUsuario = new anadirUsuario();
    private estadistica fragmentEstadistica = new estadistica();
    private Busqueda fragmentBusqueda = new Busqueda();

    ArrayList<ClientesInvitados> listaInvitados=new ArrayList<>();
    ClientesInvitados clientesInvitados;
    private ProgressDialog dialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //POR DEFECTO
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_QR:
                    setFragment(fragmentEscanQr);
                    return true;
                case R.id.navigation_addUsuario:
                    setFragment(fragmentAnadirUsuario);
                    return true;
                case R.id.navigation_Estadistica:
                    setFragment(fragmentEstadistica);
                    return true;
                case R.id.navigation_Busqueda:
                    setFragment(fragmentBusqueda);
                    return true;
            }
            return false;
        }
    };


    @Override//POR DEFECTO
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragment(fragmentEscanQr);
        dialog = (ProgressDialog) ProgressDialog.show(this, "Cargando...", "espere por favor...",true);
        db.collection("clientesInvitados")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                clientesInvitados = new ClientesInvitados(document.getString("Nombre")
                                        ,document.getString("Correo"),document.getLong("id"));
                                listaInvitados.add(clientesInvitados);
                            }
                            dialog.dismiss();
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("listaInvitados",listaInvitados);
                            fragmentBusqueda.setArguments(bundle);
                            Log.d("TEST", "Current cites in CA: " + listaInvitados);
                        } else {
                            dialog.dismiss();
                            Log.d("TEST", "Error getting documents: ", task.getException());
                        }
                    }
                });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }


}


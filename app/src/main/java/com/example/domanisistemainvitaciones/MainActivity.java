package com.example.domanisistemainvitaciones;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.domanisistemainvitaciones.Fragments.anadirUsuario;
import com.example.domanisistemainvitaciones.Fragments.escanQR;
import com.example.domanisistemainvitaciones.Fragments.estadistica;

public class MainActivity extends AppCompatActivity {
    /*DECLARACION DE VARIABLES*/

    //DECLARACION DE FRAGMENTS
    private escanQR fragmentEscanQr = new escanQR();
    private anadirUsuario fragmentAnadirUsuario = new anadirUsuario();
    private estadistica fragmentEstadistica = new estadistica();

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
            }
            return false;
        }
    };


    @Override//POR DEFECTO
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


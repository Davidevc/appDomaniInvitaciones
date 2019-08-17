package com.example.domanisistemainvitaciones.Fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.domanisistemainvitaciones.R;


public class anadirUsuario extends Fragment {


    public anadirUsuario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_anadir_usuario, container, false);
        Spinner dia = (Spinner) view.findViewById(R.id.idDia);
        ArrayAdapter<CharSequence> adapterDia = ArrayAdapter.createFromResource(getContext(), R.array.dias,android.R.layout.simple_spinner_item);
        adapterDia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dia.setAdapter(adapterDia);

        Spinner mes = (Spinner) view.findViewById(R.id.idMes);
        ArrayAdapter<CharSequence> adapterMes = ArrayAdapter.createFromResource(getContext(), R.array.mes,android.R.layout.simple_spinner_item);
        adapterMes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mes.setAdapter(adapterMes);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

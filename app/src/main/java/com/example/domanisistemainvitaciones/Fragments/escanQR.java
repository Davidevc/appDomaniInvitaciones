package com.example.domanisistemainvitaciones.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domanisistemainvitaciones.R;


public class escanQR extends Fragment {


    public escanQR() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup  container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_escan_qr, container, false);
    }
}

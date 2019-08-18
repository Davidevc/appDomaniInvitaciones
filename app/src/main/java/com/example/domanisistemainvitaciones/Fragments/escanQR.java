package com.example.domanisistemainvitaciones.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.domanisistemainvitaciones.R;
import com.example.domanisistemainvitaciones.qrZxing.IntentIntegrator;
import com.example.domanisistemainvitaciones.qrZxing.IntentResult;

import org.w3c.dom.Text;


public class escanQR extends Fragment {

    Button button;
    TextView txt;

    public escanQR() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup  container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_escan_qr, container, false);

        button = (Button) view.findViewById(R.id.button);
        txt = (TextView) view.findViewById(R.id.textView4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

}

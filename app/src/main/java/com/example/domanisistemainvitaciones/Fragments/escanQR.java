package com.example.domanisistemainvitaciones.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.domanisistemainvitaciones.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;


public class escanQR extends Fragment {

    Button button;
    EditText etCodigo;

    public escanQR() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup  container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_escan_qr, container, false);

        button = (Button) view.findViewById(R.id.button);
        etCodigo = (EditText) view.findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            escanear();
            }
        });

        return view;
    }
    public void escanear(){
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(escanQR.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("ESCANEAR CODIGO");
        intent.setCameraId(0);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() == null){
            Toast.makeText(getContext(),"Cancelaste el escaneo",Toast.LENGTH_SHORT).show();
            } else {
             etCodigo.setText(result.getContents().toString());
             /*code64(result.getContents().toString());*/
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /*public void code64(String testValue) {

        byte[] encodeValue = android.util.Base64.encode(testValue.getBytes(), Base64.DEFAULT);
        String correoBtoa = new String(encodeValue);
        Log.d("TEST", "e="+ correoBtoa.replace(" ","")+"hola");
    }*/

}


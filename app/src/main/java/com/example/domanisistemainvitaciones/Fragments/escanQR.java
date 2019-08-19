package com.example.domanisistemainvitaciones.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class escanQR extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    Button button;
    EditText etCodigo;

    public escanQR() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

    public void escanear() {
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
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "Cancelaste el escaneo", Toast.LENGTH_SHORT).show();
            } else {
                etCodigo.setText(result.getContents().toString());
                editar(result.getContents().toString());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /*public void code64(String testValue) {

        byte[] encodeValue = android.util.Base64.encode(testValue.getBytes(), Base64.DEFAULT);
        String correoBtoa = new String(encodeValue);
        Log.d("TEST", "e="+ correoBtoa.replace(" ","")+"hola");
    }*/

    private void editar(String correo) {
        db.collection("cliente")
                .whereEqualTo("email", correo)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TEST", "Listen failed.", e);
                            return;
                        }

                        List<String> cities = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("nombre") != null) {
                                cities.add(doc.getString("nombre"));
                                cities.add(doc.getString("email"));
                                cities.add(doc.getString("genero"));
                            }
                        }
                        Log.d("TEST", "Current cites in CA: " + cities);
                    }
                });

        

    }

    public String code64(String testValue) {
        byte[] encodeValue = android.util.Base64.encode(testValue.getBytes(), Base64.DEFAULT);
        String correoBtoa = new String(encodeValue);
        return correoBtoa;

    }
}


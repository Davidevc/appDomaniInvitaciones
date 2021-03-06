package com.example.domanisistemainvitaciones.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.example.domanisistemainvitaciones.modelos.Cliente;
import com.example.domanisistemainvitaciones.utilitarios.Conectividad;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    private ProgressDialog dialog;

    Button button;
    TextView etNombreCliente;
    Cliente cliente = new Cliente();

    Conectividad conectividad;
    AlertDialog alert = null;

    public escanQR() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_escan_qr, container, false);

        button = (Button) view.findViewById(R.id.button);
        etNombreCliente = (TextView) view.findViewById(R.id.txtnombreCliente);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!conectividad.isOnline(getContext())){
                    AlertNoCon();
                }else {
                    escanear();
                }
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
                editar(result.getContents().toString());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void editar(String correo) {
        dialog = (ProgressDialog) ProgressDialog.show(getContext(), "Cargando...", "espere por favor...",true);
        db.collection("cliente")
                .whereEqualTo("email", correo)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            dialog.dismiss();
                            Toast.makeText(getContext(), "No se pudo establecer conexion, intente nuevamente", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            cliente.setUid(doc.getId());
                            /*Log.d("TEST", doc.getId() + "=>" + doc.getData());*/
                            if (doc.get("nombre") != null) {
                                cliente.setNombre(doc.getString("nombre"));
                                cliente.setApellidoPaterno(doc.getString("apellidoP"));
                            }
                        }

                        db.collection("cliente")
                                .document(cliente.getUid())
                                .update("asistencia","acreditado")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialog.dismiss();
                                        etNombreCliente.setText(cliente.getNombre()+" "+cliente.getApellidoPaterno());
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

    private void AlertNoCon() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Actualmente no posee conexión, revise si están activados sus datos.")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getFragmentManager().popBackStack();
                    }
                });
        alert = builder.create();
        alert.show();
    }

    protected void showDialog() {
        AlertDialog.Builder alertaError= new AlertDialog.Builder(getContext());
        alertaError.setMessage("Error al obtener los datos, \n intente mas tarde.")
                .setCancelable(false).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        getFragmentManager().popBackStack();
                    }
                });
        android.app.AlertDialog alertDialog = alertaError.create();
        alertDialog.show();
    }

}


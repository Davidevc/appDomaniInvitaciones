package com.example.domanisistemainvitaciones.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.domanisistemainvitaciones.R;
import com.example.domanisistemainvitaciones.modelos.ClienteEntrada;
import com.example.domanisistemainvitaciones.utilitarios.Conectividad;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class anadirUsuario extends Fragment {
    /*INSTANCIAR FIREBASE*/
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    /*VARIABLES CON RESPECTO AL XML*/
    EditText nombre,correo;
    Button addCliente;
    private ProgressDialog dialog;


    Conectividad conectividad;
    AlertDialog alert = null;

    public anadirUsuario()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_anadir_usuario, container, false);

        /*INICIALIZA LAS VARIABLES CORRESPONDIENTE A LOS SPINNER*/
        final Spinner dia = (Spinner) view.findViewById(R.id.idDia);
        ArrayAdapter<CharSequence> adapterDia = ArrayAdapter.createFromResource(getContext(), R.array.dias,android.R.layout.simple_spinner_item);
        adapterDia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dia.setAdapter(adapterDia);

        final Spinner mes = (Spinner) view.findViewById(R.id.idMes);
        ArrayAdapter<CharSequence> adapterMes = ArrayAdapter.createFromResource(getContext(), R.array.mes,android.R.layout.simple_spinner_item);
        adapterMes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mes.setAdapter(adapterMes);

        /*ENLAZA BOTONES CON VARIABLES*/
        addCliente = (Button) view.findViewById(R.id.idAgregarCliente);
        nombre = (EditText) view.findViewById(R.id.txtNombre);
        correo = (EditText) view.findViewById(R.id.txtCorreo);


        addCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(validacionCampos()){
                if(!conectividad.isOnline(getContext())){
                    AlertNoCon();
                }else {
                    dialog = (ProgressDialog) ProgressDialog.show(getContext(), "Cargando...", "espere por favor...", true);
                    String nombreC = nombre.getText().toString();
                    String correoC = correo.getText().toString();
                    String diaC = dia.getSelectedItem().toString();
                    String mesC = mes.getSelectedItem().toString();

                    ClienteEntrada cE = new ClienteEntrada();
                    cE.setNombre(nombreC);
                    cE.setCorreo(correoC);
                    cE.setDia(diaC);
                    cE.setMes(mesC);


                    // Add a new document with a generated ID
                    db.collection("clientesEntrada")
                            .document(correoC)
                            .set(cE)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), "Cliente añadido", Toast.LENGTH_LONG).show();
                                    limpiarCajas();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), "Error en la conexion, intente nuevamente.", Toast.LENGTH_LONG).show();
                                    limpiarCajas();
                                }
                            });
                }
            }
            }
        });
        return view;
    }

    /*FUNCION PARA LA VALIDACION CORRECTA DE LOS DATOS A INGRESAR*/
    private boolean validacionCampos(){
        String nombreC = nombre.getText().toString();
        String correoC = correo.getText().toString();
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(correoC);

        if(nombreC.equals("")){
            nombre.setError("Required");
            Toast.makeText(getContext(),"Ingrese Nombre",Toast.LENGTH_LONG).show();
            return false;
        } else if (mather.find() == false){
            correo.setError("Required");
            Toast.makeText(getContext(),"Ingrese Correo valido",Toast.LENGTH_LONG).show();
            return false;
        }else return true;
    }
    /*FUNCION PARA HACER LIMPIEZA DE LOS DATOS UNA VEZ VALIDADOS Y ENVIADOS AL SERVIDOR*/
    private void limpiarCajas(){
        nombre.setText("");
        correo.setText("");
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

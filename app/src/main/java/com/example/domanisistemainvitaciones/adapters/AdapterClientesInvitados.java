package com.example.domanisistemainvitaciones.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domanisistemainvitaciones.R;
import com.example.domanisistemainvitaciones.modelos.ClientesInvitados;

import java.util.ArrayList;

public class AdapterClientesInvitados extends
        RecyclerView.Adapter<AdapterClientesInvitados.ClientesViewHolder> implements
        View.OnClickListener{

    ArrayList<ClientesInvitados> listaInvitados;
    private View.OnClickListener listener;

    public AdapterClientesInvitados(ArrayList<ClientesInvitados> listaInvitados) {
        this.listaInvitados = listaInvitados;
    }

    @NonNull
    @Override
    public ClientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        view.setOnClickListener(this);
        return new ClientesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientesViewHolder holder, int position) {
        holder.txtNombre.setText(listaInvitados.get(position).getNombre());
        holder.txtCorreo.setText(listaInvitados.get(position).getCorreo());
    }

    @Override
    public int getItemCount() {
        return listaInvitados.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public class ClientesViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre,txtCorreo;
        public ClientesViewHolder(View view) {
            super(view);
            txtNombre = (TextView) view.findViewById(R.id.txtNombre);
            txtCorreo = (TextView) view.findViewById(R.id.txtCorreo);
        }
    }
}

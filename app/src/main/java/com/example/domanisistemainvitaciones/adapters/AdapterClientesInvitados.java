package com.example.domanisistemainvitaciones.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domanisistemainvitaciones.R;
import com.example.domanisistemainvitaciones.modelos.ClientesInvitados;

import java.util.ArrayList;

public class AdapterClientesInvitados extends
        RecyclerView.Adapter<AdapterClientesInvitados.ClientesViewHolder> implements
        View.OnClickListener,Filterable {

    ArrayList<ClientesInvitados> listaInvitados;
    ArrayList<ClientesInvitados> listaInvitadosFull;
    private View.OnClickListener listener;

    public AdapterClientesInvitados(ArrayList<ClientesInvitados> listaInvitados) {
        this.listaInvitados = listaInvitados;
        listaInvitadosFull = new ArrayList<>(listaInvitados);
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

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ClientesInvitados> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length()==0){
                filteredList.addAll(listaInvitadosFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ClientesInvitados item : listaInvitadosFull){
                    if (item.getNombre().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaInvitados.clear();
            listaInvitados.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public class ClientesViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre,txtCorreo;
        public ClientesViewHolder(View view) {
            super(view);
            txtNombre = (TextView) view.findViewById(R.id.txtNombre);
            txtCorreo = (TextView) view.findViewById(R.id.txtCorreo);
        }
    }


}

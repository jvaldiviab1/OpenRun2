package com.jvaldiviab.openrun2.data.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jvaldiviab.openrun2.R;

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {
    ArrayList<String> listDatos;
    public AdapterDatos(ArrayList<String> listDatos){
        this.listDatos=listDatos;
    }
    @NonNull
    @Override
    public com.jvaldiviab.openrun2.data.model.AdapterDatos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.jvaldiviab.openrun2.data.model.AdapterDatos.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }
    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        TextView dato;
        public ViewHolderDatos(View itemView){
            super(itemView);
            dato = itemView.findViewById(R.id.nota);
        }
        public void asignarDatos(String datos){
            dato.setText(datos);
        }
    }
}

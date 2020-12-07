package com.jvaldiviab.openrun2.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.util.UtilActividades;

import java.util.ArrayList;
import java.util.List;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {
    List<UtilActividades> listDatos;
    public AdapterDatos(List<UtilActividades> listDatos){
        this.listDatos=listDatos;
    }
    @NonNull
    @Override
    public AdapterDatos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDatos.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }
    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        TextView fecha;
        TextView nota;
        TextView tiempo;
        public ViewHolderDatos(View itemView){
            super(itemView);
            fecha = itemView.findViewById(R.id.fecha);
            nota = itemView.findViewById(R.id.nota);
            tiempo = itemView.findViewById(R.id.tiempo);
        }
        public void asignarDatos(UtilActividades datos){
            fecha.setText(datos.getFecha()+" "+datos.getHora());
            nota.setText(datos.getNota());
            tiempo.setText(datos.getTiempo());
        }
    }
}

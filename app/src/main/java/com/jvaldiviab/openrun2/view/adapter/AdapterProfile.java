package com.jvaldiviab.openrun2.view.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jvaldiviab.openrun2.R;

import java.util.List;

public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.ViewHolderDatos> {
    List<Integer> listDatos;
        RecyclerView recycler;
    public AdapterProfile(List<Integer> listDatos,RecyclerView recycler){
            this.recycler=recycler;
            this.listDatos=listDatos;
        }
        @NonNull
        @Override
        public AdapterProfile.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
            return new AdapterProfile.ViewHolderDatos(view,recycler);
        }

        @Override
        public void onBindViewHolder(@NonNull AdapterProfile.ViewHolderDatos holder, int position) {
            holder.asignarDatos(listDatos.get(position));
        }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder{

            RecyclerView recycler;
            ImageView nivel;
            TextView categoria;
            TextView cantidad;
            public ViewHolderDatos(View itemView,RecyclerView recycler){
                super(itemView);
                categoria = itemView.findViewById(R.id.categoria);
                cantidad = itemView.findViewById(R.id.cantidad);
                nivel = itemView.findViewById(R.id.nivel);
                this.recycler=recycler;
            }
            public void asignarDatos(int position){
                int nivel = R.drawable.nivel_1;
                String categoria="Principiante";
                String cantidad="1 Km";
                switch(position){
                    case 1:
                        nivel=R.drawable.nivel_2;
                        categoria="Corredor";
                        cantidad="5 Km";
                        break;
                    case 2:
                    nivel=R.drawable.nivel_3;
                        categoria="Atleta";
                        cantidad="20 Km";
                        break;
                    case 3:
                    nivel=R.drawable.nivel_4;
                        categoria="Avanzado";
                        cantidad="50 Km";
                        break;
                    case 4:
                    nivel=R.drawable.nivel_5;
                        categoria="Experto";
                        cantidad="200 Km";
                        break;
                }
                this.nivel.setImageResource(nivel);
                this.categoria.setText(categoria);
                this.cantidad.setText(cantidad);
            }
        }
    }

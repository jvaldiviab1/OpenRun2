package com.jvaldiviab.openrun2.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.data.repository.ActividadesRepository;
import com.jvaldiviab.openrun2.data.repository.ActividadesRepositoryImpl;
import com.jvaldiviab.openrun2.util.UtilActividades;
import com.jvaldiviab.openrun2.viewmodel.TodosViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {
    List<UtilActividades> listDatos;
    FragmentActivity activity;
    RecyclerView recycler;
    public AdapterDatos(List<UtilActividades> listDatos,FragmentActivity activity,RecyclerView recycler){
        this.listDatos=listDatos;
        this.activity=activity;
        this.recycler=recycler;
    }
    @NonNull
    @Override
    public AdapterDatos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        return new ViewHolderDatos(view,activity,recycler);
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

        ActividadesRepository actividadesRepository;
        TodosViewModel viewModel;
        RecyclerView recycler;
        TextView fecha;
        TextView nota;
        TextView tiempo;
        TextView codigo;
        TextView estado;
        Button eliminar;
        Button inicioOVista;
        String ID;
        public ViewHolderDatos(View itemView, FragmentActivity activity,RecyclerView recycler){
            super(itemView);
            actividadesRepository= new ActividadesRepositoryImpl();
            viewModel =new ViewModelProvider(activity).get(TodosViewModel.class);
            ID=viewModel.getIdUser();
            fecha = itemView.findViewById(R.id.fecha);
            nota = itemView.findViewById(R.id.nota);
            tiempo = itemView.findViewById(R.id.tiempo);
            codigo = itemView.findViewById(R.id.codigo);
            estado = itemView.findViewById(R.id.estado);
            eliminar = itemView.findViewById(R.id.eliminar);
            inicioOVista=itemView.findViewById(R.id.segunEstado);
            this.recycler=recycler;
        }
        public void asignarDatos(UtilActividades datos){
            fecha.setText(datos.getFecha()+" "+datos.getHora());
            nota.setText(datos.getNota());
            tiempo.setText(datos.getTiempo());
            codigo.setText(datos.getCodigo());
            estado.setText(datos.getEstado());
            if(datos.getEstado()=="1"){//Actividad Realizada
                inicioOVista.setBackground(ContextCompat.getDrawable(activity.getApplicationContext(),R.drawable.ic_visibility_black_24dp));
            }
            else{//Actividad Pendiente
                inicioOVista.setBackground(ContextCompat.getDrawable(activity.getApplicationContext(),R.drawable.ic_play_black_24dp));
            }
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actividadesRepository.DeleteActividades(datos);
                    viewModel.getListActividades(datos.getFecha(),recycler,activity);
                }
            });
            inicioOVista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(datos.getEstado()=="1"){//Actividad Realizada
                        //PONER CODIGO PARA VER EL DETALLE DE LA ACTIVIDAD
                    }
                    else{//Actividad Pendiente
                        //PONER CODIGO PARA INICAR LA ACTIVIDA
                    }
                }
            });
        }
    }
}

package com.jvaldiviab.openrun2.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.data.model.AdapterDatos;

import java.util.ArrayList;

public class TodosFragment extends GeneralFragment {
    ArrayList<String> listDatos;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_todos, container, false);
        final RecyclerView ReVi = root.findViewById(R.id.recyclerView);
        final CalendarView CV= root.findViewById(R.id.calendarView);
        CV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Bundle bun = new Bundle();
                bun.putString("fecha",i2+"/"+i1+"/"+i);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,TodosDetaFragment.class,bun).commit();
                Toast.makeText(getActivity(),i2+"/"+i1+"/"+i,Toast.LENGTH_LONG).show();
            }
        });
        ReVi.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        listDatos= new ArrayList<String>();
        for(int i=0;i<=10;i++){
            listDatos.add("Dato #"+i+" ");
        }
        AdapterDatos adapterDatos= new AdapterDatos(listDatos);
        ReVi.setAdapter(adapterDatos);
        return root;
    }
}
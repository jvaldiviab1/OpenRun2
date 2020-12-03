package com.jvaldiviab.openrun2.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.data.model.AdapterDatos;
import com.jvaldiviab.openrun2.view.activity.PopUpActivity;

import java.util.ArrayList;

public class TodosDetaFragment extends GeneralFragment {
    ArrayList<String> listDatos;
    Button agregar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_todos_deta, container, false);
        final RecyclerView ReVi = root.findViewById(R.id.recyclerViewDeta);
        agregar=root.findViewById(R.id.BuAddTask);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PopUpActivity.class));
            }
        });
        //ReVi.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        ReVi.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        listDatos= new ArrayList<String>();
        for(int i=0;i<=10;i++){
            listDatos.add("Dato #"+i+" ");
        }
        AdapterDatos adapterDatos= new AdapterDatos(listDatos);
        ReVi.setAdapter(adapterDatos);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,TodosFragment.class, new Bundle()).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback((LifecycleOwner) this.getContext(), callback);
        return root;

    }
}
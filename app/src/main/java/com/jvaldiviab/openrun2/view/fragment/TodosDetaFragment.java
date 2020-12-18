package com.jvaldiviab.openrun2.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.data.repository.ActividadesRepository;
import com.jvaldiviab.openrun2.databinding.FragmentTodosBinding;
import com.jvaldiviab.openrun2.databinding.FragmentTodosDetaBinding;
import com.jvaldiviab.openrun2.util.UtilActividades;
import com.jvaldiviab.openrun2.view.adapter.AdapterDatos;
import com.jvaldiviab.openrun2.view.activity.PopUpActivity;
import com.jvaldiviab.openrun2.viewmodel.TodosViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodosDetaFragment extends Fragment {
    private TodosViewModel viewModel;
    private FragmentTodosDetaBinding binding;
    String ID="",fecha="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle B=this.getArguments();
        fecha=B.getString("fecha");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTodosDetaBinding.inflate(getLayoutInflater());
        viewModel =new ViewModelProvider(getActivity()).get(TodosViewModel.class);
        //ReVi.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.recyclerViewDeta.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        viewModel.getListActividades(fecha,binding.recyclerViewDeta,getActivity());


        return binding.getRoot();

    }
}
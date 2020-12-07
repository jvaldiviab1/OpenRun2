package com.jvaldiviab.openrun2.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.data.repository.ActividadesRepository;
import com.jvaldiviab.openrun2.databinding.FragmentMusicBinding;
import com.jvaldiviab.openrun2.databinding.FragmentTodosBinding;
import com.jvaldiviab.openrun2.util.UtilActividades;
import com.jvaldiviab.openrun2.view.adapter.AdapterDatos;
import com.jvaldiviab.openrun2.data.repository.FireBaseRepository;
import com.jvaldiviab.openrun2.viewmodel.MusicViewModel;
import com.jvaldiviab.openrun2.viewmodel.TodosViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodosFragment extends GeneralFragment {
    private TodosViewModel viewModel;
    private FragmentTodosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTodosBinding.inflate(getLayoutInflater());
        viewModel =new ViewModelProvider(getActivity()).get(TodosViewModel.class);
        String ID=viewModel.getIdUser();
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Bundle bun = new Bundle();
                bun.putString("fecha",String.format("%02d", i2)+"/"+String.format("%02d", i1+1)+"/"+i);
                bun.putString("user",ID);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,TodosDetaFragment.class,bun).commit();
                //Toast.makeText(getActivity(),i2+"/"+i1+"/"+i,Toast.LENGTH_LONG).show();
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false));
        String Fecha=new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        viewModel.getListActividades(ID,Fecha,binding.recyclerView);
        return binding.getRoot();
    }
}
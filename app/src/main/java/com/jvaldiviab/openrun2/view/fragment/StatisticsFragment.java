package com.jvaldiviab.openrun2.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.data.model.RunPojo;
import com.jvaldiviab.openrun2.databinding.FragmentStatisticsBinding;
import com.jvaldiviab.openrun2.viewmodel.ProfileViewModel;
import com.jvaldiviab.openrun2.viewmodel.StatisticsViewModel;

import java.util.ArrayList;
import java.util.Map;

public class StatisticsFragment extends Fragment {

    FragmentStatisticsBinding binding;
    StatisticsViewModel viewModel;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(getActivity()).get(StatisticsViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("users").child(currentUser.getUid()).child("history");


        retrieveData();


    }

    private void retrieveData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Float> data2= new ArrayList<Float>();

                if(snapshot.hasChildren()){
                    for(DataSnapshot myData :snapshot.getChildren()){
                        RunPojo rn = myData.getValue(RunPojo.class);
                        float  tt = Float.parseFloat(String.format(rn.getDistance()+"f"));
                        data2.add(tt);

                    }

                    float[] data = new float[7];

                    for(int i = 0; i<7;i++){
                        data[6-i]= data2.get(data2.size()-1-i);
                    }



                    binding.lineChart.setData(data, new String[]{"Día 1", "Día 2", "Día 3", "Día 4", "Día 5", "Día 6", "Día 7"});
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
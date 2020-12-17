package com.jvaldiviab.openrun2.view.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.jvaldiviab.openrun2.databinding.FragmentProfileBinding;
import com.jvaldiviab.openrun2.viewmodel.ProfileViewModel;


public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    FragmentProfileBinding binding;
    ProfileViewModel viewModel;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private StorageReference storage;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        viewModel.updateProfile();

        viewModel.getProfileLiveData().observe(getViewLifecycleOwner(), usersPojo -> {
                    if (usersPojo != null) {
                        binding.nameProfile.setText(usersPojo.getName());
                        binding.descriptionProfile.setText(usersPojo.getDescription());
                        binding.weightProfile.setText(usersPojo.getWeight());
                        binding.ageProfile.setText(usersPojo.getAge());
                        binding.typProfile.setText(usersPojo.getBodyType());
                        binding.limitProfile.setText(usersPojo.getTrainingType());
                        binding.goalProfile.setText(usersPojo.getTargetWeight());
                        binding.caloriesProfile.setText("usersPojo.getcalories()");
                    }
                }
        );
        binding.medallas.setLayoutManager(new GridLayoutManager(this.getContext(),3));
        //viewModel.listarMedallas(binding.medallas);
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
    }


}
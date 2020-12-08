package com.jvaldiviab.openrun2.view.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.data.model.UsersPojo;
import com.jvaldiviab.openrun2.data.repository.FireBaseRepository;
import com.jvaldiviab.openrun2.data.var.Constants;
import com.jvaldiviab.openrun2.databinding.FragmentProfileBinding;
import com.jvaldiviab.openrun2.viewmodel.MusicViewModel;
import com.jvaldiviab.openrun2.viewmodel.ProfileViewModel;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    ProfileViewModel viewModel;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private StorageReference storage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        init();
        viewModel =new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        viewModel.updateProfile();

        viewModel.getProfileLiveData().observe(getViewLifecycleOwner(), usersPojo ->{
                    System.out.println(usersPojo.getName());
                    viewModel.updateUser(usersPojo);

                }
        );
        binding.setProfileViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);








        System.out.println(firebaseUser.getEmail());

    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
    }



}
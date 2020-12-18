package com.jvaldiviab.openrun2.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.data.model.RunPojo;
import com.jvaldiviab.openrun2.databinding.FragmentMapHistoryBinding;
import com.jvaldiviab.openrun2.view.adapter.AdapterHistory;


public class MapHistoryFragment extends Fragment {


    FragmentMapHistoryBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private AdapterHistory<RunPojo, AdapterHistory.ViewHolder> adapter;

    public static MapHistoryFragment newInstance() {

        Bundle args = new Bundle();

        MapHistoryFragment fragment = new MapHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MapHistoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMapHistoryBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.listHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.listHistory.setHasFixedSize(true);


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Query query = FirebaseDatabase.getInstance()
                .getReference().child("users").child(currentUser.getUid())
                .child("history");

        fetch(query);
    }

    private void fetch(Query query) {

        FirebaseRecyclerOptions<RunPojo> options =
                new FirebaseRecyclerOptions.Builder<RunPojo>()
                        .setQuery(query, new SnapshotParser<RunPojo>() {
                            @NonNull
                            @Override
                            public RunPojo parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new RunPojo(
                                        snapshot.child("avgPace").getValue().toString(),
                                        snapshot.child("date").getValue().toString(),
                                        snapshot.child("distance").getValue().toString(),
                                        snapshot.child("time").getValue().toString());
                            }
                        })
                        .build();

        adapter = new AdapterHistory<RunPojo, AdapterHistory.ViewHolder>(options);
        adapter.startListening();
        binding.listHistory.setAdapter(adapter);
    }
}
package com.jvaldiviab.openrun2.view.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.data.repository.TrackRepository;
import com.jvaldiviab.openrun2.databinding.FragmentMusicBinding;
import com.jvaldiviab.openrun2.util.UtilsFragments;
import com.jvaldiviab.openrun2.view.adapter.MusicListAdapter;
import com.jvaldiviab.openrun2.viewmodel.MusicViewModel;

public class MusicFragment extends Fragment {


    private FragmentMusicBinding binding;

    private MusicViewModel viewModel;

    private MusicListAdapter adapter;
    private SearchView searchView;
    private ImageButton repeatBtn, shuffleBtn;

    private TrackFragment trackFragment = TrackFragment.newInstance();

    public static MusicFragment newInstance() {

        Bundle args = new Bundle();

        MusicFragment fragment = new MusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MusicFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMusicBinding.inflate(getLayoutInflater());

        viewModel =new ViewModelProvider(getActivity()).get(MusicViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findComponents();

        setMusicAdapter();

        setEvents();

        makeSearchViewWhite();

        setOnChangeEvents();

        UtilsFragments.changeFragment(getChildFragmentManager(),trackFragment,R.id.fragment_container_view,true,"TRACK");




    }

    private void setMusicAdapter() {
        adapter = new MusicListAdapter(viewModel.getTrackList());
        binding.musicRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.musicRecycler.setAdapter(adapter);
    }

    @SuppressLint("ResourceType")
    private void setOnChangeEvents() {

        viewModel.isShuffle().observe(getViewLifecycleOwner(), isShuffle -> shuffleBtn.setBackgroundColor(Color.parseColor(isShuffle ? getResources().getString(R.color.black) : "#00FFFFFF")));

        viewModel.isRepeating().observe(getViewLifecycleOwner(), isRepeating -> {
            repeatBtn.setBackgroundColor(Color.parseColor(isRepeating ? getResources().getString(R.color.black_one) : "#00FFFFFF"));
            repeatBtn.setImageDrawable(isRepeating ? getResources().getDrawable(R.drawable.ic_repeat_black_24dp) : getResources().getDrawable(R.drawable.ic_repeat_black_24dp));
        });

        viewModel.getPlayingTrack().observe(getViewLifecycleOwner(), track -> {
            viewModel.setTrack(track);
            adapter.notifyDataSetChanged();
        });

    }

    private void setEvents() {

        // Shuffle

        shuffleBtn.setOnClickListener(shuffleBtn -> TrackRepository.getInstance().switchShuffle());

        // Repeat Button

        repeatBtn.setOnClickListener(repeatBtn -> TrackRepository.getInstance().switchRepeating());

        // SearchView Events

        searchView.setOnSearchClickListener(searchViewBox -> {
            repeatBtn.setVisibility(View.GONE);
            shuffleBtn.setVisibility(View.GONE);
            searchView.setVisibility(View.VISIBLE);
        });

        searchView.setOnCloseListener(() -> {
            repeatBtn.setVisibility(View.VISIBLE);
            shuffleBtn.setVisibility(View.VISIBLE);
            return false;
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchingString) {
                adapter.setTracks(viewModel.search(searchingString));
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    private void findComponents() {
        shuffleBtn = binding.shuffleButton;
        repeatBtn = binding.repeatButton;
        searchView = binding.musicSearchView;
    }

    @SuppressLint("ResourceType")
    private void makeSearchViewWhite() {
        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextColor(Color.parseColor(getResources().getString(R.color.white)));
    }




}

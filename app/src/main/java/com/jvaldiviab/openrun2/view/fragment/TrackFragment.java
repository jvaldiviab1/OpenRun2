package com.jvaldiviab.openrun2.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.databinding.FragmentTrackBinding;
import com.jvaldiviab.openrun2.viewmodel.MusicViewModel;
import com.squareup.picasso.Picasso;

public class TrackFragment extends Fragment {


    private FragmentTrackBinding binding;
    private MusicViewModel viewModel;


    public static TrackFragment newInstance() {

        Bundle args = new Bundle();

        TrackFragment fragment = new TrackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TrackFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTrackBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(getActivity()).get(MusicViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setMusicViewModel(viewModel);

        setEvents();

        setOnChangeEvents();

    }

    private void setEvents() {


        //binding.getRoot().setOnClickListener(barView -> startActivity(TrackPlayerActivity.newIntent(getActivity())));

        binding.buttonPlay.setOnClickListener(btnView -> viewModel.onPlayPauseBtnClicked());

        binding.buttonNext.setOnClickListener(skipNextBtn -> viewModel.onNextBtnClicked());

        binding.buttonPrev.setOnClickListener(skipPreviousBtn -> viewModel.onPreviousBtnClicked());



    }

    private void setOnChangeEvents() {

        viewModel.isPlayingMusic().observe(getViewLifecycleOwner(), playing -> binding.buttonPlay.setImageDrawable(getResources().getDrawable(playing ? R.drawable.ic_pause_black_24dp : R.drawable.ic_play_black_24dp)));

        viewModel.getPlayingTrack().observe(getViewLifecycleOwner(), track -> {
            binding.setMusicViewModel(viewModel);
            Picasso.get().load(viewModel.getPlayingCoverSrc()).placeholder(getResources().getDrawable(R.drawable.music_icon)).into(binding.musicBarFragmentTrackImage);
        });

        viewModel.getPlayingTrack().observe(getViewLifecycleOwner(), playingTrack -> {
            if (playingTrack != null) binding.getRoot().setVisibility(View.VISIBLE);
            else binding.getRoot().setVisibility(View.GONE);
        });

    }

}

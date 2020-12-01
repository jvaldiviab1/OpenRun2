package com.jvaldiviab.openrun2.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.data.model.TrackPojo;
import com.jvaldiviab.openrun2.databinding.LayoutTrackBinding;
import com.jvaldiviab.openrun2.viewmodel.MusicViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder> {

    private List<TrackPojo> tracks;

    private Activity activity;

    public MusicListAdapter(List<TrackPojo> tracks) {
        this.tracks = tracks;
    }

    public MusicListAdapter() {
        this.tracks = new ArrayList<>();
    }

    @NonNull
    @Override
    public MusicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        LayoutTrackBinding bindingData = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.layout_track, parent, false);
        return new MusicListViewHolder(bindingData);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListViewHolder holder, int position) {
        holder.bind(tracks.get(position));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class MusicListViewHolder extends RecyclerView.ViewHolder {

        private LayoutTrackBinding binding;

        private MusicViewModel viewModel;


        public MusicListViewHolder(@NonNull LayoutTrackBinding binding) {
            super(binding.getRoot());


            this.binding = binding;
            this.viewModel = new ViewModelProvider((FragmentActivity) activity).get(MusicViewModel.class);

        }

        @SuppressLint("ResourceType")
        public void bind(final TrackPojo track) {

            viewModel.setTrack(track);
            binding.setMusicViewModel(viewModel);
            binding.executePendingBindings();


            // Track Cover
            Picasso.get().load(viewModel.getCoverSrc()).placeholder(activity.getResources().getDrawable(R.drawable.music_icon)).into(binding.trackLayoutTrackImage);

            binding.trackLayoutCardView.setBackgroundColor(Color.parseColor(activity.getString((viewModel.isPlayingTrack()) ? R.color.rose : R.color.black)));

            binding.trackLayoutCardView.setOnClickListener(view -> {
                viewModel.setTrack(track);
                viewModel.setPlayingList(tracks);
                viewModel.onTrackClicked();
                notifyDataSetChanged();
            });

        }

    }


    public void setTracks(List<TrackPojo> tracks) {
        this.tracks = tracks;
        notifyDataSetChanged();
    }


}

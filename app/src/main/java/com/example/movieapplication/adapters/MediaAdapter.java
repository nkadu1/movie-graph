package com.example.movieapplication.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movieapplication.databinding.MediaCardBinding;
import com.example.movieapplication.model.MediaList;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaItemViewHolder>{

    private List<MediaList> mediaList = new ArrayList<>();

    @Inject
    public MediaAdapter() {
        this.setHasStableIds(true);
    }

    @NonNull
    @Override
    public MediaItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MediaCardBinding mediaCardBinding = MediaCardBinding.inflate(layoutInflater, parent, false);
        return new MediaItemViewHolder(mediaCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaItemViewHolder holder, int position) {
        holder.bind(mediaList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return mediaList.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return this.mediaList.size();
    }

    public class MediaItemViewHolder extends RecyclerView.ViewHolder {

        private final MediaCardBinding mediaCardBinding;

        MediaItemViewHolder(@NonNull MediaCardBinding mediaCardBinding){
            super(mediaCardBinding.getRoot());
            this.mediaCardBinding = mediaCardBinding;
        }

        void bind(MediaList mediaList) {
        }



    }
}

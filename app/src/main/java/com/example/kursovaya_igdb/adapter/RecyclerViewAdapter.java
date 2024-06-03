package com.example.kursovaya_igdb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovaya_igdb.R;
import com.example.kursovaya_igdb.ui.gameDetails.GameActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private final ArrayList<RecyclerData> dataArrayList;
    private final Context mcontext;
    private final boolean relatedGames;

    public boolean isRelatedGames() {
        return relatedGames;
    }

    public RecyclerViewAdapter(ArrayList<RecyclerData> recyclerDataArrayList, Context mcontext, boolean relatedGames) {
        this.dataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
        this.relatedGames = relatedGames;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_game, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        RecyclerData recyclerData = dataArrayList.get(position);
        String newUrl = recyclerData.getImgUrl().replace("thumb", "cover_big");
        ImageView x;
        if (isRelatedGames())
            x = holder.smallCover;
        else
            x = holder.cover;
        Picasso.get().load(newUrl).into(x);

        x.setOnClickListener(v -> {
            Intent myIntent = new Intent(mcontext, GameActivity.class);
            myIntent.putExtra("idGame", recyclerData.getId());
            mcontext.startActivity(myIntent);
        });
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView cover;
        private final ImageView smallCover;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            smallCover = itemView.findViewById(R.id.smallCover);
            cover = itemView.findViewById(R.id.cover);
            if (isRelatedGames()) {
                cover.setVisibility(View.GONE);
                smallCover.setVisibility(View.VISIBLE);
            } else {
                cover.setVisibility(View.VISIBLE);
                smallCover.setVisibility(View.GONE);
            }
        }

    }
}

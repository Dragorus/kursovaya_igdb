package com.example.kursovaya_igdb.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovaya_igdb.R;
import com.example.kursovaya_igdb.model.GameApiResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {
    private final List<GameApiResponse> games;

    public HomeAdapter(List<GameApiResponse> games) {
        this.games = games;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemhome, parent, false);
        return new HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        GameApiResponse game = games.get(position);
        String newUrl = game.getCover().getUrl().replace("thumb","cover_big");
        Picasso.get().load(newUrl).into(holder.cover);
        holder.cover.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idGame", game.getId());
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_gameActivity, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}

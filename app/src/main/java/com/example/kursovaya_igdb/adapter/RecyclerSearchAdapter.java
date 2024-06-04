package com.example.kursovaya_igdb.adapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovaya_igdb.model.GameApiResponse;
import com.example.kursovaya_igdb.R;
import com.squareup.picasso.Picasso;
import java.util.List;


public class RecyclerSearchAdapter extends RecyclerView.Adapter<RecyclerSearchAdapter.RecyclerViewHolder> {
    private List<GameApiResponse> games;
    public RecyclerSearchAdapter(List<GameApiResponse> games, Context context) {
        this.games = games;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_card_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerSearchAdapter.RecyclerViewHolder holder, int position) {
        GameApiResponse game = games.get(position);
        if(game.getCover() != null){
            Picasso.get().load(game.getCover().getUrl().replace("thumb", "cover_big")).into(holder.coverIV);
        }else{
            holder.coverIV.setImageResource(R.drawable.no_cover);
        }

        holder.gameCV.setTooltipText(game.getName());

        holder.gameCV.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idGame", game.getId());
            Navigation.findNavController(v).navigate(R.id.action_searchFragment_to_gameActivity, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView coverIV;
        private final CardView gameCV;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            coverIV = itemView.findViewById(R.id.card_game_image_IV);
            gameCV = itemView.findViewById(R.id.game_CV);
        }
    }
    public void setGames(List<GameApiResponse> games) {
        this.games = games;
    }

}

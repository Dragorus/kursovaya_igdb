package com.example.kursovaya_igdb.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovaya_igdb.R;

public class HomeViewHolder extends RecyclerView.ViewHolder {
    final ImageView cover;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        cover = itemView.findViewById(R.id.imageView);
    }
}

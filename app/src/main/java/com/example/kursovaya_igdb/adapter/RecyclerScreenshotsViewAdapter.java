package com.example.kursovaya_igdb.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovaya_igdb.R;
import com.example.kursovaya_igdb.ui.gameDetails.ScreenshotFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerScreenshotsViewAdapter extends RecyclerView.Adapter<RecyclerScreenshotsViewAdapter.RecyclerViewHolder> {

    private final ArrayList<RecyclerData> dataArrayList;

    public RecyclerScreenshotsViewAdapter(ArrayList<RecyclerData> recyclerDataArrayList, Context mcontext) {
        this.dataArrayList = recyclerDataArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.screenshot_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        RecyclerData recyclerData = dataArrayList.get(position);
        String newUrl = recyclerData.getImgUrl().replace("thumb", "screenshot_huge");
        Log.i("adapter", newUrl);
        Picasso.get().load(newUrl).into(holder.screenshot);
        holder.screenshot.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            CoordinatorLayout coordinatorLayout = activity.findViewById(R.id.scrollView);
            coordinatorLayout.setVisibility(View.INVISIBLE);

            Fragment fragment = new ScreenshotFragment();
            Bundle bundle = new Bundle();
            ArrayList<String> list = new ArrayList<>();
            for (RecyclerData data : dataArrayList){
                list.add(data.getImgUrl().replace("thumb", "screenshot_huge"));
            }
            bundle.putString("currentImage", newUrl);
            bundle.putInt("position", position);
            bundle.putStringArrayList("imageUrl",list);
            fragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.screenshotContainer, fragment).commit();
        });
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView screenshot;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            screenshot = itemView.findViewById(R.id.screenshot);
            screenshot.setVisibility(View.VISIBLE);
            ImageView screenshotBig = itemView.findViewById(R.id.screenshotBig);
            screenshotBig.setVisibility(View.GONE);
        }
    }
}


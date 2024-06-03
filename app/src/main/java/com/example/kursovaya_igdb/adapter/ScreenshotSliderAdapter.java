package com.example.kursovaya_igdb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kursovaya_igdb.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ScreenshotSliderAdapter extends SliderViewAdapter<ScreenshotSliderAdapter.Holder> {
    private final ArrayList<String> images;
    public ScreenshotSliderAdapter(ArrayList<String> images){
        this.images = images;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.screenshot_layout, parent, false);
        return new Holder(view);
    }
    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        Picasso.get().load(images.get(position)).into(viewHolder.imageViewBig);
    }
    @Override
    public int getCount() {
        return images.size();
    }
    public static class Holder extends SliderViewAdapter.ViewHolder {
        final ImageView imageViewBig;
        final ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.screenshot);
            imageView.setVisibility(View.GONE);
            imageViewBig = itemView.findViewById(R.id.screenshotBig);
            imageViewBig.setVisibility(View.VISIBLE);
        }
    }
}

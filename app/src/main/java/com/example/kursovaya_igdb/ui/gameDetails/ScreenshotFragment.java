package com.example.kursovaya_igdb.ui.gameDetails;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kursovaya_igdb.R;
import com.example.kursovaya_igdb.adapter.ScreenshotSliderAdapter;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class ScreenshotFragment extends Fragment {

    private ArrayList<String> list;
    private int position;
    public ScreenshotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = getArguments().getStringArrayList("imageUrl");
            position = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_screenshot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SliderView sliderView = requireView().findViewById(R.id.image_slider);
        ScreenshotSliderAdapter sliderAdapter = new ScreenshotSliderAdapter(list);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setCurrentPagePosition(position);
    }
}

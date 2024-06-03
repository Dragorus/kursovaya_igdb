package com.example.kursovaya_igdb.ui.gameDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kursovaya_igdb.adapter.RecyclerData;
import com.example.kursovaya_igdb.adapter.RecyclerViewAdapter;
import com.example.kursovaya_igdb.model.GameApiResponse;
import com.example.kursovaya_igdb.R;
import com.example.kursovaya_igdb.ui.viewModel.GamesViewModel;
import com.google.android.material.button.MaterialButton;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class CompanyActivity extends AppCompatActivity {
    private ArrayList<RecyclerData> recyclerDataArrayList;
    private ProgressBar progressBar;
    private GamesViewModel gamesViewModel;
    private MaterialButton sorting;
    private RecyclerViewAdapter adapter;
    private boolean reverse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_company);

        Intent intent = getIntent();
        String company = intent.getStringExtra("nameCompany");
        TextView companyTitleView = findViewById(R.id.companyTitle);
        MaterialTextView descriptionView = findViewById(R.id.companyDescription);

        progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.companyRecyclerView);
        sorting = findViewById(R.id.sorting);

        recyclerDataArrayList=new ArrayList<>();
        adapter = new RecyclerViewAdapter(recyclerDataArrayList,this, false);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if (company != null){
            progressBar.setVisibility(View.VISIBLE);
            companyTitleView.setText(company);

        } else {
            sorting.setVisibility(View.GONE);
            companyTitleView.setText(R.string.no_results);
        }


    }
    String sortingParameter = "";
    private int lastSelectedSortingParameter = 1;

    @SuppressLint("NotifyDataSetChanged")
    public void showGames(List<GameApiResponse> gamesList) {
        progressBar.setVisibility(View.GONE);
        recyclerDataArrayList.clear();
        for (GameApiResponse gameApiResponse : gamesList) {
            if (gameApiResponse.getCover() != null)
                recyclerDataArrayList.add(new RecyclerData(gameApiResponse.getId(), gameApiResponse.getCover().getUrl()));
        }
        adapter.notifyDataSetChanged();
    }

}
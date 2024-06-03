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
import android.widget.Toast;

import com.example.kursovaya_igdb.adapter.RecyclerData;
import com.example.kursovaya_igdb.adapter.RecyclerViewAdapter;
import com.example.kursovaya_igdb.model.GameApiResponse;
import com.example.kursovaya_igdb.R;
import com.example.kursovaya_igdb.ui.viewModel.GamesViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class FranchiseActivity extends AppCompatActivity {
    private ArrayList<RecyclerData> recyclerDataArrayList;
    private ProgressBar progressBar;
    private GamesViewModel gamesViewModel;
    private MaterialButton sorting;
    private RecyclerViewAdapter adapter;
    private boolean reverse;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_franchise);
        Intent intent = getIntent();
        String franchise = intent.getStringExtra("nameFranchise");
        TextView franchiseTitleView = findViewById(R.id.franchiseTitle);

        progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.franchiseRecyclerView);
        sorting = findViewById(R.id.sorting_F);
        recyclerDataArrayList=new ArrayList<>();
        adapter = new RecyclerViewAdapter(recyclerDataArrayList,this, false);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if (franchise != null) {
            franchiseTitleView.setText(franchise);
        }  else {
            sorting.setVisibility(View.GONE);
            franchiseTitleView.setText(R.string.no_results);
        }

    }
    String sortingParameter = "";
    private int lastSelectedSortingParameter = 1;
    private void setSorting(List<GameApiResponse> games) {
        sorting.setOnClickListener(v -> {
            final String[] listItems = getResources().getStringArray(R.array.sorting_parameters);
            sortingParameter = listItems[1];
            new MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.sort_by_dialog_title)
                    .setSingleChoiceItems(listItems, lastSelectedSortingParameter, (dialog, i) -> {
                        sortingParameter = listItems[i];
                        lastSelectedSortingParameter = i;
                    })
                    .setPositiveButton(R.string.confirm_text, (dialog, which) -> {
                        showGames(games);
                        Toast.makeText(this, R.string.no_connection_message, Toast.LENGTH_LONG).show();
                    }).setNegativeButton(R.string.cancel_text, (dialogInterface, i) -> dialogInterface.dismiss()).show();
        });
    }

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
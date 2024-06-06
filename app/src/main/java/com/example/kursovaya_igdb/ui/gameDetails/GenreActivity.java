package com.example.kursovaya_igdb.ui.gameDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.kursovaya_igdb.repository.IGamesRepository;
import com.example.kursovaya_igdb.ui.viewModel.GamesViewModel;
import com.example.kursovaya_igdb.ui.viewModel.GamesViewModelFactory;
import com.example.kursovaya_igdb.util.ServiceLocator;
import com.example.kursovaya_igdb.util.sort.SortByAlphabet;
import com.example.kursovaya_igdb.util.sort.SortByBestRating;
import com.example.kursovaya_igdb.util.sort.SortByMostPopular;
import com.example.kursovaya_igdb.util.sort.SortByMostRecent;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenreActivity extends AppCompatActivity{
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
        setContentView(R.layout.activity_genre);
        progressBar = findViewById(R.id.progressBar);
        sorting = findViewById(R.id.sorting_G);
        Intent intent = getIntent();
        String genre = intent.getStringExtra("genreName");

        TextView genreTitle = findViewById(R.id.genreTitle);
        genreTitle.setText(genre);

        RecyclerView recyclerView = findViewById(R.id.genreRecyclerView);
        recyclerDataArrayList=new ArrayList<>();
        adapter = new RecyclerViewAdapter(recyclerDataArrayList,this, false);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if (checkNetwork()) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            genreTitle.setText(R.string.no_connection);
            sorting.setVisibility(View.GONE);
        }
        IGamesRepository iGamesRepository;
        try {
            iGamesRepository = ServiceLocator.getInstance().getGamesRepository(getApplication());
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        if (iGamesRepository != null) {
            gamesViewModel = new ViewModelProvider(this, new GamesViewModelFactory(iGamesRepository)).get(GamesViewModel.class);
        }

        gamesViewModel.getGenreGames(genre).observe(this, result -> {
            progressBar.setVisibility(View.GONE);
            Collections.sort(result, new SortByMostRecent());
            showGames(result);
            setSorting(result);
        });
    }

    private boolean checkNetwork() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
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
    String sortingParameter = "";
    private int lastSelectedSortingParameter = 1;
    private void setSorting(List<GameApiResponse> games) {
        sorting.setOnClickListener(v -> {
            final String[] listItems = getResources().getStringArray(R.array.sorting_parameters);
            final View customLayout = getLayoutInflater().inflate(R.layout.dialog_sort, null);
            MaterialSwitch switchView = customLayout.findViewById(R.id.materialSwitch);
            new MaterialAlertDialogBuilder(this)
                    .setView(customLayout)
                    .setTitle(R.string.sort_by_dialog_title)
                    .setSingleChoiceItems(listItems, lastSelectedSortingParameter, (dialog, i) -> {
                        sortingParameter = listItems[i];
                        lastSelectedSortingParameter = i;
                    })
                    .setPositiveButton(R.string.confirm_text, (dialog, which) -> {
                        if (checkNetwork() || !games.isEmpty()) {
                            reverse = switchView.isChecked();
                            sortGames(games, sortingParameter);
                            showGames(games);
                        } else {
                            Toast.makeText(this, R.string.no_connection_message, Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton(R.string.cancel_text, (dialogInterface, i) -> dialogInterface.dismiss()).show();
        });
    }

    public void sortGames(List<GameApiResponse> games, String sortingParameter) {
        switch (sortingParameter) {
            case "Most popular":
                Collections.sort(games, new SortByMostPopular());
                break;
            case "Most recent":
                Collections.sort(games, new SortByMostRecent());
                break;
            case "Best rating":
                Collections.sort(games, new SortByBestRating());
                break;
            case "Alphabet":
                Collections.sort(games, new SortByAlphabet());
                break;
        }
        if (reverse)
            Collections.reverse(games);
        showGames(games);
    }
}
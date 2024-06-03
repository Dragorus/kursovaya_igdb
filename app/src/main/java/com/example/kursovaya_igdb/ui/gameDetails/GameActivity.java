package com.example.kursovaya_igdb.ui.gameDetails;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.example.kursovaya_igdb.adapter.RecyclerData;
import com.example.kursovaya_igdb.adapter.RecyclerScreenshotsViewAdapter;
import com.example.kursovaya_igdb.adapter.RecyclerViewAdapter;
import com.example.kursovaya_igdb.model.GameApiResponse;
import com.example.kursovaya_igdb.R;
import com.example.kursovaya_igdb.repository.IGamesRepository;
import com.example.kursovaya_igdb.ui.viewModel.GamesViewModel;
import com.example.kursovaya_igdb.ui.viewModel.GamesViewModelFactory;
import com.example.kursovaya_igdb.util.Constants;
import com.example.kursovaya_igdb.util.ServiceLocator;
import com.example.kursovaya_igdb.util.SharedPreferencesUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.RoundingMode;
import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private GameApiResponse game;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArrayList<RecyclerData> recyclerDataArrayList;
    private Button wantedButton;
    private Button playedButton;

    private GamesViewModel gamesViewModel;
    private Calendar today;

    public Button getWantedButton() {
        return wantedButton;
    }

    public Button getPlayedButton() {
        return playedButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();

        Intent intent = getIntent();
        int idGame = intent.getIntExtra("idGame", 1020);

    }

    @SuppressLint("SetTextI18n")
    public void onSuccess() {
        showGameCover();
        showGameName();
        showReleaseDate();
        showRating();
        showReviewsNumber();
        showGenres();
        showPlatforms();
        setFranchiseButton();
        setCompanyButton();
        setButtons();
        setPlayVideoButton();
        showDescription();
        showScreenshots();
        showRelatedGames();
    }

    private void setPlayVideoButton() {
        Button button = findViewById(R.id.playVideoButton);
        if (game.getVideoId() == null){
            button.setVisibility(View.GONE);
            return;
        }
        button.setOnClickListener(v -> {
            if (game.getVideoId().containsKey("Trailer") && game.getVideoId().containsKey("Gameplay video")){
                DialogFragment fragment = new PlayVideoDialogFragment(game.getVideoId());
                fragment.show(getSupportFragmentManager(), "dialog");
            } else {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + game.getVideos().get(0).getVideo_id()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(Constants.YOUTUBE_URL + game.getVideos().get(0).getVideo_id()));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setButtons() {
        wantedButton = findViewById(R.id.wantedButton);
        playedButton = findViewById(R.id.playedButton);
        wantedButton.setVisibility(View.VISIBLE);
        wantedButton.setText(R.string.wanted);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getApplication());
        wantedButton.setText(R.string.wanted);
        playedButton.setVisibility(View.VISIBLE);
        today = Calendar.getInstance();
        if (new Date(game.getFirstReleaseDate() * 1000L).compareTo(today.getTime()) > 0) {
            playedButton.setVisibility(View.GONE);
        }
    }

    private void showRelatedGames() {
        gamesViewModel.getSimilarGames(game.getSimilarGames()).observe(this, gameApiResponses -> {
            if (gameApiResponses != null && !gameApiResponses.isEmpty()) {
                TextView textView = findViewById(R.id.relatedView);
                textView.setVisibility(View.VISIBLE);
                recyclerView = findViewById(R.id.relatedRecyclerView);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerDataArrayList = new ArrayList<>();

                for (GameApiResponse gameApiResponse : gameApiResponses) {
                    if (gameApiResponse.getCover() != null)
                        recyclerDataArrayList.add(new RecyclerData(gameApiResponse.getId(), gameApiResponse.getCover().getUrl()));
                }
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(recyclerDataArrayList, this, true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });
    }


    private void showScreenshots() {
        recyclerView = findViewById(R.id.screenshotsRecyclerView);
        recyclerDataArrayList = new ArrayList<>();
        if (game.getScreenshots() != null) {
            for (int i = 0; i < game.getScreenshots().size(); i++) {
                if (game.getScreenshots() != null)
                    recyclerDataArrayList.add(new RecyclerData(game.getId(), game.getScreenshots().get(i).getUrl()));
            }
            RecyclerScreenshotsViewAdapter adapter = new RecyclerScreenshotsViewAdapter(recyclerDataArrayList, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showDescription() {
        if (game.getDescription() != null) {
            TextView descriptionView = findViewById(R.id.descriptionView);
            descriptionView.setVisibility(View.VISIBLE);
            TextView descriptionText = findViewById(R.id.descriptionText);
            descriptionText.setText(game.getDescription());
        }
    }

    @SuppressLint("SetTextI18n")
    private void showReviewsNumber() {
        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            ImageView imageView = findViewById(R.id.ratingCountImage);
            imageView.setImageResource(R.drawable.ic_rate_review_light);
        }
        MaterialTextView ratingCount = findViewById(R.id.ratingCount);
        ratingCount.setText(String.valueOf(game.getTotalRatingCount()));
    }

    @SuppressLint("SetTextI18n")
    private void showRating() {
        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            ImageView imageView = findViewById(R.id.ratingImage);
            imageView.setImageResource(R.drawable.ic_thumb_up_light);
        }
        DecimalFormat df = new DecimalFormat("0.0");
        MaterialTextView rating = findViewById(R.id.rating);
        if (game.getTotalRating() != 0.0) {
            double value = game.getTotalRating() / 10;
            df.setRoundingMode(RoundingMode.DOWN);
            rating.setText(df.format(value));
        } else {
            rating.setText(getString(R.string.NoRating));
        }
    }

    private void showPlatforms() {
        TextView platformsView = findViewById(R.id.platformsView);
        platformsView.setVisibility(View.VISIBLE);
        TextView platformText = findViewById(R.id.platformText);
        StringBuilder text = new StringBuilder();
        if (game.getPlatforms() != null) {
            List<String> platforms = game.getPlatformsString();
            for (int i = 0; i < platforms.size(); i++) {
                text.append(platforms.get(i));
                if (i != platforms.size() - 1) {
                    text.append(" - ");
                }
            }
        } else {
            text.append(getString(R.string.no_platforms));
        }
        platformText.setText(text);
    }

    private void showGenres() {
        List<String> genres = game.getGenresString();
        LinearLayout linearLayout = findViewById(R.id.genresLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                120
        );
        params.setMarginEnd(20);
        TextView genresView = findViewById(R.id.genresView);
        genresView.setVisibility(View.VISIBLE);

        for (String genre : genres) {
            MaterialButton button = new MaterialButton(this);
            button.setLayoutParams(params);
            button.setTextSize(16);
            button.setCornerRadius(50);
            button.setText(genre);
            button.setLayoutParams(params);
            button.setOnClickListener(v -> {
                Intent myIntent = new Intent(getApplicationContext(), GenreActivity.class);
                myIntent.putExtra("genreName", genre);
                startActivity(myIntent);
            });
            linearLayout.addView(button);
        }
    }

    private void showReleaseDate() {
        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            ImageView imageView = findViewById(R.id.calendarImage);
            imageView.setImageResource(R.drawable.ic_calendar_light);
        }
        MaterialTextView releaseDateView = findViewById(R.id.releaseDate);
        String date;
        if (game.getFirstReleaseDateString() != null)
            date = game.getFirstReleaseDateString();
        else
            date = getString(R.string.Unreleased);
        releaseDateView.setText(date);
    }

    private void showGameCover() {
        ImageView gameImage = findViewById(R.id.gameCover);
        if (game.getCover() != null) {
            String uriString = game.getCover().getUrl();
            String newUri = uriString.replace("thumb", "cover_big_2x");
            Picasso.get().load(newUri).into(gameImage);
        }
        gameImage.setVisibility(View.VISIBLE);
    }

    private void showGameName() {
        MaterialTextView gameNameView = findViewById(R.id.gameName);
        gameNameView.setText(game.getName());
    }

    private void setFranchiseButton() {
        Button showFranchiseButton = findViewById(R.id.showFranchiseButton);
        showFranchiseButton.setVisibility(View.VISIBLE);
        if (game.getFranchise() != null) {
            showFranchiseButton.setText(game.getFirstFranchise().getName());
        }
        showFranchiseButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(getApplicationContext(), FranchiseActivity.class);
            if (game.getFranchise() != null)
                myIntent.putExtra("nameFranchise", game.getFirstFranchise().getName());
            startActivity(myIntent);
        });
    }

    private void setCompanyButton() {
        Button showCompanyButton = findViewById(R.id.showCompanyButton);
        showCompanyButton.setVisibility(View.VISIBLE);
        if (game.getCompanies() != null) {
            showCompanyButton.setText(game.getInvolvedCompany().getCompany().getName());
        }
        showCompanyButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(getApplicationContext(), CompanyActivity.class);
            if (game.getCompanies() != null)
                myIntent.putExtra("nameCompany", game.getInvolvedCompany().getCompany().getName());
            startActivity(myIntent);
        });
    }

    private void setToolbar() {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.screenshotContainer);
        if (!(fragment instanceof ScreenshotFragment)) {
            super.onBackPressed();
        } else {
            CoordinatorLayout coordinatorLayout = findViewById(R.id.scrollView);
            coordinatorLayout.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            NestedScrollView nestedScrollView = findViewById(R.id.nestedScrollView);
            nestedScrollView.setNestedScrollingEnabled(true);
        }
    }
}

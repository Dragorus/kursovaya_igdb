package com.example.kursovaya_igdb.ui.home;

import static com.example.kursovaya_igdb.util.Constants.LAST_UPDATE_HOME;
import static com.example.kursovaya_igdb.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kursovaya_igdb.adapter.HomeAdapter;
import com.example.kursovaya_igdb.model.GameApiResponse;
import com.example.kursovaya_igdb.R;
import com.example.kursovaya_igdb.repository.IGamesRepository;
import com.example.kursovaya_igdb.ui.viewModel.GamesViewModel;
import com.example.kursovaya_igdb.ui.viewModel.GamesViewModelFactory;
import com.example.kursovaya_igdb.util.ServiceLocator;
import com.example.kursovaya_igdb.util.SharedPreferencesUtil;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView galleryPopular;
    private RecyclerView galleryBestGames;
    private RecyclerView galleryLatestReleases;
    private RecyclerView galleryIncoming;


    LayoutInflater inflater;

    private GamesViewModel gamesViewModel;
    NestedScrollView scrollView;
    private SharedPreferencesUtil sharedPreferencesUtil;

    public HomeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IGamesRepository iGamesRepository;
        try {
            iGamesRepository = ServiceLocator.getInstance().getGamesRepository(requireActivity().getApplication());
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        if (iGamesRepository != null) {
            gamesViewModel = new ViewModelProvider(this, new GamesViewModelFactory(iGamesRepository)).get(GamesViewModel.class);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inflater = LayoutInflater.from(getContext());
        scrollView = requireView().findViewById(R.id.homeFragment);
        scrollView.setVisibility(View.GONE);


        galleryPopular = view.findViewById(R.id.homeGalleryPopular);
        galleryBestGames = view.findViewById(R.id.homeGalleryBestGames);
        galleryLatestReleases = view.findViewById(R.id.homeGalleryLatestReleases);
        galleryIncoming = view.findViewById(R.id.homeGalleryIncoming);

        if (!isNetworkAvailable()){
            Toast.makeText(requireContext(), R.string.no_connection_message, Toast.LENGTH_LONG).show();
        }

        setShowAll();

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());
        String lastUpdate = "0";
        if (sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE_HOME) != null) {
            lastUpdate = sharedPreferencesUtil.readStringData(
                    SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE_HOME);
        }
        observeViewModel(lastUpdate);
    }

    private void setShowAll() {
        MaterialTextView showAllPopular = requireView().findViewById(R.id.showAllPopular);
        showAllPopular.setOnClickListener(v -> {
            Intent myIntent = new Intent(requireContext(), AllGamesActivity.class);
            myIntent.putExtra("section", "POPULAR");
            startActivity(myIntent);
        });
        MaterialTextView showAllBest = requireView().findViewById(R.id.showAllBest);
        showAllBest.setOnClickListener(v -> {
            Intent myIntent = new Intent(requireContext(), AllGamesActivity.class);
            myIntent.putExtra("section", "BEST");
            startActivity(myIntent);
        });
        MaterialTextView showAllLatest = requireView().findViewById(R.id.showAllLatest);
        showAllLatest.setOnClickListener(v -> {
            Intent myIntent = new Intent(requireContext(), AllGamesActivity.class);
            myIntent.putExtra("section", "LATEST");
            startActivity(myIntent);
        });
        MaterialTextView showAllIncoming = requireView().findViewById(R.id.showAllIncoming);
        showAllIncoming.setOnClickListener(v -> {
            Intent myIntent = new Intent(requireContext(), AllGamesActivity.class);
            myIntent.putExtra("section", "INCOMING");
            startActivity(myIntent);
        });
    }

    private void observeViewModel(String lastUpdate) {
        gamesViewModel.getPopularGames(Long.parseLong(lastUpdate), isNetworkAvailable()).observe(getViewLifecycleOwner(), result -> showGames(0, result));
        gamesViewModel.getBestGames(Long.parseLong(lastUpdate), isNetworkAvailable()).observe(getViewLifecycleOwner(), result -> showGames(1, result));
        gamesViewModel.getLatestGames(Long.parseLong(lastUpdate), isNetworkAvailable()).observe(getViewLifecycleOwner(), result -> showGames(2, result));
        gamesViewModel.getIncomingGames(Long.parseLong(lastUpdate), isNetworkAvailable()).observe(getViewLifecycleOwner(), result -> showGames(3, result));
    }

    public void showGames(int countQuery, List<GameApiResponse> gameList) {
        switch (countQuery) {
            case 0:
                showPopular(gameList);
                break;
            case 1:
                showBest(gameList);
                break;
            case 2:
                showLatest(gameList);
                break;
            case 3:
                showIncoming(gameList);
                break;
            case 4:
                break;
        }
        //LinearLayout layout = requireView().findViewById(R.id.icon);
        //layout.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
    }

    private void showPopular(List<GameApiResponse> gameList) {
        showInRecyclerView(gameList, galleryPopular);
    }

    private void showInRecyclerView(List<GameApiResponse> gameList, RecyclerView recyclerView) {
        List<GameApiResponse> newList = new ArrayList<>();
        for (GameApiResponse gameApiResponse : gameList) {
            if (gameApiResponse.getCover() != null) {
                newList.add(gameApiResponse);
            }
        }
        HomeAdapter homeAdapter = new HomeAdapter(newList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(homeAdapter);
    }

    private void showBest(List<GameApiResponse> gameList) {
        showInRecyclerView(gameList, galleryBestGames);
    }

    private void showIncoming(List<GameApiResponse> gameList) {
        showInRecyclerView(gameList, galleryIncoming);
    }

    private void showLatest(List<GameApiResponse> gameList) {
        showInRecyclerView(gameList, galleryLatestReleases);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
package com.example.kursovaya_igdb.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovaya_igdb.R;
import com.example.kursovaya_igdb.adapter.RecyclerData;
import com.example.kursovaya_igdb.adapter.RecyclerProfileViewAdapter;
import com.example.kursovaya_igdb.model.GameApiResponse;
import com.example.kursovaya_igdb.repository.IGamesRepository;
import com.example.kursovaya_igdb.ui.viewModel.GamesViewModel;
import com.example.kursovaya_igdb.ui.viewModel.GamesViewModelFactory;
import com.example.kursovaya_igdb.util.ServiceLocator;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class PlayingFragment extends Fragment {
    private ProgressBar progressBar;
    private GamesViewModel gamesViewModel;
    RecyclerProfileViewAdapter homeAdapter;
    private ArrayList<RecyclerData> recyclerDataArrayList;
    private RecyclerView recyclerView;
    private TextView noGameTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = requireView().findViewById(R.id.playingRecyclerView);
        recyclerDataArrayList = new ArrayList<>();
        homeAdapter = new RecyclerProfileViewAdapter(recyclerDataArrayList, getContext());
        recyclerView.setAdapter(homeAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = requireView().findViewById(R.id.progressBar);
        noGameTextView = requireView().findViewById(R.id.noGameText);
        noGameTextView.setVisibility(View.GONE);
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

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void observeViewModel() {
        progressBar.setVisibility(View.VISIBLE);

        gamesViewModel.getPlayingGames(checkNetwork(requireContext())).observe(getViewLifecycleOwner(), gameApiResponses -> {
            if (gameApiResponses.size() == 0) {
                noGameTextView.setVisibility(View.VISIBLE);
            } else {
                noGameTextView.setVisibility(View.GONE);
            }
            recyclerDataArrayList.clear();
            for (GameApiResponse gameApiResponse : gameApiResponses) {
                recyclerDataArrayList.add(new RecyclerData(gameApiResponse.getId(), gameApiResponse.getCover().getUrl()));
            }
            homeAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        });
    }
    private boolean checkNetwork(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    @Override
    public void onResume() {
        super.onResume();
        observeViewModel();
    }
}
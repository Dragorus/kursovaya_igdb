package com.example.kursovaya_igdb.ui.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kursovaya_igdb.repository.IGamesRepository;

public class GamesViewModelFactory implements ViewModelProvider.Factory {
    private final IGamesRepository iGamesRepository;

    public GamesViewModelFactory(IGamesRepository iGamesRepository) {
        this.iGamesRepository = iGamesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GamesViewModel(iGamesRepository);
    }

}
package com.example.kursovaya_igdb.repository;

import static com.example.kursovaya_igdb.util.Constants.FRESH_TIMEOUT;

import android.os.Build;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.kursovaya_igdb.model.GameApiResponse;
import com.example.kursovaya_igdb.source.BaseGamesDataSource;
import com.example.kursovaya_igdb.source.GameCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GamesRepository implements IGamesRepository, GameCallback {
    private final BaseGamesDataSource gamesDataSource;
    private final MutableLiveData<List<GameApiResponse>> allGames = new MutableLiveData<>();
    private final MutableLiveData<List<GameApiResponse>> popularGames = new MutableLiveData<>();
    private final MutableLiveData<List<GameApiResponse>> bestGames = new MutableLiveData<>();
    private final MutableLiveData<List<GameApiResponse>> incomingGames = new MutableLiveData<>();
    private final MutableLiveData<List<GameApiResponse>> latestGames = new MutableLiveData<>();
    private final MutableLiveData<GameApiResponse> game = new MutableLiveData<>();
    private final MutableLiveData<List<GameApiResponse>> exploreGames;
    private final MutableLiveData<List<GameApiResponse>> companyGames = new MutableLiveData<>();
    private final MutableLiveData<List<GameApiResponse>> franchiseGames = new MutableLiveData<>();
    private final MutableLiveData<List<GameApiResponse>> genreGamesMutableLiveData;
    private final MutableLiveData<List<GameApiResponse>> searchedGamesMutableLiveData;
    private final MutableLiveData<List<GameApiResponse>> filteredGamesMutableLiveData;
    private final MutableLiveData<List<GameApiResponse>> similarGamesMutableLiveData;
    private final MutableLiveData<List<GameApiResponse>> allPopularGames;
    private final MutableLiveData<List<GameApiResponse>> allBestGames;
    private final MutableLiveData<List<GameApiResponse>> allLatestGames;
    private final MutableLiveData<List<GameApiResponse>> allIncomingGames;

    public GamesRepository(BaseGamesDataSource gamesDataSource) {
        this.gamesDataSource = gamesDataSource;
        this.genreGamesMutableLiveData = new MutableLiveData<>();
        this.searchedGamesMutableLiveData = new MutableLiveData<>();
        this.filteredGamesMutableLiveData = new MutableLiveData<>();
        this.similarGamesMutableLiveData = new MutableLiveData<>();
        this.exploreGames = new MutableLiveData<>();
        this.allPopularGames = new MutableLiveData<>();
        this.allBestGames = new MutableLiveData<>();
        this.allLatestGames = new MutableLiveData<>();
        this.allIncomingGames = new MutableLiveData<>();
        this.gamesDataSource.setGameCallback(this);
    }

    @Override
    public MutableLiveData<List<GameApiResponse>> fetchPopularGames(long lastUpdate, boolean networkAvailable) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdate > FRESH_TIMEOUT && networkAvailable) {
            gamesDataSource.getPopularGames();
        }
        return popularGames;
    }

    @Override
    public MutableLiveData<List<GameApiResponse>> fetchBestGames(long lastUpdate, boolean networkAvailable) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdate > FRESH_TIMEOUT && networkAvailable) {
            gamesDataSource.getBestGames();
        }
        return bestGames;
    }

    @Override
    public MutableLiveData<List<GameApiResponse>> fetchLatestGames(long lastUpdate, boolean networkAvailable) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdate > FRESH_TIMEOUT && networkAvailable) {
            gamesDataSource.getLatestGames();
        }
        return latestGames;
    }

    @Override
    public MutableLiveData<List<GameApiResponse>> fetchIncomingGames(long lastUpdate, boolean networkAvailable) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdate > FRESH_TIMEOUT && networkAvailable) {
            gamesDataSource.getIncomingGames();
        }
        return incomingGames;
    }


    @Override
    public MutableLiveData<List<GameApiResponse>> fetchExploreGames(boolean networkAvailable) {
        if (networkAvailable) {
            gamesDataSource.getExploreGames();
        }
        return exploreGames;
    }

    @Override
    public MutableLiveData<List<GameApiResponse>> fetchCompanyGames(String company) {
        gamesDataSource.getCompanyGames(company);
        return companyGames;
    }

    @Override
    public MutableLiveData<List<GameApiResponse>> fetchFranchiseGames(String franchise) {
        gamesDataSource.getFranchiseGames(franchise);
        return franchiseGames;
    }

    @Override
    public MutableLiveData<List<GameApiResponse>> fetchGenreGames(String genre) {
        gamesDataSource.getGenreGames(genre);
        return genreGamesMutableLiveData;
    }


    @Override
    public MutableLiveData<List<GameApiResponse>> getSearchedGames(String userInput) {
        if (userInput != null)
            gamesDataSource.getSearchedGames(userInput);
        return searchedGamesMutableLiveData;
    }

    @Override
    public MutableLiveData<List<GameApiResponse>> getSimilarGames(List<Integer> similarGames) {
        gamesDataSource.getSimilarGames(similarGames);
        return similarGamesMutableLiveData;
    }

    @Override
    public MutableLiveData<List<GameApiResponse>> getAllPopularGames() {
        gamesDataSource.getAllPopularGames();
        return allPopularGames;
    }

    @Override
    public MutableLiveData<List<GameApiResponse>> getAllBestGames() {
        gamesDataSource.getAllBestGames();
        return allBestGames;
    }

    @Override
    public MutableLiveData<List<GameApiResponse>> getAllLatestGames() {
        gamesDataSource.getAllLatestGames();
        return allLatestGames;
    }

    @Override
    public MutableLiveData<List<GameApiResponse>> getAllIncomingGames() {
        gamesDataSource.getAllIncomingGames();
        return allIncomingGames;
    }

    @Override
    public MutableLiveData<List<GameApiResponse>> getFilteredGames(String genre, String platform, String year) {
        if (genre != null || platform != null){
            gamesDataSource.getFilteredGames(genre, platform, year);
        }
        return filteredGamesMutableLiveData;
    }

    @Override
    public void onSuccessFromLocal(List<GameApiResponse> gameApiResponses, String i) {
        List<GameApiResponse> gamesList = allGames.getValue();
        if (gamesList != null) {
            gamesList.addAll(gameApiResponses);
        }

        allGames.postValue(gamesList);
        switch (i) {
            case "POPULAR":
                popularGames.postValue(gameApiResponses);
                break;
            case "BEST":
                bestGames.postValue(gameApiResponses);
                break;
            case "INCOMING":
                incomingGames.postValue(gameApiResponses);
                break;
            case "LATEST":
                latestGames.postValue(gameApiResponses);
                break;
            case "SINGLE":
                game.postValue(gameApiResponses.get(0));
                break;
            case "EXPLORE":
                exploreGames.postValue(gameApiResponses);
                break;
            case "SEARCHED":
                searchedGamesMutableLiveData.postValue(gameApiResponses);
                break;
            case "FILTERED" :
                filteredGamesMutableLiveData.postValue(gameApiResponses);
                break;
            case "FRANCHISE":
                franchiseGames.postValue(gameApiResponses);
                break;
            case "COMPANY":
                companyGames.postValue(gameApiResponses);
                break;
            case "GENRE":
                genreGamesMutableLiveData.postValue(gameApiResponses);
                break;
            case "SIMILAR":
                similarGamesMutableLiveData.postValue(gameApiResponses);
                break;
            case "ALLPOPULAR":
                allPopularGames.postValue(gameApiResponses);
                break;
            case "ALLBEST":
                allBestGames.postValue(gameApiResponses);
                break;
            case "ALLLATEST":
                allLatestGames.postValue(gameApiResponses);
                break;
            case "ALLINCOMING":
                allIncomingGames.postValue(gameApiResponses);
                break;
        }
    }

    @Override
    public void onSuccessDeletion() {
        Log.i("tutto", "eliminato");
    }

    @Override
    public void onSuccessFromCloudReading(List<GameApiResponse> games, String wanted) {
        if (games != null) {
            for (GameApiResponse gameApiResponse : games) {
                gameApiResponse.setSynchronized(true);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(games, Comparator.comparing(GameApiResponse::getAdded));
            }
        }
    }

    @Override
    public void onFailureFromCloud(Exception e) {
        Log.e(getClass().getSimpleName(), "onFailureFromCloud");
    }

    @Override
    public void onSuccessSynchronization() {
        Log.d("GamesRepository", "Games synchronized from remote");
    }
}

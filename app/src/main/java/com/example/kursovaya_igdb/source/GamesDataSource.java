package com.example.kursovaya_igdb.source;

import static com.example.kursovaya_igdb.util.Constants.CLIENT_ID_VALUE;
import static com.example.kursovaya_igdb.util.Constants.CONTENT_TYPE_VALUE;
import static com.example.kursovaya_igdb.util.Constants.TOKEN_API_VALUE;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.kursovaya_igdb.model.GameApiResponse;
import com.example.kursovaya_igdb.service.GamesApiService;
import com.example.kursovaya_igdb.util.ServiceLocator;

import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesDataSource extends BaseGamesDataSource {
    private final GamesApiService gamesApiService;
    private final String fields;

    public GamesDataSource() {
        this.gamesApiService = ServiceLocator.getInstance().getGamesApiService();
        fields = "fields name, videos.*, franchises.name, similar_games, first_release_date, release_dates.y, genres.name, total_rating," +
                " total_rating_count, cover.url, involved_companies.company.name, involved_companies.company.description, platforms.name, summary, screenshots.url, follows; ";
    }

    public void getGames(String query, String i) {
        RequestBody body = RequestBody.create(MediaType.parse("text-plain"), query);
        Call<List<GameApiResponse>> gameApiResponseCall = gamesApiService.getGames(
                CONTENT_TYPE_VALUE,
                CLIENT_ID_VALUE,
                TOKEN_API_VALUE,
                body);

        gameApiResponseCall.enqueue(new Callback<List<GameApiResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<GameApiResponse>> call, @NonNull Response<List<GameApiResponse>> response) {
                if (response.body() != null && response.isSuccessful()) {
                    List<GameApiResponse> gameApiResponses = response.body();
                    gameCallback.onSuccessFromRemote(gameApiResponses, i);
                } else {
                    Log.e(getClass().getName(), "API expired");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GameApiResponse>> call, @NonNull Throwable t) {
                Log.e(getClass().getSimpleName(), t.getMessage());
            }
        });
    }

    @Override
    public void getGames(String query) {
        getGames(query, "SINGLE");
    }

    @Override
    public void getGame(int id) {
        String query = fields + "where id = " + id + ";";
        getGames(query);
    }

    @Override
    public void getPopularGames() {
        String query = fields + "where follows!=null; sort follows desc; limit 20;";
        getGames(query, "POPULAR");
    }
    @Override
    public void getBestGames() {
        String query = fields + "where total_rating_count>1000;sort total_rating desc; limit 20;";
        getGames(query,"BEST");
    }
    @Override
    public void getLatestGames() {
        String query = fields + "where first_release_date <= " + currentDate() + ";sort first_release_date desc; limit 20;";
        getGames(query,"LATEST");
    }
    @Override
    public void getIncomingGames() {
        String query = fields + "where first_release_date > " + currentDate() + ";sort first_release_date asc; limit 20;";
        getGames(query, "INCOMING");
    }

    @Override
    public void getExploreGames() {
        String query = fields + "where first_release_date <= " + currentDate() + " & cover.url != null; limit 200;";
        getGames(query, "EXPLORE");
    }

    @Override
    public void getCompanyGames(String company) {
        String query = fields + "where involved_companies.company.name = \"" + company + "\";  limit 30;";
        getGames(query, "COMPANY");
    }

    @Override
    public void getFranchiseGames(String franchise) {
        String query = fields + " where franchises.name = \"" + franchise + "\"; limit 30;";
        getGames(query, "FRANCHISE");
    }

    @Override
    public void getGenreGames(String genre) {
        String query = fields + "where genres.name = \"" + genre + "\" & total_rating > 85 & first_release_date > 1262304000; limit 30;";
        getGames(query, "GENRE");
    }

    @Override
    public void getSearchedGames(String userInput) {
        String query = fields + "where first_release_date < " + System.currentTimeMillis() / 1000 + " & version_parent = null;search \"" + userInput + "\"; limit 60;";
        getGames(query, "SEARCHED");
    }

    @Override
    public void getFilteredGames(String genre, String platform, String year) {
        String query = fields;
        query += "where total_rating != null & cover.url != null & first_release_date != null";
        if (genre != null || platform != null || year != null) {
            if (!genre.isEmpty()) {
                query += " & genres.name = \"" + genre + "\"";
            }
            if (!platform.isEmpty()){
                query += " & platforms.name = \"" + platform + "\"";
            }
            if (!year.isEmpty()){
                query += " & release_dates.y = " + year;
            }
        }
        query += "; ";
        query += "limit 200;";
        Log.i("query", query);
        getGames(query, "FILTERED");
    }

    @Override
    public void getSimilarGames(List<Integer> similarGames) {
        StringBuilder ids = new StringBuilder();
        if (similarGames != null){
            for (Integer id : similarGames){
                ids.append(id);
                if (similarGames.lastIndexOf(id) != similarGames.size() - 1){
                    ids.append(",");
                }
            }
        }
        String query = fields + "where id = (" + ids + "); limit 15;";
        getGames(query, "SIMILAR");
    }

    @Override
    public void getAllPopularGames() {
        String query = fields + "where follows!=null; sort follows desc; limit 200;";
        getGames(query, "ALLPOPULAR");
    }

    @Override
    public void getAllBestGames() {
        String query = fields + "where total_rating_count>1000;sort total_rating desc; limit 200;";
        getGames(query, "ALLBEST");
    }

    @Override
    public void getAllLatestGames() {
        String query = fields + "where first_release_date <= " + currentDate() + ";sort first_release_date desc; limit 200;";
        getGames(query, "ALLLATEST");
    }

    @Override
    public void getAllIncomingGames() {
        String query = fields + "where first_release_date > " + currentDate() + ";sort first_release_date asc; limit 200;";
        getGames(query, "ALLINCOMING");
    }

    @Override
    public void getForYouGames(List<Integer> gamesId, int limit) {
        StringBuilder ids = new StringBuilder();
        for (Integer id : gamesId){
            ids.append(id);
            if (gamesId.lastIndexOf(id) != gamesId.size() - 1){
                ids.append(",");
            }
        }
        String query = fields + "where id = (" + ids + "); limit " + limit + ";";
        getGames(query, "FORYOU");
    }

    private long currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis() / 1000;
    }
}

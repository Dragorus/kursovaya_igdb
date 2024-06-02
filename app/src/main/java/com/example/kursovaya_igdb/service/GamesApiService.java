package com.example.kursovaya_igdb.service;

import static com.example.kursovaya_igdb.util.Constants.CLIENT_ID;
import static com.example.kursovaya_igdb.util.Constants.CONTENT_TYPE;
import static com.example.kursovaya_igdb.util.Constants.TOKEN_API;
import static com.example.kursovaya_igdb.util.Constants.TOP_HEADLINES_ENDPOINT;

import com.example.kursovaya_igdb.model.GameApiResponse;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GamesApiService {
    @POST(TOP_HEADLINES_ENDPOINT)
    Call<List<GameApiResponse>> getGames(
            @Header(CONTENT_TYPE) String contentType,
            @Header(CLIENT_ID) String clientID,
            @Header(TOKEN_API) String authorization,
            @Body RequestBody query);
}

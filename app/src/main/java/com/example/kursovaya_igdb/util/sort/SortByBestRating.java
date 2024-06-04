package com.example.kursovaya_igdb.util.sort;

import com.example.kursovaya_igdb.model.GameApiResponse;

public class SortByBestRating implements java.util.Comparator<GameApiResponse> {
    public int compare(GameApiResponse a, GameApiResponse b) {
        return -Double.compare(a.getTotalRating(), b.getTotalRating());
    }
}

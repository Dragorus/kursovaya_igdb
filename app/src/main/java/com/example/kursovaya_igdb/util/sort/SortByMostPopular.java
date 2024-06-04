package com.example.kursovaya_igdb.util.sort;

import com.example.kursovaya_igdb.model.GameApiResponse;

public class SortByMostPopular implements java.util.Comparator<GameApiResponse> {
    public int compare(GameApiResponse a, GameApiResponse b) {
        return -Integer.compare(a.getFollows(), b.getFollows());
    }
}

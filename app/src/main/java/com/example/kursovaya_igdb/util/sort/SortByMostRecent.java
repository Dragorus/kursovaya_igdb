package com.example.kursovaya_igdb.util.sort;

import com.example.kursovaya_igdb.model.GameApiResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SortByMostRecent implements java.util.Comparator<GameApiResponse> {
    public int compare(GameApiResponse a, GameApiResponse b) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = formatter.parse(a.getFirstReleaseDateString());
            Date date2 = formatter.parse(b.getFirstReleaseDateString());
            assert date1 != null;
            return -date1.compareTo(date2);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return -100;
    }
}

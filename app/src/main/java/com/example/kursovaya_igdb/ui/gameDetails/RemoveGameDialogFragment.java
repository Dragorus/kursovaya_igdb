package com.example.kursovaya_igdb.ui.gameDetails;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kursovaya_igdb.R;
import com.example.kursovaya_igdb.model.GameApiResponse;
import com.example.kursovaya_igdb.ui.viewModel.GamesViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;
import java.util.Date;

public class RemoveGameDialogFragment extends DialogFragment{
    final GameActivity activity;
    Bundle bundle;
    final GamesViewModel gamesViewModel;
    final GameApiResponse game;

    public RemoveGameDialogFragment(GameActivity activity, GamesViewModel gamesViewModel, GameApiResponse game) {
        this.activity = activity;
        this.gamesViewModel = gamesViewModel;
        this.game = game;
    }
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        GameActivity gameActivity = activity;
        MaterialAlertDialogBuilder alertDialogBuilder=  new MaterialAlertDialogBuilder(requireContext());
        alertDialogBuilder.setTitle(game.getName());
        alertDialogBuilder.setMessage(R.string.RemoveGame);
        alertDialogBuilder.setNegativeButton(R.string.cancel_text, (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.setPositiveButton(R.string.confirm_text, (dialog, which) -> {
            if (game.isPlayed()) {
                game.setPlayed(false);
                gamesViewModel.updatePlayedGame(game);
            } else if (game.isPlaying()) {
                game.setPlaying(false);
                gamesViewModel.updatePlayingGame(game);
            }
            gameActivity.getPlayedButton().setText(R.string.played);
            gameActivity.getWantedButton().setText(R.string.wanted);
            gameActivity.getWantedButton().setVisibility(View.VISIBLE);
            setPlayedButton(gameActivity);
            dialog.dismiss();
        });
        return alertDialogBuilder.create();
    }

    private void setPlayedButton(GameActivity gameActivity) {
        int dateInt = game.getFirstReleaseDate();
        Date date = new Date((long)dateInt*1000);
        Calendar today = Calendar.getInstance();
        if (date.compareTo(today.getTime()) <= 0) {
            gameActivity.getPlayedButton().setVisibility(View.VISIBLE);
        }
    }
}

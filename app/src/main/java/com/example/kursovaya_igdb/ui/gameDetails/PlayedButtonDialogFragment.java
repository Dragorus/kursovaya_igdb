package com.example.kursovaya_igdb.ui.gameDetails;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kursovaya_igdb.R;
import com.example.kursovaya_igdb.model.GameApiResponse;
import com.example.kursovaya_igdb.ui.viewModel.GamesViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;

public class PlayedButtonDialogFragment extends DialogFragment {
    private boolean isPlayed = true;
    private boolean isPlaying;
    final GamesViewModel gamesViewModel;
    final GameApiResponse game;

    public PlayedButtonDialogFragment(GamesViewModel gamesViewModel, GameApiResponse game) {
        this.gamesViewModel = gamesViewModel;
        this.game = game;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] options = {getString(R.string.played), getString(R.string.playing)};
        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(requireContext());
        alertDialog.setTitle(game.getName());
        alertDialog.setSingleChoiceItems(options, 0, (dialog, which) -> {
            switch (which) {
                case 0:
                    isPlaying = false;
                    isPlayed = true;
                    break;
                case 1:
                    isPlaying = true;
                    isPlayed = false;
                    break;
            }
        });
        alertDialog.setPositiveButton(getString(R.string.confirm_text), (dialog, which) -> {
            Calendar today = Calendar.getInstance();
            if (isPlaying) {
                game.setPlaying(!game.isPlaying());
                game.setAdded(today.getTimeInMillis());
                gamesViewModel.updatePlayingGame(game);
            }
            if (isPlayed) {
                game.setPlayed(!game.isPlayed());
                game.setAdded(today.getTimeInMillis());
                gamesViewModel.updatePlayedGame(game);
            }
            dismiss();
        });
        alertDialog.setNegativeButton(getString(R.string.cancel_text), (dialog, which) -> dismiss());
        return alertDialog.create();
    }
}

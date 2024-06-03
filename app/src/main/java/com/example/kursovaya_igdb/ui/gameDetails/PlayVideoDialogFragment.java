package com.example.kursovaya_igdb.ui.gameDetails;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kursovaya_igdb.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Map;


public class PlayVideoDialogFragment extends DialogFragment{
    private final Map<String, String> videoId;
    String id;
    public PlayVideoDialogFragment(Map<String, String> videoId) {
        this.videoId = videoId;
        id = videoId.get("Trailer");
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] options = {"Trailer", "Gameplay video"};
        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(requireContext());
        alertDialog.setTitle(getString(R.string.video_choose));
        alertDialog.setSingleChoiceItems(options, 0, (dialog, which) -> {

            switch (which) {
                case 0:
                    id = videoId.get("Trailer");
                    break;
                case 1:
                    id = videoId.get("Gameplay video");
                    break;
            }

        });
        alertDialog.setPositiveButton(getString(R.string.confirm_text), (dialog, which) -> {
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            try {
                startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                startActivity(webIntent);
            }
            dismiss();
        });
        alertDialog.setNegativeButton(getString(R.string.cancel_text), (dialog, which) -> dismiss());
        return alertDialog.create();
    }

}

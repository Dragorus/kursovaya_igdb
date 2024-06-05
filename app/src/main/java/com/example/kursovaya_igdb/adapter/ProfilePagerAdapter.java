package com.example.kursovaya_igdb.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.kursovaya_igdb.ui.profile.WantedFragment;
import com.example.kursovaya_igdb.ui.profile.PlayedFragment;
import com.example.kursovaya_igdb.ui.profile.PlayingFragment;

public class ProfilePagerAdapter extends FragmentStateAdapter {
    public ProfilePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new PlayingFragment();
            case 2:
                return new PlayedFragment();
            default:
                return new WantedFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

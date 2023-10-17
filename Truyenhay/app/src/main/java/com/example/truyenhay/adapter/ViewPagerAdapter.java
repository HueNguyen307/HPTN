package com.example.truyenhay.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.truyenhay.fragment.FragmentGenre;
import com.example.truyenhay.fragment.FragmentHome;
import com.example.truyenhay.fragment.FragmentUser;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentHome();
            case 1: return new FragmentGenre();
            case 2: return new FragmentUser();

            default:
                return new FragmentHome();
        }

    }
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0: return "Trang chu";
            case 1: return "The loai";
            case 2: return "Ca nhan";

        }
        return null;
    }
    @Override
    public int getCount() {
        return 3;
    }
}

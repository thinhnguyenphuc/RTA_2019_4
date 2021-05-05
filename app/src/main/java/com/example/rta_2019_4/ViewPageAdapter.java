package com.example.rta_2019_4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewPageAdapter extends FragmentStatePagerAdapter {

    ListFragment listFragment = new ListFragment();
    MapFragment mapFragment = new MapFragment();
    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{ return listFragment; }
            case 1:{ return new MapFragment(); }
            default: return listFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:{return "List";}
            case 1:{return "Map";}
            default: return "List";
        }
    }
}

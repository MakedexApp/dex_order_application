package com.example.dexorderapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dexorderapplication.BeverageFragment;
import com.example.dexorderapplication.Instant_noodlesFragment;
import com.example.dexorderapplication.SnackFragment;
import com.example.dexorderapplication.BeverageFragment;
import com.example.dexorderapplication.Instant_noodlesFragment;
import com.example.dexorderapplication.SnackFragment;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {

    private String[] titles = new String[]{"Beverage","Snack","Instant_noodles"};

    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new BeverageFragment();
            case 1:
                return new SnackFragment();
            case 2:
                return new Instant_noodlesFragment();
        }
        return new BeverageFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}

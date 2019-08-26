package com.example.khana_online.fyp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class pageradapter extends FragmentStatePagerAdapter {
    int noOftabs;
    public pageradapter(FragmentManager fm,int noOftabs) {
        super(fm);
        this.noOftabs=noOftabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                Profile_Fragment tab1 = new Profile_Fragment();
                return tab1;
            case 1:
                Dishes_Fragment tab2 = new Dishes_Fragment();
                return tab2;
            case 2:
            Order_Fragment tab3 = new Order_Fragment();
            return tab3;
            default:
                return null;


        }
    }

    @Override
    public int getCount() {
        return noOftabs;
    }
}


package com.ravijeet.teleportal.homeScreen;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.TelePortalApplication;
import com.ravijeet.teleportal.categoryScreen.CategoryFragment;

/**
 * Created by Ravijeet on 2/27/18.
 */

class HomeActivityViewPagerAdapter extends FragmentPagerAdapter {

    private static final String AIRING_TODAY = "airing_today";
    private static final String ON_THE_AIR = "on_the_air";
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";


    public HomeActivityViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        String categoryType = "";

        switch (position){
            case 0:
                categoryType = AIRING_TODAY;
                break;
            case 1:
                categoryType = ON_THE_AIR;
                break;
            case 2:
                categoryType = POPULAR;
                break;
            case 3:
                categoryType = TOP_RATED;
                break;
        }

        return CategoryFragment.newInstance(categoryType);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return TelePortalApplication.getContext().getString(R.string.airing_today);
            case 1:
                return TelePortalApplication.getContext().getString(R.string.on_the_air);
            case 2:
                return TelePortalApplication.getContext().getString(R.string.popular);
            case 3:
                return TelePortalApplication.getContext().getString(R.string.top_rated);
            default:
                return "";
        }


    }
}

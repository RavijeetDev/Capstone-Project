package com.ravijeet.teleportal.homeScreen;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.favoriteScreen.FavoriteActivity;

/**
 * Created by Ravijeet on 2/27/18.
 */

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.categoriesViewPager);

        setSupportActionBar(toolbar);

        MobileAds.initialize(this, getString(R.string.banner_app_id));
        AdView adView = findViewById(R.id.adView);
        initBannerAd(adView);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.airing_today));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.on_the_air));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.popular));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.top_rated));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        HomeActivityViewPagerAdapter viewPagerAdapter = new HomeActivityViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseAnalytics.setCurrentScreen(this, getString(R.string.first_screen), getString(R.string.first_screen));

    }

    private void initBannerAd(AdView adView) {

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("21879CF6EB5FDE22962CD7C74C517CD2")
                .build();
        adView.loadAd(adRequest);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.favorite:
                Intent intent = new Intent(this, FavoriteActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

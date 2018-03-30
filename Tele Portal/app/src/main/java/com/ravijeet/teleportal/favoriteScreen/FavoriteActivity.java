package com.ravijeet.teleportal.favoriteScreen;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ravijeet.teleportal.R;


/**
 * Created by Ravijeet on 3/10/18.
 */

public class FavoriteActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.favorite_title));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(savedInstanceState == null) {
            FavoriteTvShowFragment favoriteTvShowFragment = new FavoriteTvShowFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, favoriteTvShowFragment).commitAllowingStateLoss();
        }
    }

}

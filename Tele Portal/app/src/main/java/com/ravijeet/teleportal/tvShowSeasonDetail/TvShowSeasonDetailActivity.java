package com.ravijeet.teleportal.tvShowSeasonDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;


import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.util.Constant;
import com.squareup.picasso.Picasso;

import static com.ravijeet.teleportal.util.Constant.SEASON_NUMBER_EXTRA;
import static com.ravijeet.teleportal.util.Constant.TVSHOW_BACKROUND_EXTRA;
import static com.ravijeet.teleportal.util.Constant.TVSHOW_ID_EXTRA;


/**
 * Created by Ravijeet on 3/6/18.
 */

public class TvShowSeasonDetailActivity extends AppCompatActivity {


    private TvShowSeasonDetailContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageView backdropImageView = findViewById(R.id.backdrop);
        int seasonNumber = getIntent().getIntExtra(SEASON_NUMBER_EXTRA, 0);
        int tvShowId = getIntent().getIntExtra(TVSHOW_ID_EXTRA, 0);

        toolbar.setTitle(String.format(getString(R.string.season), seasonNumber+""));

        Picasso.with(this)
                .load(Constant.BANNER_IMAGE_URL + Constant.BACKGROUND_IMAGE_SIZE
                        + getIntent().getStringExtra(TVSHOW_BACKROUND_EXTRA))
                .placeholder(R.drawable.banner_placeholder)
                .into(backdropImageView);

        if(savedInstanceState == null){
            presenter = new TvShowSeasonDetailPresenter();
            TvShowSeasonDetailFragment tvShowSeasonDetailFragment = new TvShowSeasonDetailFragment();
            tvShowSeasonDetailFragment.setPresenter(presenter);
            presenter.setView(tvShowSeasonDetailFragment);
            presenter.onAttachView();
            Bundle bundle = new Bundle();
            bundle.putInt(TVSHOW_ID_EXTRA, tvShowId);
            bundle.putInt(SEASON_NUMBER_EXTRA, seasonNumber);
            tvShowSeasonDetailFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,tvShowSeasonDetailFragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDetachView();
        super.onDestroy();
    }
}

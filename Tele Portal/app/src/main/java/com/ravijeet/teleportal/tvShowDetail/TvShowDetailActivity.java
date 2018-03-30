package com.ravijeet.teleportal.tvShowDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.bean.TvShow;

import static com.ravijeet.teleportal.util.Constant.TV_SHOW_EXTRA;

/**
 * Created by Ravijeet on 3/2/18.
 */

public class TvShowDetailActivity extends AppCompatActivity {

    private TvShowDetailContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null) {

            presenter = new TvShowDetailPresenter();
            TvShowDetailFragment tvShowDetailFragment = new TvShowDetailFragment();
            tvShowDetailFragment.setPresenter(presenter);
            presenter.setView(tvShowDetailFragment);
            presenter.onAttachView();

//            Toolbar toolbar = findViewById(R.id.toolbar);
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    finish();
//                }
//            });

            TvShow tvShow = getIntent().getParcelableExtra(TV_SHOW_EXTRA);
            Bundle bundle = new Bundle();
            bundle.putParcelable(TV_SHOW_EXTRA, tvShow);
            tvShowDetailFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, tvShowDetailFragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDetachView();
        super.onDestroy();
    }
}

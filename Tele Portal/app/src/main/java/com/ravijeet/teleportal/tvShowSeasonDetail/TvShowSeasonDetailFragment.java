package com.ravijeet.teleportal.tvShowSeasonDetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.bean.Episode;

import java.util.List;

import static com.ravijeet.teleportal.util.Constant.SEASON_NUMBER_EXTRA;
import static com.ravijeet.teleportal.util.Constant.TVSHOW_ID_EXTRA;

/**
 * Created by Ravijeet on 3/6/18.
 */

public class TvShowSeasonDetailFragment extends Fragment implements TvShowSeasonDetailContract.View{


    private RecyclerView episodeListView;
    private ProgressBar progressBar;
    private TextView noInternetText;
    private TvShowSeasonDetailPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_season_detail, container, false);

        Bundle bundle = getArguments();

        episodeListView = rootView.findViewById(R.id.seasonListView);
        progressBar = rootView.findViewById(R.id.progress);
        noInternetText = rootView.findViewById(R.id.error_message);


        presenter.setTvShowProperty(bundle.getInt(TVSHOW_ID_EXTRA), bundle.getInt(SEASON_NUMBER_EXTRA));
        presenter.start();

        return rootView;
    }


    @Override
    public void setPresenter(TvShowSeasonDetailContract.Presenter presenter) {
        this.presenter = (TvShowSeasonDetailPresenter) presenter;
    }

    @Override
    public void onSeasonDetailLoaded(List<Episode> episodeList) {

        TvShowSeasonDetailAdapter tvShowSeasonDetailAdapter = new TvShowSeasonDetailAdapter(episodeList);
        episodeListView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        episodeListView.setAdapter(tvShowSeasonDetailAdapter);
    }

    @Override
    public void onNoInternetConnection() {
        noInternetText.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayingLoadingProgressBar(boolean isVisible) {
        if(isVisible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }
}

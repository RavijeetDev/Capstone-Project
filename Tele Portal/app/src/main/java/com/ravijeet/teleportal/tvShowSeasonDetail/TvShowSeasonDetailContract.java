package com.ravijeet.teleportal.tvShowSeasonDetail;



import com.ravijeet.teleportal.bean.Episode;

import java.util.List;

/**
 * Created by Ravijeet on 3/6/18.
 */

public interface TvShowSeasonDetailContract {

    interface View  {

        void setPresenter(Presenter presenter);

        void onSeasonDetailLoaded(List<Episode> episodeList);

        void onNoInternetConnection();

        void displayingLoadingProgressBar(boolean isVisible);

    }

    interface Presenter {

        void start();

        void onAttachView();

        void onDetachView();

        void setView(View view);

        void setTvShowProperty(int tvShowId, int seasonNumeber);

        void loadSeasonDetailList(int tvShowId, int tvShow);

        void onSeasonDetailList(List<Episode> episodeList);

        void onNoInternetConnection();

        void onItemsLoadingError(Throwable t);

    }
}

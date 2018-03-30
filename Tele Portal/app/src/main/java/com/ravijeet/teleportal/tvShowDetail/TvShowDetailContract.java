package com.ravijeet.teleportal.tvShowDetail;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.ravijeet.teleportal.bean.Cast;
import com.ravijeet.teleportal.bean.Image;
import com.ravijeet.teleportal.bean.Season;
import com.ravijeet.teleportal.bean.Trailer;
import com.ravijeet.teleportal.bean.TvShow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravijeet on 3/2/18.
 */

public interface TvShowDetailContract {

    interface View {

        void setPresenter(Presenter presenter);

        void onTrailerLoaded(Trailer trailer);

        void showBasicDetail(TvShow tvShow);

        void onImagesLoaded(List<Image> imageList);

        void onCastLoaded(List<Cast> castList);

        void onSeasonsLoaded(List<Season> seasonList);

        void onNoInternetConnection();

        void onItemsLoadingError();

        void showFavoriteIcon(boolean visible);

        void displayLoadingProgressBar(boolean isVisible);

    }


    interface Presenter  {

        void start();

        void onAttachView();

        void onDetachView();

        void setView(View view);

        void setTVShowId(int tvShowId);

        void loadTvShowTrailer();

        void onTrailerLoaded(List<Trailer> trailerList);

        void loadImages();

        void onImageListLoaded(List<Image> imageList);

        void loadCastList();

        void onCastListLoaded(List<Cast> castList);

        void loadSeasonList();

        void onLoadSeasonList(int numberOfSeasons, List<Season> seasonList);

        void onNoInternetConnection();

        void onItemsLoadingError(Throwable t);

        void onSeasonItemClick(Context context, int seasonNumber, String posterPath);

        void onTrailerClick(Context context, String key);

        void onShareClick(Context context, String key);

        void onImageClicked(AppCompatActivity compatActivity, int position,
                                   ArrayList<Image> imageList, String tvShowName);

        boolean checkIsFavorite(Context context, int id);

        void onFavoriteClick(Context context, TvShow tvShow);
    }
}

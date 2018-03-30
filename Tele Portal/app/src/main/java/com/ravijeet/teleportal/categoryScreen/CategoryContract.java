package com.ravijeet.teleportal.categoryScreen;

import android.content.Context;

import com.ravijeet.teleportal.bean.TvShow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravijeet on 2/29/18.
 */

public class CategoryContract {

    interface View {

        void setPresenter(Presenter presenter);

        Presenter getPresenter();

        void onTvShowListLoaded(ArrayList<TvShow> tvShowArrayList, int totalPages);

        void onNoInternetConnection();

        void displayLoadingProgressBar(boolean isVisible);
    }

    interface Presenter {

        void start();

        void onAttachView();

        void setView(View view);

        void loadTvShowList(String category, int page);

        void onTvShowListLoaded(ArrayList<TvShow> tvShowArrayList, int totalPages);

        void onNoInternetConnection();

        void onItemsLoadingError(Throwable t);

        void onItemClick(Context context, TvShow tvShow);

        void onDetachView();
    }
}

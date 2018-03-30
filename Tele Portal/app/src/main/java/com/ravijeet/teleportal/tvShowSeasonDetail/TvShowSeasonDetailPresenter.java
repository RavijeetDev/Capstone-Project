package com.ravijeet.teleportal.tvShowSeasonDetail;


import com.ravijeet.teleportal.api.NetworkCall;
import com.ravijeet.teleportal.bean.Episode;
import com.ravijeet.teleportal.bean.EpisodeResult;
import com.ravijeet.teleportal.util.Utility;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ravijeet on 3/6/18.
 */

public class TvShowSeasonDetailPresenter implements TvShowSeasonDetailContract.Presenter {

    private TvShowSeasonDetailContract.View view;
    private NetworkCall networkCall;
    private int tvShowId, seasonNumber;

    private CompositeDisposable mCompositeDisposable;

    @Override
    public void start() {
        networkCall = new NetworkCall();

        view.displayingLoadingProgressBar(true);

        if (Utility.isInternetConnectionAvailable()) {
            loadSeasonDetailList(tvShowId, seasonNumber);
        } else {
            onNoInternetConnection();
        }
    }

    @Override
    public void onAttachView() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onDetachView() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }


    @Override
    public void setView(TvShowSeasonDetailContract.View view) {
        this.view = view;

    }

    @Override
    public void setTvShowProperty(int tvShowId, int seasonNumber) {
        this.tvShowId = tvShowId;
        this.seasonNumber = seasonNumber;
    }

    @Override
    public void loadSeasonDetailList(int tvShowId, int tvShow) {

        Observable<EpisodeResult> retrofitObserver;

        retrofitObserver = networkCall.getEpisodeResult(tvShowId, seasonNumber);

        retrofitObserver.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(episodeNetworkApiObserver());
    }

    private Observer<EpisodeResult> episodeNetworkApiObserver() {
        return new Observer<EpisodeResult>() {

            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(EpisodeResult episodeResult) {
                onSeasonDetailList(episodeResult.getEpisodeList());
            }

            @Override
            public void onError(Throwable e) {
                onItemsLoadingError(e);
            }

            @Override
            public void onComplete() {
                mCompositeDisposable.clear();


            }
        };
    }

    @Override
    public void onSeasonDetailList(List<Episode> episodeList) {
        view.displayingLoadingProgressBar(false);
        view.onSeasonDetailLoaded(episodeList);
    }

    @Override
    public void onNoInternetConnection() {
        view.onNoInternetConnection();
        view.displayingLoadingProgressBar(false);
    }

    @Override
    public void onItemsLoadingError(Throwable t) {
        view.onNoInternetConnection();
        view.displayingLoadingProgressBar(false);
    }
}

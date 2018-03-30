package com.ravijeet.teleportal.categoryScreen;

import android.content.Context;
import android.content.Intent;

import com.ravijeet.teleportal.api.NetworkCall;
import com.ravijeet.teleportal.bean.TvShow;
import com.ravijeet.teleportal.bean.TvShowListResponse;
import com.ravijeet.teleportal.tvShowDetail.TvShowDetailActivity;
import com.ravijeet.teleportal.util.Utility;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.ravijeet.teleportal.util.Constant.TV_SHOW_EXTRA;

/**
 * Created by Ravijeet on 2/29/18.
 */

public class CategoryPresenter implements CategoryContract.Presenter {

    private CategoryContract.View view;
    private NetworkCall networkCall;


    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void start() {
        networkCall = new NetworkCall();

    }

    @Override
    public void onAttachView() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void setView(CategoryContract.View view) {
        this.view = view;
        view.displayLoadingProgressBar(true);
    }

    @Override
    public void loadTvShowList(String category, int page) {

        if(Utility.isInternetConnectionAvailable()){
            Observable<TvShowListResponse> retrofitObserver;

            retrofitObserver = networkCall.getTvShowList(category, page);

            retrofitObserver.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(tvShowListNetworkApiObserver());
        }else {
            onNoInternetConnection();
        }
    }

    private Observer<TvShowListResponse> tvShowListNetworkApiObserver() {
        return new Observer<TvShowListResponse>(){

            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(TvShowListResponse tvShowListResponse) {
                onTvShowListLoaded(tvShowListResponse.getTvShowList(), tvShowListResponse.getTotalPages());
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
    public void onTvShowListLoaded(ArrayList<TvShow> tvShowArrayList, int totalPages) {
        view.displayLoadingProgressBar(false);
        view.onTvShowListLoaded(tvShowArrayList, totalPages);
    }

    @Override
    public void onNoInternetConnection() {
        view.onNoInternetConnection();
        view.displayLoadingProgressBar(false);
    }

    @Override
    public void onItemsLoadingError(Throwable t) {
        view.onNoInternetConnection();
        view.displayLoadingProgressBar(false);
    }

    @Override
    public void onItemClick(Context context, TvShow tvShow) {

        Intent intent = new Intent(context, TvShowDetailActivity.class);
        intent.putExtra(TV_SHOW_EXTRA, tvShow);
        context.startActivity(intent);

    }

    @Override
    public void onDetachView() {
        if(mCompositeDisposable != null){
            mCompositeDisposable.dispose();
        }
    }
}

package com.ravijeet.teleportal.tvShowDetail;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.api.NetworkCall;
import com.ravijeet.teleportal.bean.Cast;
import com.ravijeet.teleportal.bean.CastList;
import com.ravijeet.teleportal.bean.Image;
import com.ravijeet.teleportal.bean.ImagesResponse;
import com.ravijeet.teleportal.bean.Season;
import com.ravijeet.teleportal.bean.SeasonsResponse;
import com.ravijeet.teleportal.bean.Trailer;
import com.ravijeet.teleportal.bean.TrailerResult;
import com.ravijeet.teleportal.bean.TvShow;
import com.ravijeet.teleportal.data.TelePortalContract;
import com.ravijeet.teleportal.tvShowSeasonDetail.TvShowSeasonDetailActivity;
import com.ravijeet.teleportal.util.Utility;
import com.ravijeet.teleportal.youtube.YoutubeVideoActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


import static com.ravijeet.teleportal.util.Constant.IMAGES_EXTRA;
import static com.ravijeet.teleportal.util.Constant.MEDIA_URL_EXTRA;
import static com.ravijeet.teleportal.util.Constant.POSITION_EXTRA;
import static com.ravijeet.teleportal.util.Constant.SEASON_NUMBER_EXTRA;
import static com.ravijeet.teleportal.util.Constant.TVSHOW_BACKROUND_EXTRA;
import static com.ravijeet.teleportal.util.Constant.TVSHOW_ID_EXTRA;
import static com.ravijeet.teleportal.util.Constant.TV_SHOW_NAME_EXTRA;
import static com.ravijeet.teleportal.util.Constant.YOUTUBE_SHARE_URL;
import static com.ravijeet.teleportal.util.Constant.YOUTUBE_URL;


/**
 * Created by Ravijeet on 3/2/18.
 */

public class TvShowDetailPresenter implements TvShowDetailContract.Presenter {

    private TvShowDetailContract.View view;
    private NetworkCall networkCall;
    private int tvShowId;

    private CompositeDisposable mCompositeDisposable;

    @Override
    public void start() {
        networkCall = new NetworkCall();
        view.displayLoadingProgressBar(true);

        if (Utility.isInternetConnectionAvailable()) {
            loadTvShowTrailer();
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
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
    }


    @Override
    public void setView(TvShowDetailContract.View view) {
        this.view = view;

    }

    @Override
    public void setTVShowId(int tvShowId) {
        this.tvShowId = tvShowId;
    }



    @Override
    public void loadTvShowTrailer() {
        Observable<TrailerResult> retrofitObserver;

        retrofitObserver = networkCall.getTrailers(tvShowId);

        retrofitObserver.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trailerNetworkApiObserver());
    }

    private Observer<TrailerResult> trailerNetworkApiObserver() {
        return new Observer<TrailerResult>() {

            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(TrailerResult trailerResult) {
                onTrailerLoaded(trailerResult.getTrailerList());
            }

            @Override
            public void onError(Throwable e) {
                onItemsLoadingError(e);
            }

            @Override
            public void onComplete() {
                mCompositeDisposable.clear();
                loadImages();

            }
        };
    }

    @Override
    public void onTrailerLoaded(List<Trailer> trailerList) {
        if (trailerList != null && trailerList.size() > 0) {
            Trailer trailer = trailerList.get(0);
            view.onTrailerLoaded(trailer);
        }
    }

    @Override
    public void loadImages() {
        Observable<ImagesResponse> retrofitObserver;

        retrofitObserver = networkCall.getImageList(tvShowId);

        retrofitObserver.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imagesNetworkApiObserver());
    }


    private Observer<ImagesResponse> imagesNetworkApiObserver() {
        return new Observer<ImagesResponse>() {

            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(ImagesResponse imagesResponse) {
                onImageListLoaded(imagesResponse.getImageArrayList());
            }

            @Override
            public void onError(Throwable e) {
                onItemsLoadingError(e);
            }

            @Override
            public void onComplete() {
                mCompositeDisposable.clear();
                loadCastList();
            }
        };
    }

    @Override
    public void onImageListLoaded(List<Image> imageList) {
        view.onImagesLoaded(imageList);
    }

    @Override
    public void loadCastList() {

        Observable<CastList> retrofitObserver;

        retrofitObserver = networkCall.getCastList(tvShowId);

        retrofitObserver.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(castNetworkApiObserver());
    }

        private Observer<CastList> castNetworkApiObserver() {
        return new Observer<CastList>() {

            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(CastList castList) {
                onCastListLoaded(castList.getCastList());
            }

            @Override
            public void onError(Throwable e) {
                onItemsLoadingError(e);
            }

            @Override
            public void onComplete() {
                mCompositeDisposable.clear();
                loadSeasonList();
            }
        };
    }

    @Override
    public void onCastListLoaded(List<Cast> castList) {
        view.onCastLoaded(castList);
    }


    @Override
    public void loadSeasonList() {
        Observable<SeasonsResponse> retrofitObserver;

        retrofitObserver = networkCall.getTvShowSeasonList(tvShowId);

        retrofitObserver.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(seasonsNetworkApiObserver());
    }

    private Observer<SeasonsResponse> seasonsNetworkApiObserver() {
        return new Observer<SeasonsResponse>() {

            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(SeasonsResponse seasonsResponse) {
                onLoadSeasonList(seasonsResponse.getNumberOfSeasons(), seasonsResponse.getSeasonArrayList());
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
    public void onLoadSeasonList(int numberOfSeasons, List<Season> seasonList) {

        int i = 0;
        while (numberOfSeasons != seasonList.size()) {
            Season season = seasonList.get(i);
            if (season.getAirDate() == null || season.getSeasonNumber() == 0) {
                seasonList.remove(i);
            } else {
                i++;
            }
        }

        view.onSeasonsLoaded(seasonList);
    }

    @Override
    public void onNoInternetConnection() {

    }

    @Override
    public void onItemsLoadingError(Throwable t) {

    }

    @Override
    public void onSeasonItemClick(Context context, int seasonNumber, String posterPath) {
        Intent intent = new Intent(context, TvShowSeasonDetailActivity.class);
        intent.putExtra(TVSHOW_BACKROUND_EXTRA, posterPath);
        intent.putExtra(TVSHOW_ID_EXTRA, tvShowId);
        intent.putExtra(SEASON_NUMBER_EXTRA, seasonNumber);

        context.startActivity(intent);
    }


    @Override
    public void onTrailerClick(Context context, String key) {
        Intent intent = new Intent(context, YoutubeVideoActivity.class);
        intent.putExtra(MEDIA_URL_EXTRA, YOUTUBE_URL +
                key);
        context.startActivity(intent);
    }

    @Override
    public void onShareClick(Context context, String key) {
        String trailerUrl = YOUTUBE_SHARE_URL + key;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, trailerUrl);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, context.getString(R.string.share)));
    }

    public void onImageClicked(AppCompatActivity compatActivity, int position, ArrayList<Image>
            imageList, String tvShowName) {
        TvShowImagesGalleryFragment fragment = new TvShowImagesGalleryFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IMAGES_EXTRA, imageList);
        bundle.putInt(POSITION_EXTRA, position);
        bundle.putString(TV_SHOW_NAME_EXTRA, tvShowName);
        fragment.setArguments(bundle);
        compatActivity.getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(TvShowImagesGalleryFragment.class.getSimpleName())
                .add(R.id.container, fragment)
                .commit();
    }

    @Override
    public boolean checkIsFavorite(Context context, int id) {

        Cursor cursor = null;

        try {

            cursor = context.getContentResolver().query(TelePortalContract.FavoriteEntry.CONTENT_URI,
                    new String[]{TelePortalContract.FavoriteEntry.COLUMN_ID},
                    TelePortalContract.FavoriteEntry.COLUMN_ID + " = ? ",
                    new String[]{id + ""}, null);

            if (cursor != null && cursor.moveToFirst()) {
                view.showFavoriteIcon(true);
                return true;
            } else {
                view.showFavoriteIcon(false);
                return false;
            }

        } catch (Exception e) {
            return false;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override
    public void onFavoriteClick(Context context, TvShow tvShow) {

        boolean saved = checkIsFavorite(context, tvShow.getTvShowId());

        if (saved) {
            context.getContentResolver().delete(TelePortalContract.FavoriteEntry.CONTENT_URI,
                    TelePortalContract.FavoriteEntry.COLUMN_ID + " = ?",
                    new String[]{tvShow.getTvShowId()+""});
            view.showFavoriteIcon(false);
        } else {

            ContentValues contentValues = new ContentValues();
            contentValues.put(TelePortalContract.FavoriteEntry.COLUMN_ID, tvShow.getTvShowId());
            contentValues.put(TelePortalContract.FavoriteEntry.COLUMN_TITLE, tvShow.getTvShowName());
            contentValues.put(TelePortalContract.FavoriteEntry.COLUMN_VOTE_AVERAGE, tvShow.getVoteAverage());
            contentValues.put(TelePortalContract.FavoriteEntry.COLUMN_POSTER_PATH, tvShow.getPosterPath());
            contentValues.put(TelePortalContract.FavoriteEntry.COLUMN_BACKGROUND_POSTER_PATH, tvShow.getBackdropPath());
            contentValues.put(TelePortalContract.FavoriteEntry.COLUMN_RELEASE_DATE, tvShow.getFirstAirDate());
            contentValues.put(TelePortalContract.FavoriteEntry.COLUMN_OVERVIEW, tvShow.getOverView());

            context.getContentResolver().insert(TelePortalContract.FavoriteEntry.CONTENT_URI,
                    contentValues);
            view.showFavoriteIcon(true);
        }
    }


}

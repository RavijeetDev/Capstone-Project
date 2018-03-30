package com.ravijeet.teleportal.api;

import com.ravijeet.teleportal.BuildConfig;
import com.ravijeet.teleportal.bean.CastList;
import com.ravijeet.teleportal.bean.EpisodeResult;
import com.ravijeet.teleportal.bean.ImagesResponse;
import com.ravijeet.teleportal.bean.SeasonsResponse;
import com.ravijeet.teleportal.bean.TrailerResult;
import com.ravijeet.teleportal.bean.TvShowListResponse;

import io.reactivex.Observable;

/**
 * Created by Ravijeet on 2/27/18.
 */

public class NetworkCall {

    private TvShowApi tvShowApi;

    public NetworkCall(){
        tvShowApi = Api.getRetrofitClient().create(TvShowApi.class);
    }

    public Observable<TvShowListResponse> getTvShowList(String tvCategory, int pageNo) {
        return tvShowApi.getTVList(tvCategory, BuildConfig.API_KEY, pageNo);
    }

    public Observable<TrailerResult> getTrailers(int id) {
        return tvShowApi.getTrailer(id, BuildConfig.API_KEY);
    }

    public Observable<CastList> getCastList(int id) {
        return tvShowApi.getCastList(id, BuildConfig.API_KEY);
    }

    public Observable<ImagesResponse> getImageList(int id) {
        return tvShowApi.getImageList(id, BuildConfig.API_KEY);
    }

    public Observable<SeasonsResponse> getTvShowSeasonList(int tvShowId) {
        return tvShowApi.getSeasonList(tvShowId, BuildConfig.API_KEY);
    }

    public Observable<EpisodeResult> getEpisodeResult(int tvShowId, int seasonNumber){
        return tvShowApi.getEpisodeResult(tvShowId, seasonNumber, BuildConfig.API_KEY);
    }
}

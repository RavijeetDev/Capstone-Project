package com.ravijeet.teleportal.api;

import com.ravijeet.teleportal.bean.CastList;
import com.ravijeet.teleportal.bean.EpisodeResult;
import com.ravijeet.teleportal.bean.ImagesResponse;
import com.ravijeet.teleportal.bean.SeasonsResponse;
import com.ravijeet.teleportal.bean.TrailerResult;
import com.ravijeet.teleportal.bean.TvShowListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ravijeet on 2/27/18.
 */

public interface TvShowApi {

    @GET("tv/{type}")
    Observable<TvShowListResponse> getTVList(
            @Path("type") String sortingType,
            @Query("api_key") String apiKey,
            @Query("page") int pageNo
    );

    @GET("tv/{id}/videos")
    Observable<TrailerResult> getTrailer(
            @Path("id") int id,
            @Query("api_key") String key
    );

    @GET("tv/{id}/credits")
    Observable<CastList> getCastList(
            @Path("id") int id,
            @Query("api_key") String key
    );

    @GET("tv/{id}/images")
    Observable<ImagesResponse> getImageList(
            @Path("id") int id,
            @Query("api_key") String key
    );

    @GET("tv/{id}")
    Observable<SeasonsResponse> getSeasonList(
            @Path("id") int tvShowId,
            @Query("api_key") String key
    );

    @GET("tv/{tv_id}/season/{season_number}")
    Observable<EpisodeResult> getEpisodeResult(
            @Path("tv_id") int tvShowId,
            @Path("season_number") int seasonNumber,
            @Query("api_key") String key
    );
}

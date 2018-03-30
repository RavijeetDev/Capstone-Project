package com.ravijeet.teleportal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ravijeet on 3/1/18.
 */

public class TvShow implements Parcelable {

    @SerializedName("id")
    private int tvShowId;

    @SerializedName("name")
    private String tvShowName;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("overview")
    private String overView;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("genre_ids")
    private ArrayList<Integer> generesIds = new ArrayList<>();

    public TvShow() {

    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
    }

    public void setTvShowName(String tvShowName) {
        this.tvShowName = tvShowName;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setGeneresIds(ArrayList<Integer> generesIds) {
        this.generesIds = generesIds;
    }

    protected TvShow(Parcel in) {
        tvShowId = in.readInt();
        tvShowName = in.readString();
        posterPath = in.readString();
        overView = in.readString();
        backdropPath = in.readString();
        firstAirDate = in.readString();
        voteAverage = in.readDouble();
        in.readList(generesIds, Integer.class.getClassLoader());
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public int getTvShowId() {
        return tvShowId;
    }

    public String getTvShowName() {
        return tvShowName;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverView() {
        return overView;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public ArrayList<Integer> getGeneresIds() {
        return generesIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tvShowId);
        dest.writeString(tvShowName);
        dest.writeString(posterPath);
        dest.writeString(overView);
        dest.writeString(backdropPath);
        dest.writeString(firstAirDate);
        dest.writeDouble(voteAverage);
        dest.writeList(generesIds);
    }


}

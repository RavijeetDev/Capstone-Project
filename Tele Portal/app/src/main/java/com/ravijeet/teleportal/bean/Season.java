package com.ravijeet.teleportal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ravijeet on 3/1/18.
 */

public class Season implements Parcelable {

    @SerializedName("id")
    private int seasonId;

    @SerializedName("air_date")
    private String airDate;

    @SerializedName("episode_count")
    private String episodeCount;

    @SerializedName("poster_path")
    private String seasonPosterPath;

    @SerializedName("season_number")
    private int seasonNumber;

    protected Season(Parcel in) {
        seasonId = in.readInt();
        airDate = in.readString();
        episodeCount = in.readString();
        seasonPosterPath = in.readString();
        seasonNumber = in.readInt();
    }

    public static final Creator<Season> CREATOR = new Creator<Season>() {
        @Override
        public Season createFromParcel(Parcel in) {
            return new Season(in);
        }

        @Override
        public Season[] newArray(int size) {
            return new Season[size];
        }
    };

    public int getSeasonId() {
        return seasonId;
    }

    public String getAirDate() {
        return airDate;
    }

    public String getEpisodeCount() {
        return episodeCount;
    }

    public String getSeasonPosterPath() {
        return seasonPosterPath;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(seasonId);
        parcel.writeString(airDate);
        parcel.writeString(episodeCount);
        parcel.writeString(seasonPosterPath);
        parcel.writeInt(seasonNumber);
    }
}

package com.ravijeet.teleportal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ravijeet on 3/1/18.
 */

public class Episode implements Parcelable{

    @SerializedName("name")
    private String episodeName;

    @SerializedName("air_date")
    private String airDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("still_path")
    private String posterPath;

    protected Episode(Parcel in) {
        episodeName = in.readString();
        airDate = in.readString();
        overview = in.readString();
        posterPath = in.readString();
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

    public String getEpisodeName() {
        return episodeName;
    }

    public String getAirDate() {
        return airDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(episodeName);
        parcel.writeString(airDate);
        parcel.writeString(overview);
        parcel.writeString(posterPath);
    }
}

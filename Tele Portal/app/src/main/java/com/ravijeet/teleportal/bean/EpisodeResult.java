package com.ravijeet.teleportal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ravijeet on 3/1/18.
 */

public class EpisodeResult implements Parcelable{

    @SerializedName("episodes")
    private List<Episode> episodeList = null;

    protected EpisodeResult(Parcel in) {
        episodeList = in.createTypedArrayList(Episode.CREATOR);
    }

    public static final Creator<EpisodeResult> CREATOR = new Creator<EpisodeResult>() {
        @Override
        public EpisodeResult createFromParcel(Parcel in) {
            return new EpisodeResult(in);
        }

        @Override
        public EpisodeResult[] newArray(int size) {
            return new EpisodeResult[size];
        }
    };

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(episodeList);
    }
}

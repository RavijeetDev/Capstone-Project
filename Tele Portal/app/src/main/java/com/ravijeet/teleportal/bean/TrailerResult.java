package com.ravijeet.teleportal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ravijeet on 3/1/18.
 */

public class TrailerResult implements Parcelable{

    @SerializedName("results")
    private List<Trailer> trailerList = null;

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    protected TrailerResult(Parcel in) {
        trailerList = in.createTypedArrayList(Trailer.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(trailerList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrailerResult> CREATOR = new Creator<TrailerResult>() {
        @Override
        public TrailerResult createFromParcel(Parcel in) {
            return new TrailerResult(in);
        }

        @Override
        public TrailerResult[] newArray(int size) {
            return new TrailerResult[size];
        }
    };
}

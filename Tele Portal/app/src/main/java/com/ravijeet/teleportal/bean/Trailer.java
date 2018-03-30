package com.ravijeet.teleportal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ravijeet on 3/1/18.
 */

public class Trailer implements Parcelable{

    @SerializedName("id")
    private String trailerId;

    @SerializedName("key")
    private String key;

    @SerializedName("site")
    private String site;

    protected Trailer(Parcel in) {
        trailerId = in.readString();
        key = in.readString();
        site = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getTrailerId() {
        return trailerId;
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(trailerId);
        parcel.writeString(key);
        parcel.writeString(site);
    }
}

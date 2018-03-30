package com.ravijeet.teleportal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ravijeet on 3/1/18.
 */

public class SeasonsResponse implements Parcelable{

    @SerializedName("id")
    private int id;

    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @SerializedName("seasons")
    private ArrayList<Season> seasonArrayList = null;

    protected SeasonsResponse(Parcel in) {
        id = in.readInt();
        numberOfSeasons = in.readInt();
        seasonArrayList = in.createTypedArrayList(Season.CREATOR);
    }

    public static final Creator<SeasonsResponse> CREATOR = new Creator<SeasonsResponse>() {
        @Override
        public SeasonsResponse createFromParcel(Parcel in) {
            return new SeasonsResponse(in);
        }

        @Override
        public SeasonsResponse[] newArray(int size) {
            return new SeasonsResponse[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasonArrayList() {
        return seasonArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(numberOfSeasons);
        parcel.writeTypedList(seasonArrayList);
    }
}

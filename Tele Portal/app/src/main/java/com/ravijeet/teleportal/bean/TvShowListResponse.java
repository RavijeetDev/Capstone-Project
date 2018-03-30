package com.ravijeet.teleportal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravijeet on 3/1/18.
 */

public class TvShowListResponse implements Parcelable {


    @SerializedName("results")
    private ArrayList<TvShow> tvShowList = new ArrayList<>();

    @SerializedName("total_pages")
    private int totalPages;


    public ArrayList<TvShow> getTvShowList() {
        return tvShowList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(tvShowList);
        parcel.writeInt(totalPages);
    }

    protected TvShowListResponse(Parcel in) {
        tvShowList = in.createTypedArrayList(TvShow.CREATOR);
        totalPages = in.readInt();
    }

    public static final Creator<TvShowListResponse> CREATOR = new Creator<TvShowListResponse>() {
        @Override
        public TvShowListResponse createFromParcel(Parcel in) {
            return new TvShowListResponse(in);
        }

        @Override
        public TvShowListResponse[] newArray(int size) {
            return new TvShowListResponse[size];
        }
    };
}


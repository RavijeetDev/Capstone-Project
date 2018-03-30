package com.ravijeet.teleportal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ravijeet on 3/1/18.
 */

public class ImagesResponse implements Parcelable{

    @SerializedName("backdrops")
    ArrayList<Image> imageArrayList;

    protected ImagesResponse(Parcel in) {
        imageArrayList = in.createTypedArrayList(Image.CREATOR);
    }

    public static final Creator<ImagesResponse> CREATOR = new Creator<ImagesResponse>() {
        @Override
        public ImagesResponse createFromParcel(Parcel in) {
            return new ImagesResponse(in);
        }

        @Override
        public ImagesResponse[] newArray(int size) {
            return new ImagesResponse[size];
        }
    };

    public ArrayList<Image> getImageArrayList() {
        return imageArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(imageArrayList);
    }
}

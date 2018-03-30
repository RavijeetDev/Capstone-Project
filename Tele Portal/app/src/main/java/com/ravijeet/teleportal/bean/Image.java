package com.ravijeet.teleportal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ravijeet on 3/1/18.
 */

public class Image implements Parcelable{

    @SerializedName("aspect_ratio")
    private double aspectRatio;

    @SerializedName("file_path")
    private String imagePath;

    @SerializedName("height")
    private int imageHeight;

    @SerializedName("width")
    private int imageWidth;

    protected Image(Parcel in) {
        aspectRatio = in.readDouble();
        imagePath = in.readString();
        imageHeight = in.readInt();
        imageWidth = in.readInt();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public double getAspectRatio() {
        return aspectRatio;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(aspectRatio);
        parcel.writeString(imagePath);
        parcel.writeInt(imageHeight);
        parcel.writeInt(imageWidth);
    }
}

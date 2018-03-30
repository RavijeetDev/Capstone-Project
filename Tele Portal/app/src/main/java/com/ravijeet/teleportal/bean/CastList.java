package com.ravijeet.teleportal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ravijeet on 3/1/18.
 */

public class CastList implements Parcelable{

    @SerializedName("id")
    private int id;

    @SerializedName("cast")
    private List<Cast> castList = null;

    protected CastList(Parcel in) {
        id = in.readInt();
        castList = in.createTypedArrayList(Cast.CREATOR);
    }

    public static final Creator<CastList> CREATOR = new Creator<CastList>() {
        @Override
        public CastList createFromParcel(Parcel in) {
            return new CastList(in);
        }

        @Override
        public CastList[] newArray(int size) {
            return new CastList[size];
        }
    };

    public int getId() {
        return id;
    }

    public List<Cast> getCastList() {
        return castList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeTypedList(castList);
    }
}

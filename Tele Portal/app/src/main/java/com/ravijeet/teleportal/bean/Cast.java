package com.ravijeet.teleportal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ravijeet on 3/1/18.
 */

public class Cast implements Parcelable{

    @SerializedName("cast_id")
    private int castId;

    @SerializedName("character")
    private String character;

    @SerializedName("credit_id")
    private String creditId;

    @SerializedName("gender")
    private int gender;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int characterId;

    @SerializedName("order")
    private int order;

    @SerializedName("profile_path")
    private String profilePath;


    protected Cast(Parcel in) {
        castId = in.readInt();
        character = in.readString();
        creditId = in.readString();
        gender = in.readInt();
        name = in.readString();
        characterId = in.readInt();
        order = in.readInt();
        profilePath = in.readString();
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };

    public int getCastId() {
        return castId;
    }

    public String getCharacter() {
        return character;
    }

    public String getCreditId() {
        return creditId;
    }

    public int getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public int getCharacterId() {
        return characterId;
    }

    public int getOrder() {
        return order;
    }

    public String getProfilePath() {
        return profilePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(castId);
        parcel.writeString(character);
        parcel.writeString(creditId);
        parcel.writeInt(gender);
        parcel.writeString(name);
        parcel.writeInt(characterId);
        parcel.writeInt(order);
        parcel.writeString(profilePath);
    }
}

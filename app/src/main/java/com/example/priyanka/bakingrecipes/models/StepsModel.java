package com.example.priyanka.bakingrecipes.models;

import android.os.Parcel;
import android.os.Parcelable;

public class StepsModel implements Parcelable {

//    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
    }

    public StepsModel() {
    }

    protected StepsModel(Parcel in) {
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
    }

    public static final Parcelable.Creator<StepsModel> CREATOR = new Parcelable.Creator<StepsModel>() {
        @Override
        public StepsModel createFromParcel(Parcel source) {
            return new StepsModel(source);
        }

        @Override
        public StepsModel[] newArray(int size) {
            return new StepsModel[size];
        }
    };
}

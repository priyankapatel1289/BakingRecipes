package com.example.priyanka.bakingrecipes.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecipeModel implements Parcelable {

    private ArrayList<IngredientsModel> ingredients = null;
    private ArrayList<StepsModel> steps = null;
    private ArrayList<StepsModel> videoUrl = null;

    public ArrayList<StepsModel> getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(ArrayList<StepsModel> videoUrl) {
        this.videoUrl = videoUrl;
    }

    private String name;
    private int servings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public ArrayList<IngredientsModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientsModel> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<StepsModel> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepsModel> steps) {
        this.steps = steps;
    }

    public RecipeModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
        dest.writeTypedList(this.videoUrl);
        dest.writeString(this.name);
        dest.writeInt(this.servings);
    }

    protected RecipeModel(Parcel in) {
        this.ingredients = in.createTypedArrayList(IngredientsModel.CREATOR);
        this.steps = in.createTypedArrayList(StepsModel.CREATOR);
        this.videoUrl = in.createTypedArrayList(StepsModel.CREATOR);
        this.name = in.readString();
        this.servings = in.readInt();
    }

    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel source) {
            return new RecipeModel(source);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };
}

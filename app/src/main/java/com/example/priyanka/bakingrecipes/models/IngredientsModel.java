package com.example.priyanka.bakingrecipes.models;

import android.os.Parcel;
import android.os.Parcelable;

public class IngredientsModel implements Parcelable {

    private float quantity;
    private String measure;
    private String ingredient;

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    public IngredientsModel() {
    }

    protected IngredientsModel(Parcel in) {
        this.quantity = in.readFloat();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Parcelable.Creator<IngredientsModel> CREATOR = new Parcelable.Creator<IngredientsModel>() {
        @Override
        public IngredientsModel createFromParcel(Parcel source) {
            return new IngredientsModel(source);
        }

        @Override
        public IngredientsModel[] newArray(int size) {
            return new IngredientsModel[size];
        }
    };
}

package com.example.nguoidulich20.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CityDistances implements Parcelable {
    private int[] distances;
    private int numberOfCities;

    public CityDistances(int[][] distances) {
        this.numberOfCities = distances.length;
        this.distances = new int[numberOfCities * numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                this.distances[i * numberOfCities + j] = distances[i][j];
            }
        }
    }

    protected CityDistances(Parcel in) {
        numberOfCities = in.readInt();
        distances = new int[numberOfCities * numberOfCities];
        in.readIntArray(distances);
    }

    public static final Creator<CityDistances> CREATOR = new Creator<CityDistances>() {
        @Override
        public CityDistances createFromParcel(Parcel in) {
            return new CityDistances(in);
        }

        @Override
        public CityDistances[] newArray(int size) {
            return new CityDistances[size];
        }
    };

    public int[][] getDistances() {
        int[][] result = new int[numberOfCities][numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                result[i][j] = distances[i * numberOfCities + j];
            }
        }
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numberOfCities);
        dest.writeIntArray(distances);
    }
}

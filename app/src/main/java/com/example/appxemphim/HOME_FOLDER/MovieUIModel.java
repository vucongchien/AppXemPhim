package com.example.appxemphim.HOME_FOLDER;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MovieUIModel implements Parcelable{
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(movieId);
        parcel.writeString(title);
        parcel.writeString(posterUrl);
        parcel.writeString(rating);
        parcel.writeString(numView);
        parcel.writeString(year);
        parcel.writeString(description);
    }

    private int movieId;
    private String title;
    private String posterUrl;
    private String rating;
    private String numView;
    private String year;
    private String description;


    public MovieUIModel(int movieId, String title, String posterUrl, String rating,String numView,String year, String description) {
        this.movieId = movieId;
        this.title = title;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.numView=numView;
        this.year=year;
        this.description = description;
    }

    protected MovieUIModel(Parcel parcel){
        this.movieId = parcel.readInt();
        this.title = parcel.readString();
        this.posterUrl = parcel.readString();
        this.rating = parcel.readString();
        this.numView=parcel.readString();
        this.year=parcel.readString();
        this.description = parcel.readString();
    }

    public static final Parcelable.Creator<MovieUIModel> CREATOR=new Parcelable.Creator<MovieUIModel>() {
        @Override
        public MovieUIModel createFromParcel(Parcel parcel) {
            return new MovieUIModel(parcel);
        }

        @Override
        public MovieUIModel[] newArray(int i) {
            return new MovieUIModel[i];
        }
    };

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getNumView() {
        return numView;
    }

    public void setNumView(String numView) {
        this.numView = numView;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

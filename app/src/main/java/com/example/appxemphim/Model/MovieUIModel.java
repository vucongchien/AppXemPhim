package com.example.appxemphim.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class MovieUIModel implements Parcelable {

    @SerializedName("movie_Id")
    private int movieId;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_url")
    private String posterUrl;

    @SerializedName("rating")
    private String rating;

    @SerializedName("num_view")
    private String numView;

    @SerializedName("year")
    private String year;

    @SerializedName("description")
    private String description;



    // Constructor
    public MovieUIModel(int movieId, String title, String posterUrl, String rating, String numView, String year, String description) {
        this.movieId = movieId;
        this.title = title;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.numView = numView;
        this.year = year;
        this.description = description;
    }

    // Constructor dùng cho Parcelable
    protected MovieUIModel(Parcel parcel) {
        movieId = parcel.readInt();
        title = parcel.readString();
        posterUrl = parcel.readString();
        rating = parcel.readString();
        numView = parcel.readString();
        year = parcel.readString();
        description = parcel.readString();
    }

    // Parcelable implementation
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

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieUIModel> CREATOR = new Creator<MovieUIModel>() {
        @Override
        public MovieUIModel createFromParcel(Parcel in) {
            return new MovieUIModel(in);
        }

        @Override
        public MovieUIModel[] newArray(int size) {
            return new MovieUIModel[size];
        }
    };

    // equals và hashCode (so sánh theo movieId)
    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MovieUIModel that = (MovieUIModel) obj;
        return movieId == that.movieId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }

    // Getter & Setter
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

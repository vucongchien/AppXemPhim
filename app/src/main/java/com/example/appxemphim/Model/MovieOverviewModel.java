package com.example.appxemphim.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class MovieOverviewModel implements Parcelable {

    @SerializedName("movie_Id")
    private String movieId;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("poster_url")
    private String posterUrl;
    @SerializedName("trailer_url")
    private String trailerUrl;
    @SerializedName("rating")
    private Double rating;
    @SerializedName("nation")
    private String nation;
    @SerializedName("num_view")
    private String numView;

    @SerializedName("year")
    private String year;

    @SerializedName("created_at")
    private Timestamp createdAt;



    // Constructor
    public MovieOverviewModel(String movieId, String title, String posterUrl, Double rating, String numView, String year, String description) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.posterUrl = posterUrl;
        this.trailerUrl = trailerUrl;
        this.rating = rating;
        this.nation = nation;
        this.numView = numView;
        this.createdAt = createdAt;
    }

    // Constructor dùng cho Parcelable
    protected MovieOverviewModel(Parcel parcel) {
        movieId = parcel.readString();
        title = parcel.readString();
        posterUrl = parcel.readString();
        rating = parcel.readDouble();
        numView = parcel.readString();
        description = parcel.readString();
    }

    // Parcelable implementation
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(movieId);
        parcel.writeString(title);
        parcel.writeString(posterUrl);
        parcel.writeDouble(rating);
        parcel.writeString(numView);
        parcel.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieOverviewModel> CREATOR = new Creator<MovieOverviewModel>() {
        @Override
        public MovieOverviewModel createFromParcel(Parcel in) {
            return new MovieOverviewModel(in);
        }

        @Override
        public MovieOverviewModel[] newArray(int size) {
            return new MovieOverviewModel[size];
        }
    };

    // equals và hashCode (so sánh theo movieId)
    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MovieOverviewModel that = (MovieOverviewModel) obj;
        return movieId == that.movieId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNumView() {
        return numView;
    }

    public void setNumView(String numView) {
        this.numView = numView;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MovieOverviewModel{" +
                "movieId='" + movieId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                ", rating=" + rating +
                ", nation='" + nation + '\'' +
                ", numView='" + numView + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

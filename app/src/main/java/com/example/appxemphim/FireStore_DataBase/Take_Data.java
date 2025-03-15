package com.example.appxemphim.FireStore_DataBase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.appxemphim.object_data.Movie;
import com.example.appxemphim.object_data.Video;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
public class Take_Data {
    public interface MovieCallback {
        void onSuccess(ArrayList<Movie> movieList);
        void onFailure(String errorMessage);
    }

    public static void take_movie(Context context, FirebaseFirestore db, MovieCallback callback) {
        db.collection("movies")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        ArrayList<Movie> movieList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            Toast.makeText(context, movie.getMovie_Id(), Toast.LENGTH_SHORT).show();
                            movieList.add(movie);
                        }
                        callback.onSuccess(movieList);
                    } else {
                        Log.e("Firestore", "Lỗi khi lấy dữ liệu: ", task.getException());
                        Toast.makeText(context, "Lỗi khi lấy dữ liệu!", Toast.LENGTH_SHORT).show();
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

}

package com.example.appxemphim.FireStore_DataBase;

import android.content.Context;
import android.widget.Toast;

import com.example.appxemphim.object_data.Movie;
import com.google.firebase.firestore.FirebaseFirestore;

public class Add_Data {
    public static void Add_Data_movie(Context context,FirebaseFirestore db, Movie movie){
        String movieId = db.collection("movies").document().getId();
        if (movieId != null) {
            movie.setMovie_Id(movieId);
            db.collection("movies").document(movieId)
                    .set(movie)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(context, "Movie đã được lưu thành công!", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Lỗi khi lưu movie: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        }
    }
}

package com.example.appxemphim.FireStore_DataBase;

import com.example.appxemphim.object_data.Movie;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_Data {
    public static void Add_Data_movie(FirebaseDatabase db, Movie movie){
        DatabaseReference moviesRef = db.getReference("movies");
        String movieId = moviesRef.push().getKey();
        if (movieId != null) {
            movie.setMovie_Id(movieId);
            moviesRef.child(movieId).setValue(movie)
                    .addOnSuccessListener(task ->
                            System.out.println("Movie đã được lưu thành công!")
                    )
                    .addOnFailureListener(e ->
                            System.err.println("Lỗi khi lưu movie: " + e.getMessage())
                    );
        }
    }
}

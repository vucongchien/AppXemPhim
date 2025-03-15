package com.example.appxemphim.FireStore_DataBase;

import android.content.Context;
import android.widget.Toast;

import com.example.appxemphim.object_data.Actor_Director;
import com.example.appxemphim.object_data.Favourite_List;
import com.example.appxemphim.object_data.Genres;
import com.example.appxemphim.object_data.History;
import com.example.appxemphim.object_data.Hot_Movie;
import com.example.appxemphim.object_data.Movie;
import com.example.appxemphim.object_data.Movie_Actor_Director;
import com.example.appxemphim.object_data.Movie_Genres;
import com.example.appxemphim.object_data.Review;
import com.example.appxemphim.object_data.Video;
import com.google.firebase.firestore.FieldValue;
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
    public static  void  Add_Data_video(Context context, FirebaseFirestore db, Video video, String maphim){
        String videoId = db.collection("Video").document().getId();
        if(videoId!=null){
            db.collection("movies").document(maphim)
                    .update("videos", FieldValue.arrayUnion(video))
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(context, "Video đã được thêm vào movie!", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Lỗi khi thêm video: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        }
    }

    public static void Add_Actor_Direction(Context context, FirebaseFirestore db, Actor_Director test){
        String test_id = db.collection("Actor_Direction").document().getId();
        if (test_id != null) {
            test.setActor_Director_id(test_id);
            db.collection("Actor_Direction").document(test_id)
                    .set(test)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(context, "Actor_Direction đã được lưu thành công!", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Lỗi khi lưu Actor_Direction: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        }
    }

    public static void Add_Facourite_List(Context context, FirebaseFirestore db, Favourite_List test){
        String test_id = db.collection("Favourite_List").document().getId();
        if (test_id != null) {
            test.setFavourite_list_id(test_id);
            db.collection("Favourite_List").document(test_id)
                    .set(test)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(context, "Favourite_List đã được lưu thành công!", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Lỗi khi lưu Favourite_List: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        }
    }

    public static void Add_Genres(Context context, FirebaseFirestore db, Genres test){
        String test_id = db.collection("Genres").document().getId();
        if (test_id != null) {
            test.setGenres_id(test_id);
            db.collection("Genres").document(test_id)
                    .set(test)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(context, "Genres đã được lưu thành công!", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Lỗi khi lưu Genres: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        }
    }

    public static void Add_History(Context context, FirebaseFirestore db, History test){
        String test_id = db.collection("History").document().getId();
        if (test_id != null) {
            test.setHistory_id(test_id);
            db.collection("History").document(test_id)
                    .set(test)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(context, "History đã được lưu thành công!", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Lỗi khi lưu History: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        }
    }

    public static void Add_Hot_Movie(Context context, FirebaseFirestore db, Hot_Movie test){
        String test_id = db.collection("Hot_Movie").document().getId();
        if (test_id != null) {
            test.setHot_movie_id(test_id);
            db.collection("Hot_Movie").document(test_id)
                    .set(test)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(context, "Hot_Movie đã được lưu thành công!", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Lỗi khi lưu Hot_Movie: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        }
    }

    public static void Add_Movie_Actor_Director(Context context, FirebaseFirestore db, Movie_Actor_Director test){
        db.collection("Movie_Actor_Director")
                .add(test)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(context, "Movie_Actor_Director đã được lưu thành công!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Lỗi khi lưu Movie_Actor_Director: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    public static void Add_Movie_Genres(Context context, FirebaseFirestore db, Movie_Genres test){
        db.collection("Movie_Genres")
                .add(test)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(context, "Movie_Genres đã được lưu thành công!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Lỗi khi lưu Movie_Genres: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    public static void Add_Review(Context context, FirebaseFirestore db, Review test){
        String test_id = db.collection("Review").document().getId();
        if (test_id != null) {
            test.setReview_id(test_id);
            db.collection("Review").document(test_id)
                    .set(test)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(context, "Review đã được lưu thành công!", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Lỗi khi lưu Review: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        }
    }

}

package com.example.appxemphim.FireStore_DataBase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appxemphim.Add_data_sample;
import com.example.appxemphim.object_data.Movie;
import com.example.appxemphim.object_data.Video;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

}

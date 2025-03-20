package com.example.appxemphim.FireStore_DataBase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.appxemphim.object_data.Movie;
import com.example.appxemphim.object_data.Video;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Take_Data {
    public interface MovieCallback {
        void onSuccess(ArrayList<Movie> movieList);
        void onFailure(String errorMessage);
    }

    public interface VideoCallback {
        void onSuccess(ArrayList<Video> videoList);
        void onFailure(String errorMessage);
    }

    public static void take_Movie(Context context, FirebaseFirestore db, MovieCallback callback) {
        db.collection("Movies")
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

    public static void take_Video(Context context, FirebaseFirestore db, String maMovie, VideoCallback callback) {
        db.collection("Movies").document(maMovie)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        List<String> idVideoList = (List<String>) document.get("videos");

                        if (idVideoList == null || idVideoList.isEmpty()) {
                            Log.e("Firestore", "Danh sách video trống hoặc không tồn tại!");
                            callback.onFailure("Danh sách video trống!");
                            return;
                        }

                        // Danh sách các task Firestore
                        List<Task<DocumentSnapshot>> tasks = new ArrayList<>();

                        for (String id : idVideoList) {
                            Task<DocumentSnapshot> videoTask = db.collection("Video").document(id).get();
                            tasks.add(videoTask);
                        }

                        // Khi tất cả task hoàn thành
                        Tasks.whenAllSuccess(tasks).addOnSuccessListener(results -> {
                            ArrayList<Video> videolist = new ArrayList<>();

                            for (Object result : results) {
                                DocumentSnapshot videoDoc = (DocumentSnapshot) result;
                                Video video = videoDoc.toObject(Video.class);
                                if (video != null) {
                                    videolist.add(video);
                                }
                            }

                            callback.onSuccess(videolist);
                        }).addOnFailureListener(e -> {
                            Log.e("Firestore", "Lỗi khi lấy danh sách video!", e);
                            callback.onFailure(e.getMessage());
                        });

                    } else {
                        Log.e("Firestore", "Lỗi khi lấy thông tin Movie: ", task.getException());
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }



}

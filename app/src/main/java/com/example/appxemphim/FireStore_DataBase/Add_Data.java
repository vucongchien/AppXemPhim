package com.example.appxemphim.FireStore_DataBase;

import static com.example.appxemphim.Utilities.GoogleDriveUtils.exportLink;

import android.content.Context;
import android.widget.Toast;

import com.example.appxemphim.Utilities.GoogleDriveUtils;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Add_Data {
    public static void Add_Data_movie(Context context,FirebaseFirestore db, Movie movie){
        String movieId = db.collection("Movies").document().getId();
        if (movieId != null) {
            movie.setMovie_Id(movieId);
            movie.setPoster_url(exportLink(movie.getPoster_url()));
            movie.setTrailer_url(exportLink(movie.getTrailer_url()));
            db.collection("Movies").document(movieId)
                    .set(movie)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(context, "Movie đã được lưu thành công!", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Lỗi khi lưu movie: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        }
    }
    public static  void  Add_Data_video(Context context, FirebaseFirestore db, Video video, String maphim) {
        String videoId = db.collection("Video").document().getId();
        if(videoId!=null){
            video.setVideo_id(videoId);
            GoogleDriveUtils.getVideoDuration(context, video.getVideo_url(), new GoogleDriveUtils.VideoDurationCallback() {
                @Override
                public void onSuccess(long durationMillis) {
                    video.setDuration(durationMillis);
                    video.setVideo_url(exportLink(video.getVideo_url()));
                    Toast.makeText(context, "Thời gian video: " + video.getDuration() + " ms", Toast.LENGTH_SHORT).show();
                    db.collection("Movies").document(maphim)
                            .update("videos", FieldValue.arrayUnion(video.getVideo_id()))
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(context, "Video đã được thêm vào movie!", Toast.LENGTH_SHORT).show()
                            )
                            .addOnFailureListener(e ->
                                    Toast.makeText(context, "Lỗi khi thêm video: " + e.getMessage(), Toast.LENGTH_LONG).show()
                            );
                    db.collection("Video").document(videoId).set(video).addOnSuccessListener(aVoid ->
                                    Toast.makeText(context, "Movie đã được lưu thành công!", Toast.LENGTH_SHORT).show()
                            )
                            .addOnFailureListener(e ->
                                    Toast.makeText(context, "Lỗi khi lưu movie: " + e.getMessage(), Toast.LENGTH_LONG).show()
                            );
                }

                @Override
                public void onError(String errorMessage) {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    public static void Add_Facourite_List(Context context, FirebaseFirestore db, FirebaseUser user, String ma_video){
        if(user == null){
            Toast.makeText(context, "loi user", Toast.LENGTH_SHORT).show();
            return;
        }
        String test_id = user.getUid();
        if (test_id != null) {
            Date now = new Date();
            Favourite_List favouriteList = new Favourite_List(ma_video, now);
            DocumentReference docRef = db.collection("Favourite_List").document(test_id);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    docRef.update("videos", FieldValue.arrayUnion(favouriteList))
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(context, "Video đã được thêm vào Favourite_List!", Toast.LENGTH_SHORT).show()
                            )
                            .addOnFailureListener(e ->
                                    Toast.makeText(context, "Lỗi khi thêm video: " + e.getMessage(), Toast.LENGTH_LONG).show()
                            );
                } else {
                    Map<String, Object> favouriteListData = new HashMap<>();
                    favouriteListData.put("videos", Collections.singletonList(favouriteList));
                    docRef.set(favouriteListData)
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(context, "Video đã được thêm vào Favourite_List!", Toast.LENGTH_SHORT).show()
                            )
                            .addOnFailureListener(e ->
                                    Toast.makeText(context, "Lỗi khi tạo lịch sử xem: " + e.getMessage(), Toast.LENGTH_LONG).show()
                            );
                }
            }).addOnFailureListener(e ->
                    Toast.makeText(context, "Lỗi khi kiểm tra danh sách yêu thích: " + e.getMessage(), Toast.LENGTH_LONG).show()
            );
        }
    }

    public static void Add_History(Context context, FirebaseFirestore db, FirebaseUser user, String ma_video,double person_view){
        if(user == null){
            Toast.makeText(context, "loi user", Toast.LENGTH_SHORT).show();
            return;
        }
        String test_id = user.getUid();
        if (test_id != null) {
            Date now = new Date();
            History history = new History(ma_video,person_view, now);
            DocumentReference docRef = db.collection("History").document(test_id);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    docRef.update("videos", FieldValue.arrayUnion(history))
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(context, "Video đã được thêm vào History!", Toast.LENGTH_SHORT).show()
                            )
                            .addOnFailureListener(e ->
                                    Toast.makeText(context, "Lỗi khi thêm video: " + e.getMessage(), Toast.LENGTH_LONG).show()
                            );
                } else {
                    Map<String, Object> historyData = new HashMap<>();
                    historyData.put("videos", Collections.singletonList(history));
                    docRef.set(historyData) // Sử dụng set() để tạo tài liệu
                            .addOnSuccessListener(aVoid ->
                                    Toast.makeText(context, "Video đã được thêm vào History!", Toast.LENGTH_SHORT).show()
                            )
                            .addOnFailureListener(e ->
                                    Toast.makeText(context, "Lỗi khi tạo lịch sử xem: " + e.getMessage(), Toast.LENGTH_LONG).show()
                            );
                }
            }).addOnFailureListener(e ->
                    Toast.makeText(context, "Lỗi khi kiểm tra danh sách yêu thích: " + e.getMessage(), Toast.LENGTH_LONG).show()
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

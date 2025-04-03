package com.example.appxemphim.FireStore_DataBase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.appxemphim.objectDTO.MovieDTO;
import com.example.appxemphim.object_data.Actor_Director;
import com.example.appxemphim.object_data.Genres;
import com.example.appxemphim.object_data.Movie;
import com.example.appxemphim.object_data.Video;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

    public  interface MovieIDCallback{
        void  onSuccess(MovieDTO movieDTO);
        void onFailure(String e);
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

    public static void take_Movie_by_Id(Context context, FirebaseFirestore db, String movieId, MovieIDCallback callback) {
        MovieDTO movieDTO = new MovieDTO();
        List<Task<?>> tasks = new ArrayList<>();
        List<String> tmpID = new ArrayList<>();
        List<Task<DocumentSnapshot>> videoTasks = new ArrayList<>();

        // Lấy thông tin chính của phim
        Task<DocumentSnapshot> movieTask = db.collection("Movies").document(movieId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().exists()) {
                        Movie movie = task.getResult().toObject(Movie.class);
                        List<String> tmp = (List<String>) task.getResult().get("videos");
                        tmpID.addAll(tmp);
                        movieDTO.setMovie(movie);
                    } else {
                        callback.onFailure("Không tìm thấy phim hoặc lỗi: " + task.getException().getMessage());
                    }
                });
        tasks.add(movieTask);

        for (String doc : tmpID) {
            Task<DocumentSnapshot> videoTask = db.collection("Video").document(doc).get()
                    .continueWithTask(task -> {
                        if (task.isSuccessful() && task.getResult().exists()) {
                            movieDTO.getVideos().add(task.getResult().toObject(Video.class));
                        }
                        return task;
                    });
            videoTasks.add(videoTask);
        }
        tasks.add(Tasks.whenAllComplete(videoTasks));

        // Lấy danh sách diễn viên
        Task<QuerySnapshot> actorTask = db.collection("Movie_Actor").whereEqualTo("movive_Id", movieId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Task<DocumentSnapshot>> actorDirectorTasks = new ArrayList<>();
                        for (DocumentSnapshot doc : task.getResult()) {
                            String actorDirectorId = doc.getString("actor_Director_id");
                            Task<DocumentSnapshot> actorDirectorDocTask = db.collection("Actor_Direction").document(actorDirectorId).get()
                                    .addOnSuccessListener(actorDirectorDoc -> {
                                        if (actorDirectorDoc.exists()) {
                                            movieDTO.getActors().add(actorDirectorDoc.toObject(Actor_Director.class));
                                        }
                                    });
                            actorDirectorTasks.add(actorDirectorDocTask);
                        }
                        tasks.add(Tasks.whenAllComplete(actorDirectorTasks));
                    }
                });
        tasks.add(actorTask);

        // Lấy danh sách đạo diễn
        Task<QuerySnapshot> directorTask = db.collection("Movie_Director").whereEqualTo("movive_Id", movieId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Task<DocumentSnapshot>> actorDirectorTasks = new ArrayList<>();
                        for (DocumentSnapshot doc : task.getResult()) {
                            String actorDirectorId = doc.getString("actor_Director_id");
                            Task<DocumentSnapshot> actorDirectorDocTask = db.collection("Actor_Direction").document(actorDirectorId).get()
                                    .addOnSuccessListener(actorDirectorDoc -> {
                                        if (actorDirectorDoc.exists()) {
                                            movieDTO.getDirectors().add(actorDirectorDoc.toObject(Actor_Director.class));
                                        }
                                    });
                            actorDirectorTasks.add(actorDirectorDocTask);
                        }
                        tasks.add(Tasks.whenAllComplete(actorDirectorTasks));
                    }
                });
        tasks.add(directorTask);

        // Lấy danh sách thể loại phim
        Task<QuerySnapshot> genresTask = db.collection("Movie_Genres").whereEqualTo("movive_Id", movieId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Task<DocumentSnapshot>> genresTasks = new ArrayList<>();
                        for (DocumentSnapshot doc : task.getResult()) {
                            String genreId = doc.getString("genres_id");
                            Task<DocumentSnapshot> genreTask = db.collection("Genres").document(genreId).get()
                                    .addOnSuccessListener(genreDoc -> {
                                        if (genreDoc.exists()) {
                                            movieDTO.getGenres().add(genreDoc.toObject(Genres.class));
                                        }
                                    });
                            genresTasks.add(genreTask);
                        }
                        tasks.add(Tasks.whenAllComplete(genresTasks));
                    }
                });
        tasks.add(genresTask);

        // Chờ tất cả các tác vụ hoàn thành
        Tasks.whenAllComplete(tasks).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(movieDTO);
            } else {
                callback.onFailure("Lỗi khi tải dữ liệu phim");
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

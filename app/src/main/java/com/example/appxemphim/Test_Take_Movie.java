package com.example.appxemphim;

import static com.example.appxemphim.FireStore_DataBase.Take_Data.take_movie;
import static com.example.appxemphim.Utilities.GoogleDriveUtils.exportLink;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appxemphim.FireStore_DataBase.Take_Data;
import com.example.appxemphim.object_data.Movie;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import com.squareup.picasso.Callback;

public class Test_Take_Movie extends MainActivity {
    ImageView avata;
    TextView thong;
    ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_take_movie);
        avata = findViewById(R.id.avata);
        thong = findViewById(R.id.non);
        take_movie(this, db, new Take_Data.MovieCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movieList) {
                movies = movieList;

                if (movies == null) {
                    Toast.makeText(Test_Take_Movie.this, "ds rong", Toast.LENGTH_SHORT).show();
                    return;
                }

                String posterUrl = movies.get(0).getPoster_url();
                if (posterUrl == null || posterUrl.isEmpty()) {
                    Toast.makeText(Test_Take_Movie.this, "Không có URL ảnh!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String imageUrl = exportLink(posterUrl);
                if (imageUrl != null && imageUrl.startsWith("http")) {
                    Picasso.get()
                            .load(imageUrl)
                            .error(R.drawable.intro_pic) // Hiển thị ảnh lỗi nếu load thất bại
                            .into(avata, new Callback() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(Test_Take_Movie.this, "Ảnh đã tải thành công", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(Exception e) {
                                    Toast.makeText(Test_Take_Movie.this, "Lỗi tải ảnh: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                } else {
                    Toast.makeText(Test_Take_Movie.this, "URL ảnh không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(Test_Take_Movie.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
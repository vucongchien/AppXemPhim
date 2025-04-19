package com.example.appxemphim.UI.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appxemphim.Model.ActorModel;
import com.example.appxemphim.Model.DirectorModel;
import com.example.appxemphim.Model.MovieDetailModel;
import com.example.appxemphim.Network.MovieApiService;
import com.example.appxemphim.Network.RetrofitClient;
import com.example.appxemphim.R;
import com.example.appxemphim.UI.Adapter.ListVideoAdapter;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    TextView description,actor,directors,title,year;
    ListView videoList;
    ListVideoAdapter listVideoAdapter;
    MovieDetailModel movieDetails;
    String des,detaildes;
    String clickableText = "Learn more";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_details);
        description = findViewById(R.id.description);
        description.setMovementMethod(new ScrollingMovementMethod());
        actor = findViewById(R.id.actor);
        directors = findViewById(R.id.directors);
        title = findViewById(R.id.title);
        year = findViewById(R.id.year);
        videoList = findViewById(R.id.list_video);
        MovieApiService Api = RetrofitClient.getInstance().getApiService();
        Call<MovieDetailModel> call = Api.getMovieById("rMlXfo9TGonjR8NuwNGE");
        //Toast.makeText(this, "vào trang", Toast.LENGTH_SHORT).show();//truyền id thông qua intent
        call.enqueue(new Callback<MovieDetailModel>() {
            @Override
            public void onResponse(Call<MovieDetailModel> call, Response<MovieDetailModel> response) {
                if (response.isSuccessful()) {
                    if(response.code()==200){
                        try {
                            movieDetails = response.body();
                        }catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MovieDetailsActivity.this, "Lỗi xử lý dữ liệu phản hồi", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MovieDetailsActivity.this, "Lỗi Sent DTO", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_ERROR", "Code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<MovieDetailModel> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage());
            }
        });

        if(movieDetails!= null){
            Toast.makeText(this, "Khác null", Toast.LENGTH_SHORT).show();
            detaildes = movieDetails.getDescription();
            if(detaildes.length()>50) {
                des = detaildes.substring(0, 50) + "...";
                SpannableString spannableString = new SpannableString(des + clickableText);
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        description.setText(detaildes);
                        Toast.makeText(widget.getContext(), "Learn more clicked!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.CYAN); // Màu của chữ "Learn more"
                        ds.setUnderlineText(false); // Bỏ gạch chân nếu muốn
                    }
                };

                spannableString.setSpan(clickableSpan, detaildes.length(), detaildes.length() + clickableText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                description.setText(spannableString);
                description.setMovementMethod(LinkMovementMethod.getInstance());
            }else {
                description.setText(detaildes);
            }
            for(ActorModel actorModel : movieDetails.getActors()){
                actor.setText(actor.getText()+ actorModel.getName()+ ", ");
            }
            for(DirectorModel directorModel: movieDetails.getDirectors()){
                directors.setText(directors.getText()+ directorModel.getName()+", ");
            }
            title.setText(movieDetails.getTitle());
            // Chuyển Timestamp thành chuỗi ngày tháng năm
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = sdf.format(movieDetails.getCreated_at());
            year.setText(formattedDate);
            listVideoAdapter = new ListVideoAdapter(this,movieDetails.getVideos());
            videoList.setAdapter(listVideoAdapter);
        }

    }
}
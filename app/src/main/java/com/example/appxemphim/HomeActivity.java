package com.example.appxemphim;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemphim.UI.MovieAdapter;
import com.example.appxemphim.UI.MovieUIModel;
import com.example.appxemphim.UI.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerTopRated;
    private MovieAdapter movieAdapter;
    private List<MovieUIModel> movieList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        recyclerTopRated=findViewById(R.id.recyclerViewTopRated);
        movieList=new ArrayList<>();

        movieList.add(new MovieUIModel(1, "Dune: Part Two", "https://image.tmdb.org/t/p/w500/dune.jpg", "8.9","2020    3h00"));
        movieList.add(new MovieUIModel(2, "Oppenheimer", "https://image.tmdb.org/t/p/w500/oppenheimer.jpg", "9.0","2020    3h00"));
        movieList.add(new MovieUIModel(3, "Spider-Man: No Way Home", "https://image.tmdb.org/t/p/w500/spiderman.jpg", "8.7","2020    3h00"));

        movieAdapter=new MovieAdapter(movieList,this);
        recyclerTopRated.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerTopRated.setAdapter(movieAdapter);

        int spacingInPixels=getResources().getDimensionPixelOffset(R.dimen.item_spacing);
        recyclerTopRated.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
    }
}
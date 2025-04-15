package com.example.appxemphim.UI.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appxemphim.Model.MovieUIModel;
import com.example.appxemphim.ViewModel.MovieViewModel;
import com.example.appxemphim.UI.Adapter.SearchAdapter;
import com.example.appxemphim.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding; // Biến binding
    private SearchAdapter adapter;
    private MovieViewModel movieViewModel;
    private List<MovieUIModel> allData;

    //for logic search
    private ExecutorService executorService;
    private Handler handler;
    private static final long DEBOUNCE_DELAY = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Khởi tạo View Binding
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Cài đặt RecyclerView
        setUpRecycleView();

        // Khởi tạo
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        executorService= Executors.newSingleThreadExecutor();
        handler=new Handler(Looper.getMainLooper());


        // Tải dữ liệu
        observe();

        // Xử lý sự kiện tìm kiếm
        searchListener();
    }

    private void setUpRecycleView(){
        allData=new ArrayList<>();
        binding.searchResultsRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter=new SearchAdapter();
        binding.searchResultsRecycler.setAdapter(adapter);
        adapter.setOnMovieClickListener(movieUIModel -> {
            MovieDetailActivity.showMovieDetailActivity(this,movieUIModel);
        });

    }
    private void filterResults(String query) {
        binding.progressBar.setVisibility(View.VISIBLE);
        executorService.execute(() -> {
            List<MovieUIModel> filteredList = new ArrayList<>();
            String lowerQuery = query.toLowerCase();

            if (query.trim().isEmpty()) {
                filteredList.addAll(allData); // Nếu query rỗng, hiển thị toàn bộ
            } else {
                for (MovieUIModel item : allData) {
                    if (item.getTitle().toLowerCase().contains(lowerQuery)) {
                        filteredList.add(item);
                    }
                }
            }

            // Cập nhật UI trên main thread
            runOnUiThread(() -> {
                adapter.submitList(filteredList);
                binding.progressBar.setVisibility(View.GONE);
                if (filteredList.isEmpty() && !query.trim().isEmpty()) {
                    Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    private void searchListener(){
        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterResults(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(()->filterResults(newText),DEBOUNCE_DELAY);
                return true;
            }
        });
    }
    private void observe(){
            movieViewModel.loadAllMovies();

        movieViewModel.getAllMovies().observe(this, resource -> {
            if (resource == null) return;
            switch (resource.getStatus()) {
                case LOADING:
                    // Hiển thị loading indicator nếu cần
                    break;
                case SUCCESS:
                    List<MovieUIModel> data = resource.getData();
                    if (data != null) {
                        allData.clear(); // Xóa dữ liệu cũ trước khi thêm mới
                        allData.addAll(data);
                        adapter.submitList(data);
                    }
                    break;
                case ERROR:
                    Toast.makeText(this, "Error: " + resource.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }


    public static void showSearchActivity(Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
        handler.removeCallbacksAndMessages(null);
        binding=null;
    }
}

package com.example.appxemphim.UI.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appxemphim.UI.Adapter.PopularAdapter;
import com.example.appxemphim.UI.Adapter.ViewpagerAdapter;
import com.example.appxemphim.R;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerPopular, recyclerOnly, recyclerRetro;
    LinearLayoutManager layoutManagerPopular, layoutManagerOnly, layoutManagerRetro;
    List<Integer> popular, only, retro;
    PopularAdapter popularAdapter, adapterRetro, adapterOnly;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager2 = findViewById(R.id.view_pager);
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(this);
        viewPager2.setAdapter(viewpagerAdapter);

    }
}

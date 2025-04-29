package com.example.appxemphim.UI.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appxemphim.UI.Adapter.PopularAdapter;
import com.example.appxemphim.UI.Adapter.ViewpagerAdapter;
import com.example.appxemphim.R;
import com.example.appxemphim.databinding.ActivityHomeBinding;
import com.example.appxemphim.databinding.ActivitySearchBinding;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPager.setAdapter(new ViewpagerAdapter(this));

        setContentView(R.layout.activity_home);
        viewPager2 = findViewById(R.id.view_pager);
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(this);
        viewPager2.setAdapter(viewpagerAdapter);
    }
}

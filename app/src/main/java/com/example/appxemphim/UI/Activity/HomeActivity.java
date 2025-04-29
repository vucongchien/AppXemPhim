package com.example.appxemphim.UI.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.appxemphim.UI.Adapter.PopularAdapter;
import com.example.appxemphim.UI.Adapter.ViewpagerAdapter;
import com.example.appxemphim.R;
import com.example.appxemphim.Utilities.NotificationPermissionHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerPopular, recyclerOnly, recyclerRetro;
    LinearLayoutManager layoutManagerPopular, layoutManagerOnly, layoutManagerRetro;
    List<Integer> popular, only, retro;
    PopularAdapter popularAdapter, adapterRetro, adapterOnly;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    private ActivityResultLauncher<String> notificationPermissionLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Đăng ký launcher
        notificationPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        // ✅ Người dùng cho phép
                    } else {
                        // ❌ Người dùng từ chối
                    }
                }
        );

        // Gọi xin quyền
        NotificationPermissionHelper.requestIfNeeded(this, notificationPermissionLauncher);

        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.nav_list).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.nav_profile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.nav_list:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.nav_profile:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }

}

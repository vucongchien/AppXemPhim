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
import com.example.appxemphim.databinding.ActivityHomeBinding;
import com.example.appxemphim.databinding.ActivitySearchBinding;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;

    private ActivityResultLauncher<String> notificationPermissionLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setNotificationPermissionLauncher();
        initView();


    }

    private void setNotificationPermissionLauncher(){
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

    }
    private void initView(){
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setAdapter(adapter);

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        binding.bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case 1:
                        binding.bottomNavigationView.getMenu().findItem(R.id.nav_list).setChecked(true);
                        break;
                    case 2:
                        binding.bottomNavigationView.getMenu().findItem(R.id.nav_profile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        binding.viewPager.setCurrentItem(0);
                        break;
                    case R.id.nav_list:
                        binding.viewPager.setCurrentItem(1);
                        break;
                    case R.id.nav_profile:
                        binding.viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }

}

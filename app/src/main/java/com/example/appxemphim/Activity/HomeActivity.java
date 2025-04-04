package com.example.appxemphim.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appxemphim.Fragment.DashboardFragment;
import com.example.appxemphim.Fragment.ProfileFragment;
import com.example.appxemphim.R;
import com.example.appxemphim.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new DashboardFragment());

        binding.bottomNavbar.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Xử lý khi chọn Home
                replaceFragment(new DashboardFragment());
                return true;
            } else if (itemId == R.id.nav_list) {
                // Xử lý khi chọn My List
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Xử lý khi chọn Profile
                replaceFragment(new ProfileFragment());
                return true;
            }
            return false;
        });




    }



    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }
}
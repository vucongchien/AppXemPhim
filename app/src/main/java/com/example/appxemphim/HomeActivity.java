package com.example.appxemphim;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appxemphim.HOME_FOLDER.Fragment.DashboardFragment;
import com.example.appxemphim.HOME_FOLDER.Fragment.ProfileFragment;
import com.example.appxemphim.HOME_FOLDER.MovieAdapter;
import com.example.appxemphim.HOME_FOLDER.MovieUIModel;
import com.example.appxemphim.HOME_FOLDER.MovieViewModel;
import com.example.appxemphim.HOME_FOLDER.SpaceItemDecoration;
import com.example.appxemphim.databinding.ActivityHomeBinding;

import java.util.ArrayList;

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
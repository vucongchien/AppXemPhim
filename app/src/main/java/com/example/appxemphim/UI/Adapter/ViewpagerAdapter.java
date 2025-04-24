package com.example.appxemphim.UI.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appxemphim.UI.Fragment.HomeFragment;

public class ViewpagerAdapter extends FragmentStateAdapter {

    public ViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment(); // Fragment bạn đã tạo
            // case 1:
            //     return new AnotherFragment();
            // case 2:
            //     return new YetAnotherFragment();
            default:
                return new HomeFragment(); // fallback
        }
    }

    @Override
    public int getItemCount() {
        return 1; // Số lượng fragment bạn có
    }
}

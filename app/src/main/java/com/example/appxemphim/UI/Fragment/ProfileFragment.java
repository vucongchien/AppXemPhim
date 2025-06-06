package com.example.appxemphim.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appxemphim.LoginRegister.LoginActivity;
import com.example.appxemphim.Model.ProfileOption;
import com.example.appxemphim.R;
import com.example.appxemphim.UI.Activity.EditProfileActivity;
import com.example.appxemphim.UI.Adapter.HistoryAdapter;
import com.example.appxemphim.UI.Adapter.ProfileOptionAdapter;
import com.example.appxemphim.ViewModel.HistoryViewModel;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private static final int EDIT_PROFILE = 0;
    private static final int NOTIFICATION = 1;
    private static final int HELP = 2;
    private static final int LOGOUT = 3;

    ListView listView;
    ArrayList<ProfileOption> optionArrayList;

    private RecyclerView recyclerViewHistory;
    private HistoryAdapter historyAdapter;
    private HistoryViewModel historyViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        listView = view.findViewById(R.id.listViewProfile);
        optionArrayList = new ArrayList<>();

        optionArrayList.add(new ProfileOption(R.drawable.iconedit, "Edit Profile"));
        optionArrayList.add(new ProfileOption(R.drawable.iconnotifi, "Notification"));
        optionArrayList.add(new ProfileOption(R.drawable.iconhelp, "Help"));
        optionArrayList.add(new ProfileOption(R.drawable.iconlogout, "Log Out"));

        ProfileOptionAdapter adapter = new ProfileOptionAdapter(
                getContext(),
                R.layout.item_profile_option,
                optionArrayList
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            switch (position) {
                case EDIT_PROFILE:
                    openEditProfile();
                    break;
                case NOTIFICATION:
                    openNotificationSettings();
                    break;
                case HELP:
                    openHelpCenter();
                    break;
                case LOGOUT:
                    logout();
                    break;
            }
        });
        // --- Phần mới thêm cho History ---

        recyclerViewHistory = view.findViewById(R.id.recyclerViewHistory);
        historyAdapter = new HistoryAdapter();
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHistory.setAdapter(historyAdapter);

        // Lấy ViewModel dùng chung với Activity (hoặc tạo riêng)
        historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);

        observeHistoryData();

        // Load dữ liệu lịch sử phim
        historyViewModel.loadHistoryWithMovies();


        return view;
    }
    private void observeHistoryData() {
        historyViewModel.historyWithMovieLiveData.observe(getViewLifecycleOwner(), result -> {
            switch (result.getStatus()) {
                case SUCCESS:
                    historyAdapter.setData(result.getData());
                    break;
                case ERROR:
                    Toast.makeText(getContext(), "Lỗi: " + result.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    // Nếu cần có thể show loading (hoặc ProgressBar)
                    break;
            }
        });
    }


    private void openEditProfile() {

        startActivity(new Intent(getActivity(), EditProfileActivity.class));
    }

    private void openNotificationSettings() {
        Toast.makeText(getContext(), "Mở cài đặt thông báo", Toast.LENGTH_SHORT).show();
    }

    private void openHelpCenter() {
        Toast.makeText(getContext(), "Mở trung tâm trợ giúp", Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        Toast.makeText(getContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        requireActivity().finish();
    }
}

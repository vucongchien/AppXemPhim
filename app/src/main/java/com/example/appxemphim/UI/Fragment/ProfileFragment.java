package com.example.appxemphim.UI.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appxemphim.LoginRegister.LoginActivity;
import com.example.appxemphim.Model.Profile;
import com.example.appxemphim.Model.ProfileOption;
import com.example.appxemphim.R;
import com.example.appxemphim.Repository.ProfileRepository;
import com.example.appxemphim.UI.Activity.EditProfileActivity;
import com.example.appxemphim.UI.Activity.MovieDetailsActivity;
import com.example.appxemphim.UI.Adapter.HistoryAdapter;
import com.example.appxemphim.UI.Adapter.ProfileOptionAdapter;
import com.example.appxemphim.UI.Utils.Resource;
import com.example.appxemphim.ViewModel.HistoryViewModel;
import com.example.appxemphim.ViewModel.ProfileViewModel;
import com.example.appxemphim.databinding.FragmentProfileBinding;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private static final int EDIT_PROFILE = 0;
    private static final int NOTIFICATION = 1;
    private static final int HELP = 2;
    private static final int LOGOUT = 3;

    private FragmentProfileBinding binding;
    private ArrayList<ProfileOption> optionArrayList;

    private ProfileViewModel profileViewModel;
    private HistoryAdapter historyAdapter;
    private HistoryViewModel historyViewModel;

    private ProfileRepository profileRepository;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

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

        binding.listViewProfile.setAdapter(adapter);
        // Khởi tạo profileViewModel với Factory
        profileViewModel = new ViewModelProvider(
                requireActivity(),
                new ProfileViewModelFactory(requireContext()) // <-- Sử dụng Factory
        ).get(ProfileViewModel.class);
        setData();

        binding.listViewProfile.setOnItemClickListener((parent, view1, position, id) -> {
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

        // --- History ---
        historyAdapter = new HistoryAdapter();
        historyAdapter.setOnItemClickListener(item -> {
            // Chuyển sang màn hình chi tiết phim
            Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
            intent.putExtra("movie_id", item.getMovie().getMovie_Id()); // Giả sử movie có id
            startActivity(intent);
        });
        binding.recyclerViewHistory.setAdapter(historyAdapter);
        binding.recyclerViewHistory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewHistory.setAdapter(historyAdapter);

        historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);
        observeHistoryData();
        historyViewModel.loadHistoryWithMovies();

        return binding.getRoot();
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
                    // Optionally show loading
                    break;
            }
        });
    }

    private void openEditProfile() {startActivity(new Intent(getActivity(), EditProfileActivity.class));
    }

    private void openNotificationSettings() {
        Toast.makeText(getContext(), "Mở cài đặt thông báo", Toast.LENGTH_SHORT).show();
    }

    private void openHelpCenter() {
        Toast.makeText(getContext(), "Mở trung tâm trợ giúp", Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        Toast.makeText(getContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPref = requireActivity().getSharedPreferences("LocalStore", MODE_PRIVATE);
        sharedPref.edit().clear().apply();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        requireActivity().finish();
    }

    private void setData() {
        profileViewModel.getprofile();
        profileViewModel.getDataReslt.observe(requireActivity(), resource -> {
            if (resource != null) {
                switch (resource.getStatus()) {
                    case LOADING:
                        break;
                    case SUCCESS:
                        Profile result = resource.getData();
                        if (result != null) {
                            Glide.with(requireContext())
                                    .load(result.getAvatar())
                                    .into(binding.imageAvartar);
                            binding.textViewProfile.setText(result.getName());
                        }
                        break;
                    case ERROR:
                        Toast.makeText(requireContext(), "Có lỗi xảy ra: " + resource.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
    public class ProfileViewModelFactory implements ViewModelProvider.Factory {
        private final Context context;

        public ProfileViewModelFactory(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
                return (T) new ProfileViewModel(context);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
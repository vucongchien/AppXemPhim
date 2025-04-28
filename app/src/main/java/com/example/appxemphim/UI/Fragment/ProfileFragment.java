package com.example.appxemphim.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appxemphim.LoginRegister.LoginActivity;
import com.example.appxemphim.LoginRegister.RegisterActivity;
import com.example.appxemphim.Model.ProfileOption;
import com.example.appxemphim.R;
import com.example.appxemphim.UI.Activity.EditProfileActivity;
import com.example.appxemphim.UI.Adapter.ProfileOptionAdapter;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    ListView listView;

    ArrayList<ProfileOption> optionArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        listView = view.findViewById(R.id.listViewProfile);  // Bạn cần set ID cho ListView
        optionArrayList = new ArrayList<ProfileOption>();

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

        listView.setOnItemClickListener(((parent, view1, position, id) -> {
            ProfileOption selectedOption = (ProfileOption) parent.getItemAtPosition(position);
            switch (selectedOption.getTitle()) {
                case "Edit Profile":
                    // TODO: Mở màn hình chỉnh sửa profile
                    openEditProfile();
                    break;

                case "Notification":
                    // TODO: Mở cài đặt thông báo
                    openNotificationSettings();
                    break;

                case "Help":
                    // TODO: Mở màn hình trợ giúp
                    openHelpCenter();
                    break;

                case "Log Out":
                    // TODO: Đăng xuất
                    logout();
                    break;
            }
        }));
        return view;
    }
    private void openEditProfile() {
        startActivity(new Intent(getActivity(), EditProfileActivity.class));
    }

    private void openNotificationSettings() {
        // Ví dụ hiện Toast tạm
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
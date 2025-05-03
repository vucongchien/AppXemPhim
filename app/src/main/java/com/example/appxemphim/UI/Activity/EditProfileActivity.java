package com.example.appxemphim.UI.Activity;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.appxemphim.Model.Profile;
import com.example.appxemphim.Model.ReviewModel;
import com.example.appxemphim.R;
import com.example.appxemphim.Repository.ProfileRepository;
import com.example.appxemphim.Utilities.FirebaseUtils;
import com.example.appxemphim.ViewModel.ProfileViewModel;
import com.example.appxemphim.databinding.ActivityEditProfileBinding;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;
    private static final  int IMAGE_REQUEST=2;
    private Uri imageUri = null;
    private ProfileViewModel profileViewModel;
    private ProfileRepository profileRepository;
    private android.app.ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        profileViewModel = new ProfileViewModel(EditProfileActivity.this);
        profileRepository = new ProfileRepository(EditProfileActivity.this);
        setContentView(binding.getRoot());
        progressDialog = new android.app.ProgressDialog(this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setCancelable(true);
        setData();
        binding.imageAvartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editProfileName.getText().toString().trim();
                String email = binding.editProfileEmail.getText().toString().trim();
                profileRepository.updateProfile(name, email, imageUri,
                        () -> { // onSuccess
                            setData();
                        },
                        () -> { // onFailure
                            progressDialog.dismiss();
                        }
                );
            }
        });
    }
    private void openImage(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            binding.imageAvartar.setImageURI(imageUri);
        }
    }

    private  void setData(){
        profileViewModel.getprofile("1");//quy ước của tay trên server: 1 là uid lấy từ token người dùng
        profileViewModel.getDataReslt.observe(EditProfileActivity.this, resource->{
            if (resource != null) {
                switch (resource.getStatus()) {
                    case LOADING:
                        progressDialog.show();
                        break;
                    case SUCCESS:
                        progressDialog.dismiss();
                        Profile result = resource.getData();
                        if (result != null) {
                            Glide.with(EditProfileActivity.this)
                                    .load(result.getAvatar())
                                    .into(binding.imageAvartar);
                            binding.editProfileEmail.setText(result.getEmail());
                            binding.editProfileName.setText(result.getName());
                        }
                        break;
                    case ERROR:
                        progressDialog.dismiss();
                        Toast.makeText(EditProfileActivity.this, "Có lỗi xảy ra: " + resource.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


}
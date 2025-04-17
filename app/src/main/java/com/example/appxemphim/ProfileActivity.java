package com.example.appxemphim;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.appxemphim.Utilities.FirebaseUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends MainActivity {
    private static final  int IMAGE_REQUEST=2;
    private Uri imageUri;
    private  FirebaseAuth mAuth;
    private  FirebaseUser user;

    ImageView avata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        avata = findViewById(R.id.imageView);
        mAuth= FirebaseUtils.getAuth();
        user= FirebaseUtils.getUser();
        updateAvata(user);

    }

    private void updateAvata(FirebaseUser user){
        if(user.getPhotoUrl() != null){
            Glide.with(this).load(user.getPhotoUrl()).into(avata);
        }
    }
    public void changeAvata(View view) {
        openImage();
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
            uploadImage();
        }

    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("updating");
        pd.show();
        if (imageUri != null) {
            String uidUser = user.getUid();
            StorageReference fileRef = FirebaseStorage.getInstance().getReference()
                    .child("avatars/" + uidUser + "_" + System.currentTimeMillis() + getFileExtension(imageUri));

            fileRef.putFile(imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Cập nhật ảnh đại diện cho Firebase Auth
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(uri)
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(this, "Cập nhật avatar thành công!", Toast.LENGTH_SHORT).show();
                                        Log.d("UserProfile", "Cập nhật avatar thành công!");
                                        Glide.with(ProfileActivity.this).load(uri).into(avata);
                                        updateAvata(user);
                                    } else {
                                        Toast.makeText(this, "Cập nhật avatar thất bại.", Toast.LENGTH_SHORT).show();
                                        Log.e("UserProfile", "Cập nhật avatar thất bại.", task1.getException());
                                    }
                                    pd.dismiss();
                                });
                    });
                } else {
                    Toast.makeText(this, "Tải ảnh lên Firebase thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("UploadImage", "Tải ảnh lên Firebase thất bại", task.getException());
                    pd.dismiss();
                }
            });
        }
    }


}
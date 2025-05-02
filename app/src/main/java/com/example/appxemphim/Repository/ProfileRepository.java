package com.example.appxemphim.Repository;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.DTO.EmailDTO;
import com.example.appxemphim.Model.Profile;
import com.example.appxemphim.Model.ReviewModel;
import com.example.appxemphim.Network.ProfileService;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.UI.Utils.Resource;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {
    private final ProfileService profileService;
    private final Context context;

    public ProfileRepository(Context context) {
        this.context = context;
        SharedPreferences sharedPref = context.getSharedPreferences("LocalStore", MODE_PRIVATE);
        String token = sharedPref.getString("Token", "");
        this.profileService = RetrofitInstance.createService(ProfileService.class, token);
    }

    public void updateProfile(String nameStr, String gmailStr, @Nullable Uri fileUri, Runnable onSuccess, Runnable onFailure) {
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), nameStr);
        RequestBody gmail = RequestBody.create(MediaType.parse("text/plain"), gmailStr);

        MultipartBody.Part filePart = null;
        if (fileUri != null) {
            File file = uriToFile(fileUri);
            if (file != null) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                filePart = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
            }
        }

        profileService.updateProfile(name, gmail, filePart).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    if (onSuccess != null) onSuccess.run();
                } else {
                    Toast.makeText(context, "Lỗi cập nhật: " + response.code(), Toast.LENGTH_SHORT).show();
                    if (onFailure != null) onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                if (onFailure != null) onFailure.run();
            }
        });
    }

    private File uriToFile(Uri uri) {
        try {
            File file = new File(context.getCacheDir(), getFileName(uri));
            try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
                 OutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[4096];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    public void getProfile(String uid,MutableLiveData<Resource<Profile>> liveData){
        liveData.setValue(Resource.loading());
        profileService.getProfile(uid).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()&& response.body() != null) {
                    Profile profile = response.body();
                    liveData.setValue(Resource.success(profile));
                } else {
                    liveData.setValue(Resource.error("Lỗi cập nhật: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                liveData.setValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }
}

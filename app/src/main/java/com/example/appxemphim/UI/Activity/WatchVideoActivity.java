package com.example.appxemphim.UI.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;

import com.example.appxemphim.Network.ApiLoginRegisterService;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.databinding.ActivityWatchVideoBinding;

import com.example.appxemphim.Model.VideoModel;
import com.example.appxemphim.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;

@OptIn(markerClass = UnstableApi.class)
public class WatchVideoActivity extends AppCompatActivity {
    private ActivityWatchVideoBinding binding;
    private ExoPlayer exoPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWatchVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        VideoModel video = (VideoModel) getIntent().getSerializableExtra("video_data");
        if (video != null) {
            binding.namevideo.setText(video.getName());
            ApiLoginRegisterService apiService = RetrofitInstance.getApiService();
            Call<ResponseBody> call = apiService.getGoogleDriveDownloadUrl(video.getVideo_url());
            call.enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        try {
                            String json = response.body().string();
                            exoPlayer = new ExoPlayer.Builder(WatchVideoActivity.this).build();
                            binding.thumbnail.setPlayer(exoPlayer);

                            // Tạo MediaItem từ downloadUrl
                            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(json));

                            // Tạo source và set cho player
                            ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(
                                    new DefaultHttpDataSource.Factory()
                            ).createMediaSource(mediaItem);

                            exoPlayer.setMediaSource(mediaSource);
                            exoPlayer.prepare();
                            exoPlayer.play();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("API_ERROR", "Code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("API_FAILURE", t.getMessage());
                }
            });
            binding.thumbnail.setControllerVisibilityListener(
                    (androidx.media3.ui.PlayerView.ControllerVisibilityListener) visibility -> {
                        if (visibility == View.VISIBLE) {
                            binding.namevideo.setVisibility(View.VISIBLE);
                            binding.btnClose.setVisibility(View.VISIBLE);
                        } else {
                            binding.namevideo.setVisibility(View.GONE);
                            binding.btnClose.setVisibility(View.GONE);
                        }
                    }
            );

        }
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exoPlayer != null) {
                    exoPlayer.release();
                    exoPlayer = null;
                }
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
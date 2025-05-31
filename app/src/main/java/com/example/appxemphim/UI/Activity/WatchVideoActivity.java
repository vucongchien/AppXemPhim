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
import com.example.appxemphim.UI.Adapter.EpisodeAdapter;
import com.example.appxemphim.UI.Utils.CustomMedia3Controller;
import com.example.appxemphim.databinding.ActivityWatchVideoBinding;

import com.example.appxemphim.Model.VideoModel;
import com.example.appxemphim.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

@OptIn(markerClass = UnstableApi.class)
public class WatchVideoActivity extends AppCompatActivity {
    private ActivityWatchVideoBinding binding;
    private ExoPlayer exoPlayer;
    private VideoModel video;
    private ArrayList<VideoModel> videoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWatchVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        video = (VideoModel) getIntent().getSerializableExtra("video_data");
        videoList = (ArrayList<VideoModel>) getIntent().getSerializableExtra("list_ep");
        Log.d("DEBUG", "videoList size = " + (videoList == null ? "null" : videoList.size()));

        exoPlayer = new ExoPlayer.Builder(this).build();
        binding.thumbnail.setPlayer(exoPlayer);
        playEpisode(video);



    }

    private void playEpisode(VideoModel episode) {
        if (exoPlayer != null) {
            exoPlayer.stop(); // Dừng video hiện tại
            exoPlayer.clearMediaItems();
        }

        ApiLoginRegisterService apiService = RetrofitInstance.getApiService();
        Call<ResponseBody> call = apiService.getGoogleDriveDownloadUrl(episode.getVideo_url());

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String json = response.body().string();
                        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(json));
                        ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(
                                new DefaultHttpDataSource.Factory()
                        ).createMediaSource(mediaItem);

                        exoPlayer.setMediaSource(mediaSource);
                        exoPlayer.prepare();
                        exoPlayer.play();

                        new CustomMedia3Controller(WatchVideoActivity.this, binding.thumbnail, episode.getName(), videoList, () -> finish(), new EpisodeAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(VideoModel selectedEpisode) {
                                playEpisode(selectedEpisode);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage());
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
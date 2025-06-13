package com.example.appxemphim.UI.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;

import com.example.appxemphim.Model.HistoryModel;
import com.example.appxemphim.Model.VideoModel;
import com.example.appxemphim.Network.ApiLoginRegisterService;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.R;
import com.example.appxemphim.Request.HistoryRequest;
import com.example.appxemphim.UI.Utils.CustomMedia3Controller;
import com.example.appxemphim.UI.Utils.Resource;
import com.example.appxemphim.ViewModel.HistoryViewModel;
import com.example.appxemphim.databinding.ActivityWatchVideoBinding;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@androidx.media3.common.util.UnstableApi
public class WatchVideoActivity extends AppCompatActivity {

    private ActivityWatchVideoBinding binding;
    private ExoPlayer exoPlayer;
    private HistoryViewModel historyViewModel;
    private VideoModel video;
    private Double resumePositionPercentage = null;
    private ArrayList<VideoModel> videoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWatchVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        // Retrieve video data from intent
        video = (VideoModel) getIntent().getSerializableExtra("video_data");
        if (video == null) {
            finish();
            return;
        }
        videoList = (ArrayList<VideoModel>) getIntent().getSerializableExtra("list_ep");

        // Initialize ViewModel
        historyViewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
        ).get(HistoryViewModel.class);

        // Observe history for the current video
        historyViewModel.getHistoryByVideoId(video.getVideo_id())
                .observe(this, resource -> {
                    if (resource.getStatus() == Resource.Status.SUCCESS && !resource.getData().isEmpty()) {
                        HistoryModel history = resource.getData().get(0);
                        resumePositionPercentage = history.getPerson_view();
                        Log.d("DEBUG", "Resume position: " + resumePositionPercentage + "%");
                    }
                });

        // Fetch video URL and set up player
        fetchVideoUrlAndSetupPlayer(video.getVideo_url());
    }

    private void fetchVideoUrlAndSetupPlayer(String videoUrl) {
        ApiLoginRegisterService apiService = RetrofitInstance.getApiService();
        Call<ResponseBody> call = apiService.getGoogleDriveDownloadUrl(videoUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String json = response.body().string();
                        Uri videoUri = Uri.parse(json);
                        setupPlayer(videoUri);
                    } catch (IOException e) {
                        Log.e("API_ERROR", "Failed to parse response: " + e.getMessage());
                        Toast.makeText(WatchVideoActivity.this, "Không thể tải video", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_ERROR", "Response code: " + response.code());
                    Toast.makeText(WatchVideoActivity.this, "Lỗi tải video", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API_FAILURE", "Network error: " + t.getMessage());
                Toast.makeText(WatchVideoActivity.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupPlayer(Uri videoUri) {
        // Initialize ExoPlayer
        exoPlayer = new ExoPlayer.Builder(this).build();
        binding.thumbnail.setPlayer(exoPlayer);

        // Set up media source
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(
                new DefaultHttpDataSource.Factory()
        ).createMediaSource(mediaItem);

        exoPlayer.setMediaSource(mediaSource);
        exoPlayer.prepare();

        // Handle resume position
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_READY) {
                    if (resumePositionPercentage != null && resumePositionPercentage > 5 && resumePositionPercentage < 95) {
                        long resumePosition = (long) (exoPlayer.getDuration() * resumePositionPercentage / 100);
                        exoPlayer.seekTo(resumePosition);
                        String timeFormatted = formatTime(resumePosition);
                        Toast.makeText(WatchVideoActivity.this,
                                "Đang tiếp tục từ " + timeFormatted,
                                Toast.LENGTH_SHORT).show();
                    }
                    exoPlayer.removeListener(this); // Remove listener after use
                }
            }
        });

        // Set up custom controller for initial video
        new CustomMedia3Controller(
                this,
                binding.thumbnail,
                video.getName(),
                videoList,
                this::finish,
                selectedEpisode -> playEpisode(selectedEpisode)
        );

        exoPlayer.setPlayWhenReady(true); // Start playback automatically
    }

    private void playEpisode(VideoModel episode) {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.clearMediaItems();
        }
        fetchVideoUrlAndSetupPlayer(episode.getVideo_url());
        video = episode; // Update current video for history tracking
    }

    private String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            long currentPosition = exoPlayer.getCurrentPosition();
            long duration = exoPlayer.getDuration();

            if (duration > 0 && currentPosition >= 0) {
                double percentWatched = (currentPosition * 100.0) / duration;
                Log.d("DEBUG", "Video ID: " + video.getVideo_id() + ", Percent watched: " + percentWatched);
                HistoryRequest request = new HistoryRequest(video.getVideo_id(), percentWatched);
                historyViewModel.addHistory(request);
            }
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
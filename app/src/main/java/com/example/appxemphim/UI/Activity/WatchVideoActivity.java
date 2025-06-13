package com.example.appxemphim.UI.Activity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.view.WindowInsets;
import android.view.WindowInsetsController;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;

import com.example.appxemphim.Network.ApiLoginRegisterService;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.R;
import com.example.appxemphim.Request.HistoryRequest;
import com.example.appxemphim.UI.Utils.CustomMedia3Controller;
import com.example.appxemphim.ViewModel.HistoryViewModel;
import com.example.appxemphim.databinding.ActivityWatchVideoBinding;
import com.example.appxemphim.Model.VideoModel;
import com.example.appxemphim.UI.Utils.Resource;
import com.example.appxemphim.Model.HistoryModel;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@OptIn(markerClass = androidx.media3.common.util.UnstableApi.class)
public class WatchVideoActivity extends AppCompatActivity {

    private ActivityWatchVideoBinding binding;
    private ExoPlayer exoPlayer;
    private HistoryViewModel historyViewModel;
    private VideoModel video;
    private Double resumePositionPercentage = null;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWatchVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        // Lấy video từ intent
        video = (VideoModel) getIntent().getSerializableExtra("video_data");
        if (video == null) {
            finish();
            return;
        }

        // Khởi tạo ViewModel
        historyViewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
        ).get(HistoryViewModel.class);

        // Lấy lịch sử xem cho video này
        historyViewModel.getHistoryByVideoId(video.getVideo_id())
                .observe(this, resource -> {
                    if (resource.getStatus() == Resource.Status.SUCCESS && !resource.getData().isEmpty()) {
                        HistoryModel history = resource.getData().get(0);
                        resumePositionPercentage = history.getPerson_view();
                        Log.d("DEBUG", "Resume position: " + resumePositionPercentage + "%");
                        // Nếu đã có lịch sử, sẽ tự động seek khi player sẵn sàng
                    }
                });

        // Gọi API lấy link phát video
        ApiLoginRegisterService apiService = RetrofitInstance.getApiService();
        Call<ResponseBody> call = apiService.getGoogleDriveDownloadUrl(video.getVideo_url());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String json = response.body().string();
                        setupPlayer(Uri.parse(json));
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
    }

    private void setupPlayer(Uri videoUri) {
        exoPlayer = new ExoPlayer.Builder(this).build();
        binding.thumbnail.setPlayer(exoPlayer);

        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        ProgressiveMediaSource.Factory mediaSourceFactory = new ProgressiveMediaSource.Factory(
                new DefaultHttpDataSource.Factory()
        );
        ProgressiveMediaSource mediaSource = mediaSourceFactory.createMediaSource(mediaItem);

        exoPlayer.setMediaSource(mediaSource);
        exoPlayer.prepare();

        // Thêm listener để tự động seek khi player sẵn sàng
        exoPlayer.addListener(new Player.Listener() {
            boolean isFirstReady = true;

            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_READY && isFirstReady) {
                    isFirstReady = false;
                    // Nếu có lịch sử, seek đến vị trí đã lưu
                    if (resumePositionPercentage != null && resumePositionPercentage > 5 && resumePositionPercentage < 95) {
                        handler.postDelayed(() -> {
                            if (exoPlayer.getDuration() > 0) {
                                long resumePosition = (long) (exoPlayer.getDuration() * resumePositionPercentage / 100);
                                exoPlayer.seekTo(resumePosition);
                                long seconds = resumePosition / 1000;
                                long hours = seconds / 3600;
                                long minutes = (seconds % 3600) / 60;
                                long remainingSeconds = seconds % 60;

                                String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
                                Toast.makeText(WatchVideoActivity.this,
                                        "Đang tiếp tục từ " + timeFormatted,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, 300);
                    }
                }
            }
        });

        // Áp dụng custom controller
        new CustomMedia3Controller(this, binding.thumbnail, video.getName(), this::finish);

        exoPlayer.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            long currentPosition = exoPlayer.getCurrentPosition(); // millis
            long duration = exoPlayer.getDuration(); // millis

            if (duration > 0 && currentPosition >= 0) {
                double percentWatched = ((currentPosition * 100.0) / duration);
                Log.d("DEBUG", "Video ID: " + video.getVideo_id() + ", percentWatched: " + percentWatched);
                // Tạo request lưu lịch sử xem
                HistoryRequest request = new HistoryRequest(
                        video.getVideo_id(),
                        percentWatched
                );
                // Gửi về ViewModel để gọi API
                historyViewModel.addHistory(request);
            }
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}

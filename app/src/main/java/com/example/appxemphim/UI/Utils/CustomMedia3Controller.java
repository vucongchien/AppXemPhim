package com.example.appxemphim.UI.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.media3.common.Player;
import androidx.media3.ui.DefaultTimeBar;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemphim.MainActivity;
import com.example.appxemphim.R;
import com.example.appxemphim.UI.Adapter.EpisodeAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CustomMedia3Controller {

    private final Context context;
    private final PlayerView playerView;

    private final ImageView playPause;
    private final ImageView forward;
    private final ImageView backward;
    private final ImageView backBtn;
    private final TextView titleText;
    private final TextView positionText;
    private final TextView durationText;
    private final DefaultTimeBar timeBar;
    private final ImageView btnList;


    private boolean isPlaying = true;
    private Handler handler;

    public CustomMedia3Controller(Context context, PlayerView playerView, String videoTitle, Runnable onBackPressed) {
        this.context = context;
        this.playerView = playerView;
        playPause = playerView.findViewById(R.id.exo_play_pause);
        forward = playerView.findViewById(R.id.exo_forward);
        backward = playerView.findViewById(R.id.exo_backward);
        backBtn = playerView.findViewById(R.id.exo_back);
        titleText = playerView.findViewById(R.id.exo_title);
        positionText = playerView.findViewById(R.id.exo_position);
        durationText = playerView.findViewById(R.id.exo_duration);
        timeBar = playerView.findViewById(R.id.exo_progress_placeholder);
        btnList =playerView.findViewById(R.id.exo_settings_listview);

        titleText.setText(videoTitle);
        titleText.setVisibility(View.VISIBLE);

        backBtn.setOnClickListener(v -> {
            if (onBackPressed != null) onBackPressed.run();
        });

        setupControls();
    }

    private void setupControls() {
        Player player = playerView.getPlayer();
        if (player == null) return;

        playPause.setOnClickListener(v -> {
            if (player.isPlaying()) {
                player.pause();
                playPause.setImageResource(R.drawable.pause); // icon play
                isPlaying = false;
            } else {
                player.play();
                playPause.setImageResource(R.drawable.play); // icon pause
                isPlaying = true;
            }
        });

        forward.setOnClickListener(v -> player.seekForward());
        backward.setOnClickListener(v -> player.seekBack());



        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                updateTime(player);
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                updateTime(player);
            }

            @Override
            public void onEvents(@NonNull Player player, @NonNull Player.Events events) {
                updateTime(player);
            }
        });
        btnList.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(context);
            View view = LayoutInflater.from(context).inflate(R.layout.episode_list, null);

            RecyclerView recyclerView = view.findViewById(R.id.episode_recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            List<String> episodes = Arrays.asList("Tập 1", "Tập 2", "Tập 3", "Tập 4"); // Tùy danh sách bạn có

            EpisodeAdapter adapter = new EpisodeAdapter(context, episodes, episode -> {
                Toast.makeText(context, "Đã chọn: " + episode, Toast.LENGTH_SHORT).show();
                // Gọi hàm playEpisode(episode) nếu bạn có
                dialog.dismiss();
            });

            recyclerView.setAdapter(adapter);
            dialog.setContentView(view);
            dialog.show();
        });
        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(updateRunnable(player), 0);
    }

    private Runnable updateRunnable(Player player) {
        return new Runnable() {
            @Override
            public void run() {
                updateTime(player);
                handler.postDelayed(this, 1000);
            }
        };
    }

    private void updateTime(Player player) {
        if (player == null || positionText == null || durationText == null || timeBar == null) return;

        long positionMs = player.getCurrentPosition();
        long durationMs = player.getDuration();

        positionText.setText(formatTime(positionMs));
        durationText.setText(formatTime(durationMs));
        timeBar.setPosition(positionMs);
        timeBar.setDuration(durationMs);
    }

    private String formatTime(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    public void release() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}

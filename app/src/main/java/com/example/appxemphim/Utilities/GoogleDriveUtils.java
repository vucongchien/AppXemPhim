package com.example.appxemphim.Utilities;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleDriveUtils {
    private static final String API_KEY = "AIzaSyAbKpw36Qd4rpjPOQgIEl0AXCeo982Ljls";
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();// luồng sử lý công việt
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());// luồng chính nhân giá trị trả về

    // Interface để trả về dữ liệu
    public interface VideoDurationCallback {
        void onSuccess(long durationMillis);
        void onError(String errorMessage);
    }

    public static void getVideoDuration(Context context, String FILE_LINK, VideoDurationCallback callback) {
        executor.execute(() -> {
            String FILE_ID = extractFileId(FILE_LINK);
            if (FILE_ID == null) {
                mainHandler.post(() -> callback.onError("Không tìm thấy File ID!"));
                return;
            }

            try {
                String apiUrl = "https://www.googleapis.com/drive/v3/files/" + FILE_ID
                        + "?fields=videoMediaMetadata(durationMillis)&key=" + API_KEY;

                HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    mainHandler.post(() -> callback.onError("Lỗi API: " + responseCode));
                    return;
                }

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

                JSONObject jsonObject = new JSONObject(response.toString());
                if (jsonObject.has("videoMediaMetadata")) {
                    long durationMillis = jsonObject.getJSONObject("videoMediaMetadata").getLong("durationMillis");

                    mainHandler.post(() -> callback.onSuccess(durationMillis));
                } else {
                    mainHandler.post(() -> callback.onError("File không phải video hoặc không có metadata!"));
                }
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);

                mainHandler.post(() -> callback.onError("Lỗi chi tiết: " + sw.toString()));
            }
        });
    }

    public static String extractFileId(String driveUrl) {
        String regex = ".*(?:id=|/d/)([a-zA-Z0-9_-]+).*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(driveUrl);
        return matcher.find() ? matcher.group(1) : null;
    }

    public static String exportLink(String driveUrl){
        String fileId = extractFileId(driveUrl);
        return (fileId != null) ? "https://drive.google.com/uc?export=download&id=" + fileId : null;
    }
}

package com.example.appxemphim.Utilities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class NotificationPermissionHelper {

    public interface PermissionCallback {
        void onGranted();
        void onDenied();
    }

    public static boolean isPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return true;
        }
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, PermissionCallback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityResultLauncher<String> launcher =
                    ((androidx.activity.ComponentActivity) activity)
                            .registerForActivityResult(
                                    new ActivityResultContracts.RequestPermission(),
                                    isGranted -> {
                                        if (isGranted) callback.onGranted();
                                        else callback.onDenied();
                                    }
                            );

            launcher.launch(Manifest.permission.POST_NOTIFICATIONS);
        } else {
            callback.onGranted(); // không cần xin trên Android < 13
        }
    }


    public static void requestIfNeeded(AppCompatActivity activity,
                                       ActivityResultLauncher<String> permissionLauncher) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    activity, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
}
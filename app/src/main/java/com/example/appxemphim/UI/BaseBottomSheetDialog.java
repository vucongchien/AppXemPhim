package com.example.appxemphim.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public abstract class BaseBottomSheetDialog extends BottomSheetDialog {
    public BaseBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public BaseBottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BaseBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
} 
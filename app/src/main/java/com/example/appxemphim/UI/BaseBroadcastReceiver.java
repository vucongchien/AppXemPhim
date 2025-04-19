package com.example.appxemphim.UI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public abstract class BaseBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    }
} 
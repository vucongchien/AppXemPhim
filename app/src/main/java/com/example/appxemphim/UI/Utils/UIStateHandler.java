package com.example.appxemphim.UI.Utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class UIStateHandler {
    private ProgressBar progressBar;
    private ImageView imageError;
    private ImageView imageNotFound;
    private View dataContainerView;

    public UIStateHandler(ProgressBar progressBar, ImageView imageError, ImageView imageNotFound, View dataContainerView) {
        this.progressBar = progressBar;
        this.imageError = imageError;
        this.imageNotFound = imageNotFound;
        this.dataContainerView = dataContainerView;
    }

    public void showLoading() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if (imageError != null) imageError.setVisibility(View.GONE);
        if (imageNotFound != null) imageNotFound.setVisibility(View.GONE);
        if (dataContainerView != null) dataContainerView.setVisibility(View.GONE);
    }

    public void showError() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        if (imageError != null) imageError.setVisibility(View.VISIBLE);
        if (imageNotFound != null) imageNotFound.setVisibility(View.GONE);
        if (dataContainerView != null) dataContainerView.setVisibility(View.GONE);
    }

    public void showNotFound() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        if (imageError != null) imageError.setVisibility(View.GONE);
        if (imageNotFound != null) imageNotFound.setVisibility(View.VISIBLE);
        if (dataContainerView != null) dataContainerView.setVisibility(View.GONE);
    }

    public void showData() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        if (imageError != null) imageError.setVisibility(View.GONE);
        if (imageNotFound != null) imageNotFound.setVisibility(View.GONE);
        if (dataContainerView != null) dataContainerView.setVisibility(View.VISIBLE);
    }
}


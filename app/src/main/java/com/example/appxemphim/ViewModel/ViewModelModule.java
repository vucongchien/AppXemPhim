package com.example.appxemphim.ViewModel;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.multibindings.IntoMap;

@Module
@InstallIn(ViewModelComponent.class)
public abstract class ViewModelModule {

    // Thêm các @Binds cho các ViewModel của bạn ở đây
    // Ví dụ:
    // @Binds
    // @IntoMap
    // @ViewModelKey(UserViewModel.class)
    // public abstract ViewModel bindUserViewModel(UserViewModel viewModel);
} 
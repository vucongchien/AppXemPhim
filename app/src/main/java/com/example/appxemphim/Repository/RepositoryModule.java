package com.example.appxemphim.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    // Thêm các @Provides cho các repository của bạn ở đây
    // Ví dụ:
    // @Provides
    // @Singleton
    // public UserRepository provideUserRepository(UserApiService apiService) {
    //     return new UserRepository(apiService);
    // }
} 
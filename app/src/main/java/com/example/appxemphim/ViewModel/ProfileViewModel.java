package com.example.appxemphim.ViewModel;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.Profile;
import com.example.appxemphim.Model.ReviewModel;
import com.example.appxemphim.Repository.ProfileRepository;
import com.example.appxemphim.Repository.ReviewRepositytory;
import com.example.appxemphim.Request.ReviewRequest;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

public class ProfileViewModel {
    private  final ProfileRepository profileRepository;
    public ProfileViewModel(Context context){
        profileRepository = new ProfileRepository(context);
    }
    private  final MutableLiveData<Resource<Profile>> _getResult = new MutableLiveData<>();

    public LiveData<Resource<Profile>> getDataReslt = _getResult;
    public  void getprofile(String uid){
        profileRepository.getProfile(uid,_getResult);
    }
}

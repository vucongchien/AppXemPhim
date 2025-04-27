// AuthViewModel.java
package com.example.appxemphim.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.appxemphim.Model.DTO.EmailDTO;
import com.example.appxemphim.Repository.AuthRepository;
import com.example.appxemphim.Request.RepassRequest;
import com.example.appxemphim.UI.Utils.Resource;

public class AuthViewModel extends ViewModel {
    private final AuthRepository repository = new AuthRepository();

    private final MutableLiveData<Resource<String>> _loginResult = new MutableLiveData<>();
    public LiveData<Resource<String>> loginResult = _loginResult;

    private final MutableLiveData<Resource<String>> _emailCheckResult = new MutableLiveData<>();
    public LiveData<Resource<String>> emailCheckResult = _emailCheckResult;

    private final MutableLiveData<Resource<EmailDTO>> _sentDtoResult = new MutableLiveData<>();
    public LiveData<Resource<EmailDTO>> sentDtoResult = _sentDtoResult;

    private final MutableLiveData<Resource<String>> _repassResult = new MutableLiveData<>();
    public LiveData<Resource<String>> repassResult = _repassResult;

    public void login(String uid) {
        repository.loginWithToken(uid, _loginResult);
    }

    public void checkEmail(String email) {
        repository.checkEmail(email, _emailCheckResult);
    }

    public void sendDTO(String email) {
        repository.sentDTO(email, _sentDtoResult);
    }

    public void repass(RepassRequest request) {
        repository.repass(request, _repassResult);
    }
}

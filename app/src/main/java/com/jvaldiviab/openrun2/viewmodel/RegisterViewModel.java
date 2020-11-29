package com.jvaldiviab.openrun2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.jvaldiviab.openrun2.data.model.LoginPojo;
import com.jvaldiviab.openrun2.data.model.UsersPojo;
import com.jvaldiviab.openrun2.data.repository.FireBaseRepository;
import com.jvaldiviab.openrun2.data.repository.FireBaseRepositoryImpl;
import com.jvaldiviab.openrun2.data.repository.RegisterRepository;
import com.jvaldiviab.openrun2.util.UtilsValidate;

public class RegisterViewModel extends AndroidViewModel {

    private FireBaseRepository fireBaseRepository;
    private RegisterRepository registerRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        this.registerRepository=new RegisterRepository(application);
        this.fireBaseRepository=new FireBaseRepositoryImpl(application.getBaseContext());
    }
    public void register(String email,String pass){
        fireBaseRepository.registerUser(email,pass);
    }
    public void addDatabase(UsersPojo usersPojo){
        fireBaseRepository.addDatabase(usersPojo);
    }

    public boolean validateDates(String email, String pass){
        return UtilsValidate.emailAndPass(email, pass);
    }

    public MutableLiveData<UsersPojo> getRegisterLiveData(){
        return fireBaseRepository.getRegisterLiveData();
    }
    public void insert(LoginPojo loginPojo) {
        registerRepository.insert(loginPojo);
    }
    public MutableLiveData<FirebaseUser> getLoginMutableData(){
        return fireBaseRepository.getLoginFirebaseUser();
    }
}

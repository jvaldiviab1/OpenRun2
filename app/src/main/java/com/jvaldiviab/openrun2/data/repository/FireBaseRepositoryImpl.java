package com.jvaldiviab.openrun2.data.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.jvaldiviab.openrun2.data.model.UsersPojo;
import com.jvaldiviab.openrun2.view.activity.RegisterActivity;

public class FireBaseRepositoryImpl implements FireBaseRepository{

    private static final String TAG = "FirebaseRepositoryImpl";
    private Context mContext;
    private FirebaseAuth mAuth;

    private MutableLiveData<FirebaseUser> loginMutableLiveData;
    private MutableLiveData<UsersPojo> registerLiveData;

    public FireBaseRepositoryImpl(Context context){
        this.mContext = context;
        this.mAuth = FirebaseAuth.getInstance();
        this.loginMutableLiveData = new MutableLiveData<>();
        this.registerLiveData = new MutableLiveData<>();
    }

    @Override
    public void loginUser(String email, String password) {
        System.out.println("SE PASO POR AQUI TAMBIEN");
        Task<AuthResult> task = mAuth.signInWithEmailAndPassword(email, password);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    loginMutableLiveData.postValue(task.getResult().getUser());
                    // UsersPojo us1  = new UsersPojo(email, password, "123456");
                    // addDatabase(us1);
                    System.out.println("SE PASO POR AQUI TAMBIEN X2");
                    //FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    Log.d(TAG, "onComplete: "+task.getException().getMessage());
                    loginMutableLiveData.postValue(null);
                }
            }
        });
    }

    @Override
    public void registerUser(String email, String password) {
        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email, password);
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    loginMutableLiveData.postValue(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                }
            }
        });
    }

    @Override
    public void addDatabase(UsersPojo usersPojo) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Task<Void> myRef = database.getReference().child("users").child(currentUser.getUid()).setValue(usersPojo);
        myRef.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: hoyga");
                    registerLiveData.postValue(usersPojo);
                }
                else {
                    registerLiveData.postValue(null);
                    Log.d(TAG, "onComplete: nhi hua "+task.getException().getMessage());
                }
            }
        });
    }


    @Override
    public MutableLiveData<FirebaseUser> getLoginFirebaseUser() {
        return loginMutableLiveData;
    }

    @Override
    public MutableLiveData<UsersPojo> getRegisterLiveData() {
        return registerLiveData;
    }
}

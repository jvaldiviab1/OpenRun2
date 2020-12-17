package com.jvaldiviab.openrun2.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvaldiviab.openrun2.data.model.UsersPojo;
import com.jvaldiviab.openrun2.data.var.Constants;

public class ProfileRepository {

    private static final String TAG = "ProfileRepository";
    private Context mContext;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;

    private MutableLiveData<UsersPojo> profileLiveData;
    public UsersPojo usersPojo;


    public ProfileRepository() {
        this.mAuth = FirebaseAuth.getInstance();
        this.firebaseUser = mAuth.getCurrentUser();
        System.out.println(" SE CREO EL USUARIO!!!!!!!!!!!!!!!!!!!");
        this.database = FirebaseDatabase.getInstance();
        this.profileLiveData = new MutableLiveData<>();
    }

    public void updateProfile() {
        DatabaseReference referenceUsers = database.getReference(Constants.NODO_USERS);

        referenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                usersPojo = dataSnapshot.child(firebaseUser.getUid()).child("profile").getValue(UsersPojo.class);
                profileLiveData.postValue(usersPojo);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

    public UsersPojo getUser() {
        return getUser();
    }

    public MutableLiveData<UsersPojo> getProfileLiveData() {
        return profileLiveData;
    }
}

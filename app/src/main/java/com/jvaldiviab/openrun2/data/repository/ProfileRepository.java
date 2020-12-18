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

import java.util.HashMap;
import java.util.Map;

public class ProfileRepository {

    private static final String TAG = "ProfileRepository";
    private Context mContext;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    DatabaseReference mDatabase;

    private MutableLiveData<UsersPojo> profileLiveData;
    public UsersPojo usersPojo;


    public ProfileRepository() {
        this.mAuth = FirebaseAuth.getInstance();
        this.firebaseUser = mAuth.getCurrentUser();
        System.out.println(" SE CREO EL USUARIO!!!!!!!!!!!!!!!!!!!");
        this.database = FirebaseDatabase.getInstance();
        this.profileLiveData = new MutableLiveData<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void updateProfilFirebase(UsersPojo userpojo){
        String id = mAuth.getCurrentUser().getUid();
        Map<String, Object> map = new HashMap<>();
        map.put("age", userpojo.getAge());
        map.put("bodyType", userpojo.getBodyType());
        map.put("description", userpojo.getDescription());
        map.put("height", userpojo.getHeight());
        map.put("name", userpojo.getName());
        map.put("trainingType", userpojo.getTrainingType());
        map.put("weight", userpojo.getWeight());
        map.put("targetWeight", userpojo.getTargetWeight());

        mDatabase.child("users").child(id).child("profile").setValue(map);
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
    public void signOutProfile() {

        mAuth.signOut();
    }
    public UsersPojo getUser() {
        return getUser();
    }

    public MutableLiveData<UsersPojo> getProfileLiveData() {
        return profileLiveData;
    }
}

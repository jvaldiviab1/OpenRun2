package com.jvaldiviab.openrun2.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jvaldiviab.openrun2.data.model.RunPojo;
import com.jvaldiviab.openrun2.data.model.UsersPojo;
import com.jvaldiviab.openrun2.util.UtilsValidate;

import java.util.HashMap;
import java.util.Map;

import static com.jvaldiviab.openrun2.util.UtilsValidate.getDate;

public class MapsRepository {

    private static final String TAG = "MapsRepository";
    private Context mContext;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;

    private MutableLiveData<RunPojo> runLiveData;
    public RunPojo runPojo;


    public MapsRepository() {
        this.mAuth = FirebaseAuth.getInstance();
        this.firebaseUser = mAuth.getCurrentUser();
        this.database = FirebaseDatabase.getInstance();
        this.runLiveData = new MutableLiveData<>();
    }


    public void addHistory(float accumDistMeters, long startTimeMillis, long endTimeMillis) {

        String miles = Float.toString(accumDistMeters);
        String total_time = UtilsValidate.getStopwatchTime(startTimeMillis, endTimeMillis);
        String pace = Float.toString(UtilsValidate.getAverageP(accumDistMeters, endTimeMillis - startTimeMillis));
        String date = getDate();

        RunPojo rn = new RunPojo(miles, total_time, pace, date);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Task<Void> myRef = database.getReference().child("users").child(currentUser.getUid()).child("history").child(database.getReference().child(firebaseUser.getUid()).child("history").push().getKey()).setValue(rn);
        myRef.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "onComplete: hoyga");
                } else {
                    Log.d(TAG, "onComplete: nhi hua " + task.getException().getMessage());
                }
            }
        });
//
//        DatabaseReference databaseReference = database.getReference().child(firebaseUser.getUid()).child("history").push();
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", databaseReference.getKey());
//        map.put("distance", miles);
//        map.put("time", total_time);
//        map.put("avgPace", pace);
//        map.put("date", date);
//
//        System.out.println("--------------------"+databaseReference.getKey());
//        databaseReference.setValue(map);

    }
}

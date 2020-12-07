package com.jvaldiviab.openrun2.data.repository;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jvaldiviab.openrun2.util.UtilActividades;

import java.util.ArrayList;
import java.util.List;

public class ActividadesRepositoryImpl implements ActividadesRepository{
    public DatabaseReference mDatabase;

    public ActividadesRepositoryImpl() {

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public Query obtenerActividadesDelDia(String IdUser,String Fecha) {
        Query mData = mDatabase.child("users").child(IdUser).child("Actividades").child(Fecha.replace("/",""));

        return mData;
    }
}

package com.jvaldiviab.openrun2.data.repository;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
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
    FirebaseAuth mAuth;

    public ActividadesRepositoryImpl() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public Query obtenerActividadesDelDia(String Fecha) {
        Query mData = mDatabase.child("users").child(mAuth.getUid()).child("Actividades").child(Fecha.replace("/",""));

        return mData;
    }
    @Override
    public void DeleteActividades(UtilActividades util) {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(id).child("Actividades").child(util.getFecha().replace("/","")).child(util.getCodigo()).removeValue();
    }
}

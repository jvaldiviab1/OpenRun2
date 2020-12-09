package com.jvaldiviab.openrun2.data.repository;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jvaldiviab.openrun2.util.UtilActividades;
import com.jvaldiviab.openrun2.view.activity.PopUpActivity;
import com.jvaldiviab.openrun2.view.fragment.TodosFragment;

import java.util.HashMap;
import java.util.Map;

public class PopUpRepositoryImpl  implements PopUpRepository {
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    public PopUpRepositoryImpl() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public Task InsertActividades(UtilActividades util) {
        String id = mAuth.getCurrentUser().getUid();
        Map<String, Object> map = new HashMap<>();
        map.put("Hora", util.getHora());
        map.put("Nota", util.getNota());
        map.put("Estado", util.getEstado());
        String uuid=uuid();
        map.put("Codigo", uuid);

         return mDatabase.child("users").child(id).child("Actividades").child(util.getFecha().replace("/","")).child(uuid).setValue(map);

    }


    public String uuid()
    {
        String result = java.util.UUID.randomUUID().toString();

        result.replaceAll("-", "");
        result.substring(0, 32);

        return result;
    }
}

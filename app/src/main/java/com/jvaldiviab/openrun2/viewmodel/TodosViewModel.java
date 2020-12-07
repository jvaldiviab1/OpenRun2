package com.jvaldiviab.openrun2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jvaldiviab.openrun2.data.repository.ActividadesRepository;
import com.jvaldiviab.openrun2.data.repository.ActividadesRepositoryImpl;
import com.jvaldiviab.openrun2.data.repository.FireBaseRepository;
import com.jvaldiviab.openrun2.data.repository.FireBaseRepositoryImpl;
import com.jvaldiviab.openrun2.util.UtilActividades;
import com.jvaldiviab.openrun2.view.adapter.AdapterDatos;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

public class TodosViewModel extends AndroidViewModel {

    private FireBaseRepository fireBaseRepository;
    private ActividadesRepository actividadesRepository;

    public TodosViewModel(@NonNull Application application) {
        super(application);
        this.fireBaseRepository = new FireBaseRepositoryImpl(application.getBaseContext());
        actividadesRepository=new ActividadesRepositoryImpl();
        System.out.println("SE PASO POR AQUI");
    }

    public String getIdUser() {
        return fireBaseRepository.getIdUser();
    }
    public void getListActividades(String IdUser, String fecha, RecyclerView RV){
        Query mData=actividadesRepository.obtenerActividadesDelDia(IdUser,fecha);
        mData.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                List<UtilActividades> lista=new ArrayList<>();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    try {
                        String Hora=data.child("Hora").getValue(String.class);
                        String Nota=data.child("Nota").getValue(String.class);
                        String Tiempo=data.child("Tiempo").getValue(String.class);
                        lista.add(new UtilActividades(fecha,Nota,Tiempo,Hora));
                    }catch (Exception e){continue;}
                }

                AdapterDatos adapterDatos= new AdapterDatos(lista);
                RV.setAdapter(adapterDatos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public MutableLiveData<FirebaseUser> getLoginMutableData() {
        return fireBaseRepository.getLoginFirebaseUser();
    }

}

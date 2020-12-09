package com.jvaldiviab.openrun2.viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jvaldiviab.openrun2.data.repository.ActividadesRepository;
import com.jvaldiviab.openrun2.data.repository.ActividadesRepositoryImpl;
import com.jvaldiviab.openrun2.data.repository.FireBaseRepository;
import com.jvaldiviab.openrun2.data.repository.FireBaseRepositoryImpl;
import com.jvaldiviab.openrun2.data.repository.PopUpRepository;
import com.jvaldiviab.openrun2.data.repository.PopUpRepositoryImpl;
import com.jvaldiviab.openrun2.util.UtilActividades;
import com.jvaldiviab.openrun2.view.activity.PopUpActivity;

public class PopUpViewModel extends AndroidViewModel {
    private PopUpRepository popUpRepository;

    public PopUpViewModel(@NonNull Application application) {
        super(application);
        popUpRepository=new PopUpRepositoryImpl();
    }

    public void InsertarActidad(UtilActividades util){
        Task task=popUpRepository.InsertActividades(util);
        /*task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if (task2.isSuccessful()) {

                } else {
                    Toast.makeText(ctx, "Error al momento de crear la Actividad.", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }
}

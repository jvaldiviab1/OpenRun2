package com.jvaldiviab.openrun2.view.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import com.jvaldiviab.openrun2.R;

public class PopUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        DisplayMetrics DM=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(DM);
        int Ancho=DM.widthPixels;
        int Largo=DM.heightPixels;
        getWindow().setLayout((int)(Ancho*0.85),(int)(Largo*0.75));
    }
}
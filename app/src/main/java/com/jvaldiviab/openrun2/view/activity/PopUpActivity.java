package com.jvaldiviab.openrun2.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.databinding.ActivityLoginBinding;
import com.jvaldiviab.openrun2.databinding.ActivityPopUpBinding;
import com.jvaldiviab.openrun2.util.UtilActividades;
import com.jvaldiviab.openrun2.view.fragment.TodosFragment;
import com.jvaldiviab.openrun2.viewmodel.LoginViewModel;
import com.jvaldiviab.openrun2.viewmodel.PopUpViewModel;
import com.jvaldiviab.openrun2.viewmodel.TodosViewModel;

import java.util.Calendar;

public class PopUpActivity extends AppCompatActivity implements View.OnClickListener {


    private PopUpViewModel viewModel;
    private ActivityPopUpBinding binding;
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPopUpBinding.inflate(getLayoutInflater());
        viewModel =new ViewModelProvider(this).get(PopUpViewModel.class);
        View view = binding.getRoot();
        setContentView(view);
        DisplayMetrics DM=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(DM);
        int Ancho=DM.widthPixels;
        int Largo=DM.heightPixels;
        getWindow().setLayout((int)(Ancho*0.85),(int)(Largo*0.75));
        binding.verCalender.setOnClickListener(this);
        binding.verTime.setOnClickListener(this);
        binding.aceptar.setOnClickListener(this);
        binding.cancelar.setOnClickListener(this);
        binding.Hora.setText("");
        binding.Fecha.setText("");
        binding.nota.setText("");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.verTime:
                obtenerHora();
                break;
            case R.id.verCalender:
                obtenerFecha();
                break;
            case R.id.aceptar:
                insertarDatos();
                break;
            case R.id.cancelar:
                cerrarPopUp();
                break;
        }
    }
    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = String.format("%02d", dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = String.format("%02d", mesActual);
                //Muestro la fecha con el formato deseado
                binding.Fecha.setText(diaFormateado + "/" + mesFormateado + "/" + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }
    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  String.format("%02d", hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = String.format("%02d", minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                binding.Hora.setText(horaFormateada + ":" + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }
    private void insertarDatos(){
        UtilActividades util=new UtilActividades(binding.Fecha.getText().toString(),binding.nota.getText().toString(),"",binding.Hora.getText().toString(),"0","");
        viewModel.InsertarActidad(util);
        cerrarPopUp();
    }
    private void cerrarPopUp(){
        onBackPressed();
    }
}
package com.jvaldiviab.openrun2.view.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jvaldiviab.openrun2.R;

public class GeneralFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bottom_main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if(id== R.id.navigation_account){
            Toast.makeText(getActivity(),"Cuenta", Toast.LENGTH_LONG).show();
        }
        if(id== R.id.navigation_edit_profile){
            Toast.makeText(getActivity(),"Editar Perfil", Toast.LENGTH_LONG).show();
        }
        if(id== R.id.navigation_sign_out){
            Toast.makeText(getActivity(),"Cerrar Sesion", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.jvaldiviab.openrun2.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.data.model.UsersPojo;
import com.jvaldiviab.openrun2.databinding.FragmentEdiProfileBinding;
import com.jvaldiviab.openrun2.databinding.FragmentProfileBinding;
import com.jvaldiviab.openrun2.util.UtilInputFilterMinMax;
import com.jvaldiviab.openrun2.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EdiProfileFragment extends Fragment {
    private final static List<String> cuerpos = Arrays.asList("Seleccione","Ectomorfo","Endomorfo","Mesomorfo" );
    private final static List<String> tipoEntrenamiento= Arrays.asList( "Seleccione", "Ligero", "Medio","Intensivo" );
    ArrayAdapter adapCuerpo;
    ArrayAdapter adaptipoEntre;
    FragmentEdiProfileBinding binding;

    ProfileViewModel viewModel;

    public EdiProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapCuerpo = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, cuerpos);
        adaptipoEntre = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,  tipoEntrenamiento);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentEdiProfileBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        binding.spinCuerpo.setAdapter(adapCuerpo);
        binding.spinTipoEntrenamiento.setAdapter(adaptipoEntre);
        binding.txtEdad.setFilters(new InputFilter[]{ new UtilInputFilterMinMax("6", "125")});
        binding.txtPeso.setFilters(new InputFilter[]{ new UtilInputFilterMinMax("20", "600")});
        binding.txtMeta.setFilters(new InputFilter[]{ new UtilInputFilterMinMax("20", "600")});
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getProfileLiveData().observe(getViewLifecycleOwner(), usersPojo -> {
                    if (usersPojo != null) {
                        binding.txtNomUsuario.setText(usersPojo.getName());
                        binding.txtDescripcion.setText(usersPojo.getDescription());
                        binding.txtPeso.setText(usersPojo.getWeight());
                        binding.txtEdad.setText(usersPojo.getAge());
                        binding.spinCuerpo.setSelection(cuerpos.indexOf(usersPojo.getBodyType()));
                        binding.spinTipoEntrenamiento.setSelection(tipoEntrenamiento.indexOf(usersPojo.getTrainingType()));
                        binding.txtMeta.setText(usersPojo.getTargetWeight());
                    }
                }
        );
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersPojo usersPojo=new UsersPojo();
                usersPojo.setName(binding.txtNomUsuario.getText().toString());
                usersPojo.setDescription( binding.txtDescripcion.getText().toString());
                usersPojo.setWeight(binding.txtPeso.getText().toString());
                usersPojo.setAge(binding.txtEdad.getText().toString());
                usersPojo.setBodyType(binding.spinCuerpo.getSelectedItem().toString());
                usersPojo.setTrainingType(binding.spinTipoEntrenamiento.getSelectedItem().toString());
                usersPojo.setTargetWeight(binding.txtMeta.getText().toString());
                viewModel.updateProfileFirebase(usersPojo);
                getActivity().onBackPressed();
            }
        });
    }
}
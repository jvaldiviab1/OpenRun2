package com.jvaldiviab.openrun2.data.repository;

import com.google.firebase.database.Query;
import com.jvaldiviab.openrun2.util.UtilActividades;

import java.util.List;

public interface ActividadesRepository {
    Query obtenerActividadesDelDia(String IdUser, String Fecha);
    void DeleteActividades(UtilActividades util);
}

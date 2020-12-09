package com.jvaldiviab.openrun2.util;

public class UtilActividades {
    public String fecha;
    public String nota;
    public String tiempo;
    public String hora;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public UtilActividades(String fecha, String nota, String tiempo, String hora, String estado, String codigo) {
        this.fecha = fecha;
        this.nota = nota;
        this.tiempo = tiempo;
        this.hora = hora;
        this.estado = estado;
        this.codigo = codigo;
    }

    public String estado;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String codigo;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}

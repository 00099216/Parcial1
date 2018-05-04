package com.andres00099216.parcial1;

import java.io.Serializable;

/**
 * Created by Andres on 30/4/2018.
 */

public class Contacto implements Serializable{
private String nombre;
private String telefono;
private String correo;
private boolean agregado;
private int foto;

//Constructor del objeto
    public Contacto (String nombre, String telefono, String correo) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        agregado = false;
        foto = R.drawable.generic_picture;
    }

    // Setters y getters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isAgregado() {
        return agregado;
    }

    public void setAgregado(boolean agregado) {
        this.agregado = agregado;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}

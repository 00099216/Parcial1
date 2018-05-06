package com.andres00099216.parcial1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andres on 30/4/2018.
 */

public class Contacto implements Parcelable {
    private int id;
    private String nombre;
    private String telefono;
    private String correo;
    private boolean agregado;
    private String foto;
    private boolean buscado;
    private static int amount = 0;

//Constructor del objeto
    public Contacto(int id, String nombre, String telefono, String correo, boolean agregado, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.agregado = agregado;
        this.foto = foto;
        buscado = true;
        amount++;
    }

//metodo que parcea
    protected Contacto(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        telefono = in.readString();
        correo = in.readString();
        agregado = in.readByte() != 0;
        foto = in.readString();
        buscado = in.readByte() != 0;
    }
// creador que implementa el parcelable
    public static final Creator<Contacto> CREATOR = new Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };
//Metodos de parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nombre);
        parcel.writeString(telefono);
        parcel.writeString(correo);
        parcel.writeByte((byte) (agregado ? 1 : 0));
        parcel.writeString(foto);
        parcel.writeByte((byte) (buscado ? 1 : 0));
    }
    //para copiar contactos
    public void copiar(Contacto Contc) {
        nombre = Contc.getNombre();
        telefono = Contc.getTelefono();
        foto = Contc.getFoto();
        correo = Contc.getCorreo();
    }


//Setters y getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isBuscado() {
        return buscado;
    }

    public void setBuscado(boolean buscado) {
        this.buscado = buscado;
    }

    public static int getAmount() {
        return amount;
    }

    public static void setAmount(int amount) {
        Contacto.amount = amount;
    }
}


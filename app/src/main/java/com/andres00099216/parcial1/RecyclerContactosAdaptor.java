package com.andres00099216.parcial1;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Andres on 30/4/2018.
 */

public abstract class RecyclerContactosAdaptor extends RecyclerView.Adapter<RecyclerContactosAdaptor.ViewHolder> {
 private ArrayList<Contacto> Contactos;

 private RecyclerContactosAdaptor(ArrayList<Contacto> Contactos){this.Contactos=Contactos;}




 class ViewHolder extends RecyclerView.ViewHolder{

     public ViewHolder(View itemView) {
         super(itemView);
     }
 }
}

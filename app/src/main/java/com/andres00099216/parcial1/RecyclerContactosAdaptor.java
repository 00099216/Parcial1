package com.andres00099216.parcial1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andres on 30/4/2018.
 */

public abstract class RecyclerContactosAdaptor extends RecyclerView.Adapter<RecyclerContactosAdaptor.ViewHolder> {
 private ArrayList<Contacto> Contactos;
    private static boolean agregado =false;

 public RecyclerContactosAdaptor(ArrayList<Contacto> Contactos){this.Contactos=Contactos;}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacto, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
     holder.texto.setText(Contactos.get(position).getNombre());
     holder.foto.setImageResource(Contactos.get(position).getFoto());


        if(Contactos.get(position).isAgregado()) {
            holder.favorito.setImageResource(R.drawable.estrella_relleno);
        }else {
            holder.favorito.setImageResource(R.drawable.estrella);
        }
        holder.favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fav(position)){
                    holder.favorito.setImageResource(R.drawable.estrella_relleno);
                }else {
                    holder.favorito.setImageResource(R.drawable.estrella);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return Contactos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

    ImageButton favorito;
    TextView texto;
    ImageView foto;

     public ViewHolder(View itemView) {
         super(itemView);
         foto= itemView.findViewById(R.id.foto_contacto);
         texto= itemView.findViewById(R.id.nombre_contacto);
         favorito= itemView.findViewById(R.id.boton_favoritos);


     }
 }
    public boolean fav(final int position) {
        Contactos.get(position).setAgregado(!Contactos.get(position).isAgregado());
        return Contactos.get(position).isAgregado();
    }
    public void SetTrue(){
        agregado =true;
    }

    public void SetFalse(){
        agregado =false;
    }

    public boolean isAgregado() {
        return agregado;
    }
}

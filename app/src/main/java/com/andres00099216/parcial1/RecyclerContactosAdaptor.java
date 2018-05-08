package com.andres00099216.parcial1;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andres on 30/4/2018.
 */

public class RecyclerContactosAdaptor extends RecyclerView.Adapter<RecyclerContactosAdaptor.ViewHolder> {
    //Se declaran variables
    private int t;
    private Context contexto;
    private ArrayList<Contacto> Contactos = new ArrayList<>();

    public RecyclerContactosAdaptor(int tipo, Context contexto, ArrayList<Contacto> contactos) {
        this.contexto = contexto;
        t = tipo;
        //verifica el contexto en que esta, verificando si se esta en la lista de contactos de favoritos o solo contactos
        if (t == 0) {
            //normal
            for (Contacto contacto : contactos) {
                //Solo muestra los que se buscan
                if (contacto.isBuscado())
                    Contactos.add(contacto);
            }
        }
        if (t == 1) {
            //favorito
            for (Contacto contacto : contactos) {
                if (contacto.isAgregado() && contacto.isBuscado())
                    Contactos.add(contacto);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(contexto);
        View view = inflater.from(parent.getContext()).inflate(R.layout.contacto, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.texto.setText(Contactos.get(position).getNombre());
        //asignar foto de perfil
        System.out.println(holder.foto);
        if (Contactos.get(position).getFoto() == null)
            holder.foto.setImageResource(R.drawable.generic_picture);
        else
            holder.foto.setImageURI(Uri.parse(Contactos.get(position).getFoto()));

        //para la imagen del boton favorito
        if (Contactos.get(position).isAgregado()) {
            holder.favorito.setImageResource(R.drawable.estrella_relleno);
        } else {
            holder.favorito.setImageResource(R.drawable.estrella);
        }
        //para cambiar el estado del boton favorito
        holder.favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Contacto contacto = Contactos.get(holder.getAdapterPosition());
                if (!contacto.isAgregado()) {
                    contacto.setAgregado(true);
                    notifyDataSetChanged();
                } else {
                    contacto.setAgregado(false);
                    notifyDataSetChanged();
                }
            }
        });

        //Se le asigna un evento al CardView
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //estado landspace
                if (v.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {

                    Intent intent = new Intent(v.getContext(), MostrarContacto.class);
                    Bundle arguments = new Bundle();

                    //Se le manda el Contacto segun la posicion
                    arguments.putParcelable("Contactos", Contactos.get(position));
                    intent.putExtras(arguments);

                    //Se inicia el Activity
                    contexto.startActivity(intent);

                    //El caso de que se encuentre en Landscape
                } else {
                    //se crea el bundle de datos que se le enviara al fragmento
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Contactos", Contactos.get(position));

                    FragmentoMostrarContacto frag = new FragmentoMostrarContacto();

                    frag.setArguments(bundle);
                    FragmentManager fragmentManager = ((FragmentActivity) contexto).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentHorizontal, frag);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Contactos.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        //Variables
        ImageButton favorito;
        TextView texto;
        ImageView foto;
        CardView item;

        //Se referencian las variables con los valores del layout
        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item_contacto);
            foto = itemView.findViewById(R.id.profilePicture);
            texto = itemView.findViewById(R.id.nombre_contacto);
            favorito = itemView.findViewById(R.id.boton_favoritos);
        }
    }
}




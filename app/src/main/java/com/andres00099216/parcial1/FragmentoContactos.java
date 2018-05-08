package com.andres00099216.parcial1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Andres on 7/5/2018.
 */

public class FragmentoContactos extends Fragment {
    //Se declaran variables
    private RecyclerView recycler;
    private FloatingActionButton boton;
    private RecyclerContactosAdaptor adapter;
    private GridLayoutManager manager;
    private ArrayList<Contacto> Contactos;
    private int t;

    public static FragmentoContactos newInstance(int type, ArrayList<Contacto> contacto) {
        FragmentoContactos fragmento = new FragmentoContactos();
        fragmento.t = type;
        fragmento.Contactos = contacto;
        return fragmento;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragmento_contactos, container, false);
        recycler = v.findViewById(R.id.recycler_view_contactos);
        recycler.setHasFixedSize(true);

        //Se verifica si existen cambios en al girar
        if (savedInstanceState != null) {
            Contactos = savedInstanceState.getParcelableArrayList("Contacto_adapter");
            t = savedInstanceState.getInt("RECYCLER");
        }

        //Adaptador
        adapter = new RecyclerContactosAdaptor(t, container.getContext(), Contactos);
        manager = new GridLayoutManager(container.getContext(), 3);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);

        boton = v.findViewById(R.id.agregar_contacto);
        //Se crea evento del boton agregar
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Se abre la seccion de agregar
                Intent intent = new Intent(recycler.getContext(), AgregarContacoActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("Contacto_adapter", Contactos);
        outState.putInt("RECYCLER", t);
    }
}

package com.andres00099216.parcial1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  RecyclerContactosAdaptor contactosAdaptor;
    private ArrayList<Contacto> Contactos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_contactos);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        Contactos = new ArrayList<>();

        Contactos.add(new Contacto("Andres", "78192239", "00099216@", R.drawable.generic_picture));
        Contactos.add(new Contacto("Andres", "78192239", "00099216@", R.drawable.generic_picture));
        Contactos.add(new Contacto("Andres", "78192239", "00099216@", R.drawable.generic_picture));
        Contactos.add(new Contacto("Andres", "78192239", "00099216@", R.drawable.generic_picture));



        contactosAdaptor = new RecyclerContactosAdaptor(Contactos) {

        };
        recyclerView.setAdapter(contactosAdaptor);
    }
}

package com.andres00099216.parcial1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//Se definen variables
    private RecyclerView recyclerView;
    private  RecyclerContactosAdaptor contactosAdaptor;
    private ArrayList<Contacto> Contactos;
    private  ArrayList<String> contacto_nuevo;
    FloatingActionButton agregar_contacto_screen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_contactos);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        Contactos = new ArrayList<>();
        contacto_nuevo = new ArrayList<>();

        //Parte en que se captura algun posible intent de la actividad agregar contactos
        Intent callingIntent = getIntent();
        String intentAction = callingIntent.getAction();
        String intentType = callingIntent.getType();

        if (Intent.ACTION_SEND.equals(intentAction) && intentType != null){
            if (intentType.equals("text/plain")){
                contacto_nuevo = callingIntent.getStringArrayListExtra("Contacto");
                Contactos.add(new Contacto(contacto_nuevo.get(0), contacto_nuevo.get(1), contacto_nuevo.get(2)));

            }
        }

        // se setea el adaptador
        contactosAdaptor = new RecyclerContactosAdaptor(Contactos) {

        };
        recyclerView.setAdapter(contactosAdaptor);

        //Funcionalidad el boton agregar nuevo contacto
        agregar_contacto_screen = findViewById(R.id.agregar_contacto);
        agregar_contacto_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent agregar_contacto_intent = new Intent(getApplicationContext(), AgregarContacoActivity.class);
                startActivity(agregar_contacto_intent);
            }
        });

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }



}

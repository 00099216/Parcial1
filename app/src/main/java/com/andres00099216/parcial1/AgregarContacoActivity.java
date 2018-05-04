package com.andres00099216.parcial1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.util.ArrayList;

/**
 * Created by Andres on 2/5/2018.
 */

public class AgregarContacoActivity extends AppCompatActivity {
    //variables declaradas
    Button agregar;
    private ArrayList<String> Contactos;
    private String nombre;
    private String telefono;
    private String correo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_contacto);

        //Funcionalidad del boton agregar
        agregar=findViewById(R.id.crear_contacto);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent sendIntent = new Intent(getApplicationContext(), MainActivity.class);

                //Se obtiene el contenido de los EditText y se guardan en un ArrayList
                nombre=((EditText)findViewById(R.id.add_nombre_contacto)).getText().toString();
                telefono=((EditText)findViewById(R.id.add_telefono_contacto)).getText().toString();
                correo=((EditText)findViewById(R.id.add_correo_contacto)).getText().toString();
                Contactos = new ArrayList<>();
                Contactos.add(nombre);
                Contactos.add(telefono);
                Contactos.add(correo);

                //Se envia el contenido (un ArrayList)
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra("Contacto", Contactos);
                startActivity(sendIntent);
                }
            });

    }
}

package com.andres00099216.parcial1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Andres on 6/5/2018.
 */
public class MostrarContacto extends AppCompatActivity {
    //Variables
    private ImageView foto;
    private TextView nombre;
    private TextView telefono;
    private TextView correo;
    private ImageView llamar;
    private ImageView compartir;
    Contacto contacto;
    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Layout al que hace referencia
        setContentView(R.layout.fragmento_perfil_contacto);
        //Se asigna la referenciaampos en el layuot
        foto = findViewById(R.id.foto_contacto);
        nombre = findViewById(R.id.nombre_contacto);
        telefono = findViewById(R.id.telefono_contacto);
        correo = findViewById(R.id.correo_contacto);


        llamar = findViewById(R.id.llamar);
        //Donde se llama
        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent para llamar
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + telefono.getText().toString().replace("-", "")));


                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Activity activity = (Activity) getApplicationContext();
                    ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
                }

                //hace la llamada
                else startActivity(intent);
            }
        });

        Intent intent = getIntent();
        //guarda el contacto en caso de ser girado no sea destruido
        if (savedInstanceState != null) {
            contacto = savedInstanceState.getParcelable("Contactos");
        } else {
            if (intent.hasExtra("Contactos")) {
                contacto = intent.getExtras().getParcelable("Contactos");
            }
        }
        //Se asignan los valores a los campos
        foto.setImageURI(Uri.parse(contacto.getFoto()));
        nombre.setText(contacto.getNombre());
        telefono.setText(contacto.getTelefono());
        correo.setText(contacto.getCorreo());
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Guarda el Contacto
        outState.putParcelable("Contactos", contacto);
    }
}



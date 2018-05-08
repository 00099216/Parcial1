package com.andres00099216.parcial1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;


/**
 * Created by Andres on 2/5/2018.
 */

public class AgregarContacoActivity extends AppCompatActivity implements Serializable {
    //variables declaradas
    private transient Button agregar;
    private ImageView foto;
    private EditText nombre;
    private EditText telefono;
    private EditText correo;
    private transient FloatingActionButton nueva_foto;
    private int RESULT_LOAD_IMG = 1;
    private Uri fotoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_contacto);

        foto = findViewById(R.id.agregar_foto);
        nombre = findViewById(R.id.add_nombre_contacto);
        telefono = findViewById(R.id.add_telefono_contacto);
        correo = findViewById(R.id.add_correo_contacto);
        nueva_foto = findViewById(R.id.subir_foto);
        agregar = findViewById(R.id.crear_contacto);
        fotoUri = Uri.parse("android.resource://com.andres00099216.parcial1/" + R.drawable.generic_picture);
//agrega nueva foto de perfil
        nueva_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent para subir la foto
                Intent subiFoto = new Intent(Intent.ACTION_PICK);
                subiFoto.setType("image/*");
                startActivityForResult(subiFoto, RESULT_LOAD_IMG);
            }
        });

//agrega el contacto
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                //Se crea el nuevo contacto
                Contacto Contactos = new Contacto(-1, nombre.getText().toString(), telefono.getText().toString(), correo.getText().toString(), false, fotoUri + "");
                intent.putExtra("ADD", Contactos);
                Contactos.setId(Contactos.getAmount());
                v.getContext().startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int solicitado, int resultado, Intent data) {

        super.onActivityResult(solicitado, resultado, data);


        if (resultado == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                fotoUri = imageUri;

                //convierte a bitmap
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap imagenSelecionada = BitmapFactory.decodeStream(imageStream);

                //Se pone la imagen
                foto.setImageBitmap(imagenSelecionada);

                //en caso de que algo salga mal xd
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "pick image", Toast.LENGTH_LONG).show();
        }
    }

}

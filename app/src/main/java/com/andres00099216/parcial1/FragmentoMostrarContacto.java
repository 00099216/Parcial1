package com.andres00099216.parcial1;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;


/**
 * Created by Andres on 6/5/2018.
 */

public class FragmentoMostrarContacto extends Fragment {
    //Variables
    private ImageView foto;
    private TextView nombre;
    private TextView telefono;
    private TextView correo;
    private ImageView llamar;

    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 101;

    Contacto contacto;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_perfil_contacto, container, false);

        foto = view.findViewById(R.id.foto_contacto);
        nombre = view.findViewById(R.id.nombre_contacto);
        telefono = view.findViewById(R.id.telefono_contacto);
        correo = view.findViewById(R.id.correo_contacto);


        llamar = view.findViewById(R.id.llamar);
        //Funcionalidad el boton llamar
        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Donde se realiza la llamada
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + telefono.getText().toString().replace("-", "")));

                if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
                } else startActivity(intent);

            }
        });



        Bundle bundle = this.getArguments();

        if (savedInstanceState != null) {
            //por si cambia de estado
            contacto = savedInstanceState.getParcelable("Contactos");
        } else {
            if (bundle != null) {
                contacto = bundle.getParcelable("Contactos");
                foto.setImageURI(Uri.parse(contacto.getFoto()));
                nombre.setText(contacto.getNombre());
                telefono.setText(contacto.getTelefono());
                correo.setText(contacto.getCorreo());
            }
        }
        return view;
    }
}






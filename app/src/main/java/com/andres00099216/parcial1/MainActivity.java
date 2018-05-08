package com.andres00099216.parcial1;

import android.Manifest;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.support.design.widget.TabLayout;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    //Se definen variables
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private transient PagerAdapter pagerAdapter;
    private static boolean primerVez = true;
    public static ArrayList<Contacto> Contactos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se hace referencia a la layout a usar
        setContentView(R.layout.activity_main);
        Intent intent = this.getIntent();

        // verifica i algo entra
        Verificante(intent);
        //Existe algun dato antes de que se girara
        if (savedInstanceState != null) {
            Contactos = savedInstanceState.getParcelableArrayList("Contacto_adapter");
        }
        //Se obtienen los contactos por primera vez
        if (Contactos.size() == 0 && primerVez) {
            //Se llama el metodo showcontacts
            mostrarContacto();
            primerVez = false;
        }
        //Se establece las variables del viewpager
        FragmentManager valueof = getSupportFragmentManager();
        if (pagerAdapter == null)
            pagerAdapter = new PagerAdapter(valueof);
        ViewPager viewPager = findViewById(R.id.Pager);
        viewPager.setAdapter(pagerAdapter);

        //Se le agregan los fragmentos
        pagerAdapter.addFragment(FragmentoContactos.newInstance(0, Contactos), "Contactos");
        pagerAdapter.addFragment(FragmentoContactos.newInstance(1, Contactos), "favoritos");


        //Se incorporan los titulos
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        //si existe un itent de busqueda se llama este metodo
        handleIntent(getIntent());
        //Se verifica si esta en Landspace
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentTransaction ft = valueof.beginTransaction();
            FragmentoMostrarContacto frag = new FragmentoMostrarContacto();
            // Remplaza los contenidos del elemento por un fragmento
            ft.replace(R.id.fragmentHorizontal, frag);
            // Realiza el replace
            ft.commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("Contacto_adapter", Contactos);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.buscador, menu);

        //Para el search manager
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    private void mostrarContacto() {
        // Verfica la version de SDK y si los permison se les fueron concedidos ya
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {

            agregarContacto();
        }
    }

    //en su mayoria es codigo de internet xd
    public void agregarContacto() {
        try {
            ContentResolver cr = getContentResolver();
            //Cursor que recorre las tablas integradas de los contactos
            Cursor general = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            //Se recorren las tablas
            while (general.moveToNext()) {
                //Se obtiene el ID del numero
                String id = general.getString(general.getColumnIndex(ContactsContract.Contacts._ID));                //Su nombre
                String nombre = general.getString(general.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String foto = general.getString(general.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                String correo = general.getString(general.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                String telefono = general.getString(general.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                //Se agrega el contacto
                Contactos.add(new Contacto(Integer.parseInt(id), nombre, telefono, correo, false, foto));
            }
            //Se cierra el Cursor
            general.close();
            //Si falla algo tira el error
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permiso, int[] grantResults) {
        //Usando una variable estatica preguntamos pormedio de este como un puerto, si es concedido el permiso
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mostrarContacto();
            } else {
                Toast.makeText(this, "Se necesitan permisos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleIntent(Intent intent) {
        // para busquedas
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            //Se obtiene el string
            String query = intent.getStringExtra(SearchManager.QUERY);
            Pattern p = Pattern.compile(query);
            Matcher match;

            //Se recorren los contactos
            for (Contacto contacto : Contactos) {

                //Se preguta si tiene una parte en comun
                match = p.matcher(contacto.getNombre());
                if (match.find())

                    //Si se encuentra se cambia el estado a true y se encarga de ser mostrado
                    contacto.setBuscado(true);

                else
                    //En caso contrario lo pone el false que lo oculta
                    contacto.setBuscado(false);

            }
            //Se notifica al ViewPager de los cambios
            pagerAdapter.notifyDataSetChanged();
        }
    }

    public void Verificante(Intent intent) {
        Contacto contacto;
        if (intent != null) {
            //Se verifica si el Intent trae algo
            contacto = intent.getParcelableExtra("ADDED");

            if (contacto != null) {
                //Se agrega al final de la lista
                Contactos.add(contacto);
                //Se limpia para no agregar mas
                getIntent().removeExtra("ADDED");
            }
        }
    }

    //para ver contactos buscados
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            //Se regresar el estado de busque a normal para que los elementos se muestren
            for (Contacto contacto : Contactos)
                contacto.setBuscado(true);
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        return true;
    }


}

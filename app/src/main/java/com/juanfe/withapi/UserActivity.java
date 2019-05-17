package com.juanfe.withapi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.juanfe.withapi.adaptadores.AdaptadorRMisArchivos;
import com.juanfe.withapi.controladoras.ControladoraMisArchivos;
import com.juanfe.withapi.controladoras.ControladoraSubida;


import static com.juanfe.withapi.FragmentActivity.TAG_SWAP_LOG_A;
import static com.juanfe.withapi.FragmentActivity.TAG_SWAP_LOG_E;
import static com.juanfe.withapi.FragmentActivity.TAG_SWAP_LOG_N;
import static com.juanfe.withapi.FragmentActivity.TAG_SWAP_LOG_P;
import static com.juanfe.withapi.FragmentActivity.TAG_SWAP_LOG_U;

public class UserActivity extends AppCompatActivity implements AdaptadorRMisArchivos.OnReciclerListener, ControladoraSubida.OnUpdateListener {


    private static final String TAG_MIAR = "mis archivos";
    private static final int PICKFILE_REQUEST_CODE = 1;
    FrameLayout u_sitio;
    String user,pass,nombre, apellido,email;
    NavigationView nav;
    TextView textheader;
    String letra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        u_sitio = findViewById(R.id.sitiouser);

        user = getIntent().getStringExtra(TAG_SWAP_LOG_U);
        pass = getIntent().getStringExtra(TAG_SWAP_LOG_P);
        nombre = getIntent().getStringExtra(TAG_SWAP_LOG_N);
        apellido = getIntent().getStringExtra(TAG_SWAP_LOG_A);
        email = getIntent().getStringExtra(TAG_SWAP_LOG_E);
        nav = findViewById(R.id.navigation);
        textheader = nav.getHeaderView(0).findViewById(R.id.textheader);
        fragmentMisArchivos();
        acciones();

    }

    private void fragmentMisArchivos() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(u_sitio.getId(), ControladoraMisArchivos.newInstance(user,pass),TAG_MIAR);
        ft.addToBackStack(TAG_MIAR);
        ft.commit();

    }

    private void acciones() {
        cargarLetra();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                switch (menuItem.getItemId()){

                    case R.id.update:
                        ft.add(u_sitio.getId(), ControladoraSubida.newInstance(user,pass),TAG_MIAR);
                        ft.addToBackStack(TAG_MIAR);
                        break;

                    case R.id.misarch:
                        ft.add(u_sitio.getId(), ControladoraMisArchivos.newInstance(user,pass),TAG_MIAR);
                        ft.addToBackStack(TAG_MIAR);
                        break;

                    case R.id.who: break;

                    case R.id.faqs: break;

                    case R.id.bonos: break;

                    case R.id.config: break;


                }
                ft.commit();
                return true;
            }
        });
    }


    private void cargarLetra() {


        if (nombre.toCharArray().length>0) {
            letra = String.valueOf(nombre.charAt(0));
            textheader.setText(letra);

        }else {

            letra = "X";
            textheader.setText(letra);

        }
    }

    @Override
    public void onClickRecycler(String nombre) {
        //hacer llamada descarga de archico mas adelante

    }

    @Override
    public void onUpdateClick(String tipo,String ruta) {

    }

    @Override
    public void onSearchClick() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);


    }
}

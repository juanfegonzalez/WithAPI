package com.juanfe.withapi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        u_sitio = findViewById(R.id.sitiouser);

        user = savedInstanceState.getString(TAG_SWAP_LOG_U);
        pass = savedInstanceState.getString(TAG_SWAP_LOG_P);
        nombre = savedInstanceState.getString(TAG_SWAP_LOG_N);
        apellido = savedInstanceState.getString(TAG_SWAP_LOG_A);
        email = savedInstanceState.getString(TAG_SWAP_LOG_E);
        nav = findViewById(R.id.navigation);
        textheader = nav.findViewById(R.id.textheader);
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

                    case R.id.update: break;

                    case R.id.misarch:
                        ft.add(u_sitio.getId(), ControladoraMisArchivos.newInstance(user,pass),TAG_MIAR);
                        ft.addToBackStack(TAG_MIAR);
                        break;

                    case R.id.who: break;

                    case R.id.faqs: break;

                    case R.id.config: break;
                }
                ft.commit();
                return true;
            }
        });
    }

    private void cargarLetra() {
        char[] letras = nombre.toCharArray();
        textheader.setText(letras[0]);
        //TODO textheader.setTextAlignment();
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
       // startActivityForResult();

    }
}

package com.juanfe.withapi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.juanfe.withapi.adaptadores.AdaptadorBonos;
import com.juanfe.withapi.adaptadores.AdaptadorRMisArchivos;
import com.juanfe.withapi.controladoras.ControladoraBonos;
import com.juanfe.withapi.controladoras.ControladoraMisArchivos;
import com.juanfe.withapi.controladoras.ControladoraSettings;
import com.juanfe.withapi.controladoras.ControladoraSubida;
import com.juanfe.withapi.controladoras.ControladoraWeb;
import com.juanfe.withapi.dialogos.DialogoSettingsGuardar;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.juanfe.withapi.FragmentActivity.TAG_SWAP_LOG_A;
import static com.juanfe.withapi.FragmentActivity.TAG_SWAP_LOG_E;
import static com.juanfe.withapi.FragmentActivity.TAG_SWAP_LOG_I;
import static com.juanfe.withapi.FragmentActivity.TAG_SWAP_LOG_N;
import static com.juanfe.withapi.FragmentActivity.TAG_SWAP_LOG_P;
import static com.juanfe.withapi.FragmentActivity.TAG_SWAP_LOG_T;
import static com.juanfe.withapi.FragmentActivity.TAG_SWAP_LOG_U;
import static com.juanfe.withapi.utils.Constantes.APITOKEN;
import static com.juanfe.withapi.utils.Constantes.CLIENTID;
import static com.juanfe.withapi.utils.Constantes.CLIENTSECRET;
import static com.juanfe.withapi.utils.Constantes.DOMINIO;

public class UserActivity extends AppCompatActivity implements AdaptadorRMisArchivos.OnReciclerListener,
        ControladoraSubida.OnUpdateListener, ControladoraSettings.OnSettingsListener, AdaptadorBonos.OnCheckedListener {


    static final String TAG_MIAR = "mis archivos";
    static final String TAG_SETT = "ajustes";
    static final int PICKFILE_REQUEST_CODE = 1;
    static final String TAG_DIA_SET_SAVE = "dialogo guardar de settings";
    private static final String TAG_WEB = "Vista Web";
    private static final String TAG_BON = "bonos a comprar";
    FrameLayout u_sitio;
    String user,pass,nombre, apellido,email,token;
    int id;
    NavigationView nav;
    TextView textheader;
    String letra;
    Boolean ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        u_sitio = findViewById(R.id.sitiouser);

        user = getIntent().getStringExtra(TAG_SWAP_LOG_U);
        token = getIntent().getStringExtra(TAG_SWAP_LOG_T);
        pass = getIntent().getStringExtra(TAG_SWAP_LOG_P);
        nombre = getIntent().getStringExtra(TAG_SWAP_LOG_N);
        apellido = getIntent().getStringExtra(TAG_SWAP_LOG_A);
        email = getIntent().getStringExtra(TAG_SWAP_LOG_E);
        id = getIntent().getIntExtra(TAG_SWAP_LOG_I,0);
        nav = findViewById(R.id.navigation);
        textheader = nav.getHeaderView(0).findViewById(R.id.textheader);
        fragmentMisArchivos();
        acciones();

    }
    //carga el fragment de mis archivos
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

                    case R.id.bonos:
                        ft.add(u_sitio.getId(), ControladoraBonos.newInstance(user,pass),TAG_BON);
                        ft.addToBackStack(TAG_BON);
                        break;

                    case R.id.config:
                        ft.add(u_sitio.getId(), ControladoraSettings.newInstance(user,pass),TAG_SETT);
                        ft.addToBackStack(TAG_SETT);

                        break;

                    case R.id.menuweb:
                        ft.add(u_sitio.getId(),new ControladoraWeb(),TAG_WEB);
                        ft.addToBackStack(TAG_WEB);



                        break;


                }
                ft.commit();
                return true;
            }
        });
    }

    //carga la letra del usuario en el header del nav
    private void cargarLetra() {


        if (nombre.toCharArray().length>0) {
            letra = String.valueOf(nombre.charAt(0));
            textheader.setText(letra);

        }else {

            letra = "Unknown";
            //TODO poner imagen png de fondo como que no se sabe quien es
            textheader.setText(letra);

        }
    }
    //viene de adaptador de misarchivos
    @Override
    public void onClickRecycler(String nombre) {
        //TODO hacer llamada descarga de archico mas adelante

    }
    //viene de controladorasubida
    @Override
    public void onUpdateClick(String tipo,String ruta) {


    }
    //viene de controladorasubida
    @Override
    public void onSearchClick() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try{
            startActivityForResult(Intent.createChooser(intent,"R.string.nombrearchivo"),PICKFILE_REQUEST_CODE);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //devuelve los resultados de la actividad explorador de archivos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.v("test",(data.getData().toString()));
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //ft.add(u_sitio.getId(), ControladoraMisArchivos.newInstance(user,pass),TAG_MIAR);
        ControladoraSubida cs = (ControladoraSubida) fm.findFragmentByTag(TAG_MIAR);

        cs.setTextRuta(data.getData().getPath());

        ft.commit();



    }

    //viene de controladoraSettings
    @Override
    public void onClickGuardar(String nombre, String apellido, String pass, String correo) {
        enviarJson(user,pass);

        DialogoSettingsGuardar dia = new DialogoSettingsGuardar();
        dia.show(getSupportFragmentManager(),TAG_DIA_SET_SAVE);


    }
    //envio de json para gardar los cambios
    private void enviarJson(String user, String pass) {
        String API = DOMINIO + "usuarios/user/";
        // Log.v("test",API);

        //String API = "http://192.168.43.157:8001/usuarios/login/";
        HashMap<String, String> hm = new HashMap();
        hm.put("username", user);
        hm.put("password", pass);
        JSONObject jsonObject = new JSONObject(hm);


        JsonObjectRequest peticionJSON = new JsonObjectRequest(Request.Method.POST, API+id,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    procesarLogin(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Log.v("test", response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error1",error.toString());
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Access-Control-Allow-Origin", "*");
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return super.getBodyContentType();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(peticionJSON);
    }

    //todo hacer validaciones de correo con  *@*.*

    //envia el JSONTOKEN de los cambios con usuario contra clientid y client secret
    private void enviarJsonToken(String user) {
        String API = APITOKEN;

        String content = "?grant_type=password&username="+user+"&password="+pass+"&client_id="+CLIENTID+"&client_secret="+CLIENTSECRET;



        JsonObjectRequest peticionJSON = new JsonObjectRequest(Request.Method.POST, API+content,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    procesarRespuestaAUTH(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error1",error.toString());
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Access-Control-Allow-Origin", "*");
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return super.getBodyContentType();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(peticionJSON);
    }

    //procesa el json devuelta de los cambios
    private void procesarLogin(JSONObject response) throws JSONException {
        ok = response.getBoolean("ok");

        Log.v("test",response.toString());

        if (ok){
            enviarJsonToken(user);

        }

    }
    //procesa la autenticacion de el token
    private void procesarRespuestaAUTH(JSONObject response) throws JSONException {
        Log.v("test",response.toString());

        if (ok){
            String token = response.getString("access_token");

        }else {

        }

    }
    //viene de adaptador bonos
    @Override
    public void OnCheckedClick(String id) {
        enviarJsonBono(this.id,id);


    }
    // vieene de controladora bonos y envia json de bonos
    private void enviarJsonBono(int userid, String id) {
        String API = DOMINIO + "usuarios/bono_usuario/";


        HashMap<String, String> hm = new HashMap();
        hm.put("usuario", String.valueOf(userid));
        hm.put("bono", id);
        JSONObject jsonObject = new JSONObject(hm);


        JsonObjectRequest peticionJSON = new JsonObjectRequest(Request.Method.POST, API,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    procesarRsBono(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Log.v("test", response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error1",error.toString());
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Access-Control-Allow-Origin", "*");
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return super.getBodyContentType();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(peticionJSON);
    }
    //procesa la respuesta de bonos
    private void procesarRsBono(JSONObject response) throws JSONException {
        Boolean respuesta  = response.getBoolean("ok");
        if (respuesta){
            Toast.makeText(getApplicationContext(),R.string.todook,Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),R.string.todoko,Toast.LENGTH_SHORT).show();
            Log.v("error de bonos","");
        }
    }
}

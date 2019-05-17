package com.juanfe.withapi;


import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.juanfe.withapi.controladoras.ControladorRegistro;
import com.juanfe.withapi.controladoras.ControladoraLogin;
import com.juanfe.withapi.dialogos.DialogoLogin;
import com.juanfe.withapi.dialogos.DialogoRegSi;
import com.juanfe.withapi.utils.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.juanfe.withapi.utils.Constantes.DOMINIO;

public class FragmentActivity extends AppCompatActivity implements ControladorRegistro.OnClickRegCallBack, ControladoraLogin.OnLoginListener, DialogoRegSi.OnDialogoRegListener {

    private static final String TAG_FRG_REG_1 = "Fragment registro desde login";
    FrameLayout site;
    final static String TAG_DIA_REG = "request echa";
    final static String TAG_DIA_LOG_SI = "dialogo login";
    final static String TAG_DIA_LOG_NO = "dialogo login";
    final static String TAG_FRG_LOG_1 = "fragment login";
    final static String TAG_FRG_REG_12 = "fragment registro";
    final static String TAG_SWAP_LOG_U = "usuario";
    final static String TAG_SWAP_LOG_N = "nombre login";
    final static String TAG_SWAP_LOG_A = "apellido login";
    final static String TAG_SWAP_LOG_P = "pass login";
    final static String TAG_SWAP_LOG_E = "email login";
    final static String API = "";

    String usuario, pass,nombre, apellido,email;
    Boolean ok;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        site = findViewById(R.id.site);
        crearPrimerFragment();
    }


    private void crearPrimerFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(site.getId(), new ControladoraLogin(), TAG_FRG_LOG_1);
        ft.addToBackStack(TAG_FRG_LOG_1);
        ft.commit();

    }

    @Override
    public void onRequest(Boolean msn) {
        DialogoRegSi drs = DialogoRegSi.newInstance(msn);
        drs.show(getSupportFragmentManager(), TAG_DIA_REG);
    }

    @Override
    public void onLoginClick(String user, String pass) {
        enviarJson(user, pass);

    }

    @Override
    public void onRegisterClick() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentByTag(TAG_FRG_REG_1) != null)
           ft.hide(fm.findFragmentByTag(TAG_FRG_REG_1));
        ft.add(site.getId(), new ControladorRegistro(),TAG_FRG_REG_12);
        ft.addToBackStack(TAG_FRG_REG_12);
        ft.commit();

    }


    private void enviarJson(String user, String pass) {
        String API = DOMINIO + "usuarios/login/";
        Log.v("test",API);

        //String API = "http://192.168.43.157:8001/usuarios/login/";
        HashMap<String, String> hm = new HashMap();
        hm.put("username", user);
        hm.put("password", pass);
        JSONObject jsonObject = new JSONObject(hm);


        JsonObjectRequest peticionJSON = new JsonObjectRequest(Request.Method.POST, API, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    procesarRespuesta(response);
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
                headers.put("token", "");
                headers.put("Content-Type", "application/json;");
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




    private void procesarRespuesta(JSONObject response) throws JSONException {
        ok = response.getBoolean("ok");

        Log.v("test",response.toString());

        if (ok){
            JSONObject sal = response.getJSONObject("salida");

            usuario = (String) sal.get("username");
            nombre = (String) sal.get("first_name");
            apellido = (String) sal.get("last_name");
            pass = (String) sal.get("password");
            email = (String) sal.get("email");

            Intent i = new Intent(getApplicationContext(),UserActivity.class);
            i.putExtra(TAG_SWAP_LOG_U,usuario);
            i.putExtra(TAG_SWAP_LOG_N,nombre);
            i.putExtra(TAG_SWAP_LOG_A,apellido);
            i.putExtra(TAG_SWAP_LOG_P,pass);
            i.putExtra(TAG_SWAP_LOG_E,email);
            final DialogoLogin dialogoLogin = DialogoLogin.newInstance(ok);
            dialogoLogin.show(getSupportFragmentManager(),TAG_DIA_LOG_SI);

            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {


                }
            },2000);
            dialogoLogin.dismiss();
            startActivity(i);


        }else {
            DialogoLogin dialogoLogin = DialogoLogin.newInstance(ok);
            dialogoLogin.show(getSupportFragmentManager(),TAG_DIA_LOG_NO);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() < 1) {
            finish();
        }
    }

    @Override
    public void onOkclick() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment reg = fm.findFragmentByTag(TAG_FRG_REG_12);
        ft.remove(reg);
        ft.commit();

    }
}

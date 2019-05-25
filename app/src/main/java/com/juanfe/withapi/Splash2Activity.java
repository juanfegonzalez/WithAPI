package com.juanfe.withapi;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Splash2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),FragmentActivity.class);
                startActivity(i);
                finish();


            }
        },2000);
        //MIRAR COMO ALMACENAR EN ARCHIVO DE TEXTO LAS VARIABLES DE CLIENTID Y CLIENTSECRET
    }
}

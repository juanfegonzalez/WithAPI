package com.juanfe.withapi.controladoras;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.juanfe.withapi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.juanfe.withapi.utils.Constantes.DOMINIO;

public class ControladoraQSomos extends Fragment {

    private TextView descripcion;
    Context context;
    private TextView titwa;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.qsomos_content,container,false);
        descripcion = v.findViewById(R.id.textoqs);
        titwa = v.findViewById(R.id.titwa);
        enviarJsonWA();
        return v;
    }

    private void enviarJsonWA() {
        String API = DOMINIO + "contenido/quien-somos/";


        JsonObjectRequest peticionJSON = new JsonObjectRequest(Request.Method.GET, API,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    procesarRsBono(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            public Map<String, String> getHeaders()  {
                final HashMap<String, String> headers = new HashMap<>();
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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(peticionJSON);
    }

    //procesa la respuesta de bonos
    private void procesarRsBono(JSONObject response) throws JSONException {
        boolean ok = response.getBoolean("ok");

        Log.v("test",response.toString());

        if (ok){

            JSONArray sal = response.getJSONArray("salida");
            JSONObject obj = (JSONObject) sal.get(0);
            Log.v("testcon", String.valueOf(obj.get("contenido")));
            descripcion.setText((CharSequence) obj.get("contenido"));
        }

    }
}

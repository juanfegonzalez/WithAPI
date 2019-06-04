package com.juanfe.withapi.controladoras;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.juanfe.withapi.R;
import com.juanfe.withapi.adaptadores.AdaptadorBonos;
import com.juanfe.withapi.utils.Bono;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.juanfe.withapi.utils.Constantes.DOMINIO;

public class ControladoraBonos extends Fragment {
    private static final String TAG_B_USER = "usuario bonos";
    private static final String TAG_B_PASS = "pass bonos";
    Context context;
    String user,pass;
    RecyclerView recybonos;
    AdaptadorBonos adaptadorBonos;
    ArrayList lista;
    String titulo, descripcion;
    int precio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bonos_content,container,false);

        lista =new ArrayList();
        enviarJson();
        Log.v("array", String.valueOf(lista.size()));
        recybonos = v.findViewById(R.id.recyclerBonos);



        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        if (getArguments() != null) {
            user = getArguments().getString(TAG_B_USER);
            pass = getArguments().getString(TAG_B_PASS);

        }
    }


    public static ControladoraBonos newInstance(String user, String pass) {

        Bundle args = new Bundle();
        args.putString(TAG_B_USER, user);
        args.putString(TAG_B_PASS, pass);
        ControladoraBonos fragment = new ControladoraBonos();
        fragment.setArguments(args);
        return fragment;
    }

    private void enviarJson() {
        String API = DOMINIO + "contenido/bonos/";
        // Log.v("test",API);

        //String API = "http://192.168.43.157:8001/usuarios/login/";
//        HashMap<String, String> hm = new HashMap();
//        hm.put("username", user);
//        hm.put("password", pass);
//        JSONObject jsonObject = new JSONObject(hm);


        JsonObjectRequest peticionJSON = new JsonObjectRequest(Request.Method.GET, API, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    procesarDatos(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Log.v("test", response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error1",error.toString());
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(peticionJSON);
    }

    private void procesarDatos(JSONObject response) throws JSONException {

        JSONArray array = response.getJSONArray("salida");
        Log.v("testBonos",response.getJSONArray("salida").toString());
        for (int i = 0; i<= array.length();i++){
            JSONObject object  = array.getJSONObject(i);
            Log.v("testBonos",object.get("titulo").toString());

            Bono b = new Bono(object.getString("titulo"),
                    object.getString("id"),
                    object.getInt("precio"),
                    object.getInt("peticiones"),
                    object.getString("descripcion"));


            lista.add(b);
            Log.v("testBonos", String.valueOf(lista.size()));
            adaptadorBonos = new AdaptadorBonos(lista,context);
            recybonos.setAdapter(adaptadorBonos);
            recybonos.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,
                    false));





        }

    }
}

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
import com.juanfe.withapi.adaptadores.AdaptadorRMisArchivos;
import com.juanfe.withapi.utils.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.juanfe.withapi.utils.Constantes.APITOKEN;
import static com.juanfe.withapi.utils.Constantes.DOMINIO;


public class ControladoraMisArchivos extends Fragment {
    private static final String ID_TAG = "id user";
    private static final String TOK_TAG = "token";
    Context context;
    RecyclerView misarchivos;
    final static String USER_TAG ="usuario";
    final static String PASS_TAG ="pass";
    String user,pass,token;
    int id;
    ArrayList<String> lista_archivos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mis_archivos,container,false);
        misarchivos = v.findViewById(R.id.recyclerArchivos);
        rellenarLista();

        return v;
    }




    private void rellenarLista() {
        lista_archivos = new ArrayList<String>();
        //enviar id y apitoken
        enviarJsonToken(user,pass);



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (getArguments()!= null){
            user = getArguments().getString(USER_TAG);
            pass = getArguments().getString(PASS_TAG);
            id = getArguments().getInt(ID_TAG);
            token = getArguments().getString(TOK_TAG);

        }
    }

    public static ControladoraMisArchivos newInstance(String user,String pass,String token, int id) {
        Bundle b = new Bundle();
        b.putString(USER_TAG,user);
        b.putString(PASS_TAG,pass);
        b.putInt(ID_TAG,id);
        b.putString(TOK_TAG,token);
        ControladoraMisArchivos archivos = new ControladoraMisArchivos();
        archivos.setArguments(b);
        return archivos;
    }

    //ULQNw0csmQpjSms9qfKzwPW7nqJVw6

    private void enviarJsonToken(String user,String pass) {
        String API = DOMINIO+"usuarios/list_files/";

        HashMap<String, String> hm = new HashMap();
        hm.put("id", String.valueOf(id));
        hm.put("username", user);
        hm.put("Autorization", "Bearer "+token);
        JSONObject jsonObject = new JSONObject(hm);



        JsonObjectRequest peticionJSON = new JsonObjectRequest(Request.Method.POST, API,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    procesarRespuestaFiles(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                headers.put("Authorization","Bearer "+ token);
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




    private void procesarRespuestaFiles(JSONObject response) throws JSONException {
        boolean ok = response.getBoolean("ok");

        //Log.v("test",response.toString());

        if (ok){

            JSONArray sal = response.getJSONArray("request");

            for (int i = 0 ; i <sal.length();i++){
                JSONObject obj = (JSONObject) sal.get(i);
                char[] nombre = obj.getString("documento").toCharArray();
                String docu="";
                for (int j = 7; j<nombre.length;j++){
                    docu += nombre[j];
                }
                lista_archivos.add(docu);
                Log.v("lista",lista_archivos.get(i));

            }

            AdaptadorRMisArchivos  adaptador = new AdaptadorRMisArchivos(context,lista_archivos);
            misarchivos.setAdapter(adaptador);
            misarchivos.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,
                    false));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(misarchivos.getContext(),
                    ((LinearLayoutManager) misarchivos.getLayoutManager()).getOrientation());
            misarchivos.addItemDecoration(dividerItemDecoration);


        }
    }


}

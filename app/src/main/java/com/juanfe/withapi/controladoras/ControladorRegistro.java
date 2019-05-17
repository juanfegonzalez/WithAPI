package com.juanfe.withapi.controladoras;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.juanfe.withapi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.juanfe.withapi.utils.Constantes.DOMINIO;

public class ControladorRegistro extends Fragment implements View.OnClickListener {

    EditText usuario, nombre, apellido, pass, pass2, correo, correo2;
    Button registrar;
    Context context;
    OnClickRegCallBack oclireg;

    String ok;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.registro_content, container, false);
        registrar = v.findViewById(R.id.registro);
        usuario = v.findViewById(R.id.editUser);
        nombre = v.findViewById(R.id.editnom);
        apellido = v.findViewById(R.id.editapelli);
        pass = v.findViewById(R.id.editpass);
        pass2 = v.findViewById(R.id.editpasscorr);
        correo = v.findViewById(R.id.editcorreo);
        correo2 = v.findViewById(R.id.editcorreo2);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        acciones();
    }

    private void acciones() {
        registrar.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        oclireg = (OnClickRegCallBack) context;

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == registrar.getId()) {
            /*
            if (pass.getText().toString().isEmpty() || pass2.getText().toString().isEmpty()){
                //Toast.makeText(context,R.string.passvacio,Toast.LENGTH_LONG).show();
                Toast.makeText(context,"pass vacio",Toast.LENGTH_LONG).show();

            }else if (!(pass.getText().toString().equals(pass2.getText().toString()))){
                //Toast.makeText(context,R.string.passvacio,Toast.LENGTH_LONG).show();
                Toast.makeText(context,"pass no es igual",Toast.LENGTH_LONG).show();

            }else if (correo.getText().toString().isEmpty() || correo2.getText().toString().isEmpty()){
                //Toast.makeText(context,R.string.Corrvacio,Toast.LENGTH_LONG).show();
                Toast.makeText(context,"correo vacio",Toast.LENGTH_LONG).show();

            }else if (!(correo.getText().toString().equals(correo2.getText().toString()))){
                //Toast.makeText(context,R.string.Corrvacio,Toast.LENGTH_LONG).show();
                Toast.makeText(context,"correo no es igual",Toast.LENGTH_LONG).show();

            }else {*/
                enviarJson();
                Log.i("test", "haentrado");
                //TODO mirar porque es una referencia nula
                //


           // }
        }
    }

    private void enviarJson() {
        String API = DOMINIO+"usuarios/register/";
        HashMap<String, String> hm = new HashMap();
        hm.put("password",  pass.getText().toString());
        hm.put("username",  usuario.getText().toString() );
        hm.put("first_name",nombre.getText().toString() );
        hm.put("last_name",  apellido.getText().toString() );
        hm.put("email",  correo.getText().toString() );
        JSONObject jsonObject = new JSONObject(hm);




        JsonObjectRequest peticionJSON = new JsonObjectRequest(Request.Method.POST, API, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.v("test", response.toString());
                    procesarRespuesta(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test",error.toString());
                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final HashMap<String,String>  headers= new HashMap<String,String> ();
                headers.put("Access-Control-Allow-Origin", "*");
                headers.put("token","");
                headers.put("Content-Type", "application/json;");
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


        //todo hacer validaciones de correo con  *@*.*


    private void procesarRespuesta(JSONObject response) throws JSONException {
        ok = response.getString("ok");
        Boolean asd = Boolean.valueOf(ok);
        //Log.v("test", ok);
        //Log.v("test", String.valueOf(asd));
        oclireg.onRequest(asd);



    }

    public interface OnClickRegCallBack{
        void onRequest(Boolean msn);
    }

}

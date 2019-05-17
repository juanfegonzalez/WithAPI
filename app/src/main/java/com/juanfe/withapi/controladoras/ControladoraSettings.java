package com.juanfe.withapi.controladoras;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.juanfe.withapi.R;

public class ControladoraSettings extends Fragment {
    private static final String TAG_SET_USER = "usuario settings";
    private static final String TAG_SET_PASS = "pass settings";
    Context context;
    EditText nombre, apellido,editpass,pass2,correo;
    String  user, pass;
    Button guardar;

    OnSettingsListener osl;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_content, container,false);
        instancias(v);
        acciones();


        return v;
    }

    private void acciones() {
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.guardar){
                    if (!(editpass.getText().toString().equals(pass2.getText().toString()))){
                        //Toast.makeText(context,R.string.passvacio,Toast.LENGTH_LONG).show();
                        Toast.makeText(context,"pass no es igual",Toast.LENGTH_LONG).show();

                    }else {
                        osl.onClickGuardar(nombre.getText().toString(),apellido.getText().toString(),
                                editpass.getText().toString(),correo.getText().toString());
                    }
                }
            }
        });
    }


    private void instancias(View v) {
        nombre = v.findViewById(R.id.nomset);
        apellido = v.findViewById(R.id.apelset);
        editpass = v.findViewById(R.id.passset);
        pass2 = v.findViewById(R.id.pass2set);
        correo = v.findViewById(R.id.corset);
        guardar = v.findViewById(R.id.guardar);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        osl = (OnSettingsListener) context;
        if (getArguments()!= null){
            user = getArguments().getString(TAG_SET_USER);
            pass = getArguments().getString(TAG_SET_PASS);
        }
        
    }

    public static ControladoraSettings newInstance(String user, String pass) {

        Bundle args = new Bundle();
        args.putString(TAG_SET_USER, user);
        args.putString(TAG_SET_PASS, pass);
        ControladoraSettings fragment = new ControladoraSettings();
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnSettingsListener{
        void onClickGuardar(String nombre,String apellido,String pass,String correo);
    }
}

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
import android.widget.LinearLayout;

import com.juanfe.withapi.R;

public class ControladoraLogin extends Fragment implements View.OnClickListener {

    EditText usuario, pass;
    Button login,singup;
    Context context;
    OnLoginListener onLoginListener;
    LinearLayout recuperar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_content,container,false);
        usuario = v.findViewById(R.id.user);
        pass = v.findViewById(R.id.pass);
        login = v.findViewById(R.id.login);
        singup = v.findViewById(R.id.singup);
        recuperar = v.findViewById(R.id.recuperar);

        acciones();

        return v;
    }

    private void acciones() {
        login.setOnClickListener(this);
        singup.setOnClickListener(this);
        recuperar.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        onLoginListener = (OnLoginListener) context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.login){
            if (!(usuario.getText().toString().isEmpty()) || !(pass.getText().toString().isEmpty()))
                onLoginListener.onLoginClick(usuario.getText().toString(),pass.getText().toString());

        }else if (v.getId()==R.id.singup){
            onLoginListener.onRegisterClick();

        }else if (v.getId()==R.id.recuperar){
            onLoginListener.onRecordarClick();

        }

    }

    public interface OnLoginListener{
        void onLoginClick(String user,String pass);
        void onRegisterClick();
        void onRecordarClick();
    }
}

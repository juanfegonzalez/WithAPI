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
import android.widget.TextView;

import com.juanfe.withapi.R;


public class ControladoraSubida extends Fragment implements View.OnClickListener {
    private static final String USER_TAG_UP = "user";
    private static final String PASS_TAG_UP = "pass";
    Context context;
    private String user;
    private String pass;

    Button buscar, subir;
    TextView ruta;

    OnUpdateListener oul;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.up_content,container,false);
        instancias(v);
        acciones();
        return v;
    }

    private void acciones() {
        buscar.setOnClickListener(this);
        subir.setOnClickListener(this);
    }

    private void instancias(View v) {
        buscar = v.findViewById(R.id.buscar);
        subir = v.findViewById(R.id.update);
        ruta = v.findViewById(R.id.ruta);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        oul = (OnUpdateListener) context;
        if (getArguments()!=null){
            user = getArguments().getString(USER_TAG_UP);
            pass = getArguments().getString(PASS_TAG_UP);
        }
    }


    public static ControladoraSubida newInstance(String user, String pass) {

        Bundle args = new Bundle();
        args.putString(USER_TAG_UP, user);
        args.putString(PASS_TAG_UP, pass);
        ControladoraSubida fragment = new ControladoraSubida();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buscar: oul.onSearchClick();break;
            case R.id.update: oul.onUpdateClick();break;
        }

    }

    public interface OnUpdateListener{
        void onUpdateClick();
        void onSearchClick();
    }
}

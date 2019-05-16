package com.juanfe.withapi.controladoras;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juanfe.withapi.R;
import com.juanfe.withapi.adaptadores.AdaptadorRMisArchivos;

import java.util.ArrayList;

public class ControladoraMisArchivos extends Fragment {
    Context context;
    RecyclerView misarchivos;
    final static String USER_TAG ="usuario";
    final static String PASS_TAG ="pass";
    String user,pass;
    ArrayList lista_archivos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mis_archivos,container,false);
        misarchivos = v.findViewById(R.id.recyclerArchivos);
        rellenarLista();
        AdaptadorRMisArchivos  adaptador = new AdaptadorRMisArchivos(context,lista_archivos);
        misarchivos.setAdapter(adaptador);
        misarchivos.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,
                false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(misarchivos.getContext(),
                ((LinearLayoutManager) misarchivos.getLayoutManager()).getOrientation());
        misarchivos.addItemDecoration(dividerItemDecoration);
        return v;
    }




    private void rellenarLista() {





    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (getArguments()!= null){
            user = getArguments().getString(USER_TAG);
            pass = getArguments().getString(PASS_TAG);

        }
    }

    public static ControladoraMisArchivos newInstance(String user,String pass) {
        Bundle b = new Bundle();
        b.putString(USER_TAG,user);
        b.putString(PASS_TAG,pass);
        ControladoraMisArchivos archivos = new ControladoraMisArchivos();
        archivos.setArguments(b);
        return archivos;
    }
}

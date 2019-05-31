package com.juanfe.withapi.controladoras;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.juanfe.withapi.R;

public class ControladoraScrappy extends Fragment {

    Context context;
    TextView ruta,titulo;
    EditText nombre;
    Button buscar, subir;
    OnScrappyListener ocl;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        ocl = (OnScrappyListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.up_content,container,false);
        titulo = v.findViewById(R.id.titulo);
        ruta = v.findViewById(R.id.ruta);
        nombre = v.findViewById(R.id.tit);
        buscar = v.findViewById(R.id.buscar);
        subir = v.findViewById(R.id.subir);
        titulo.setText(R.string.Ecommerse);
        acciones();
        return v;
    }

    private void acciones() {
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocl.onScrappyClickSearch();

            }
        });

        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(titulo.getText().toString().isEmpty())) {
                    ocl.onScrappyClickUp(ruta.getText().toString(), titulo.getText().toString());
                }

            }
        });
    }
    public void setTextRutaScrappy(String ruta) {

        this.ruta.setText(ruta);
    }

    public interface OnScrappyListener{
        void onScrappyClickSearch();
        void onScrappyClickUp( String archivo, String nombre);
    }


}

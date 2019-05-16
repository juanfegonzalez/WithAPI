package com.juanfe.withapi.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.juanfe.withapi.R;

import java.util.ArrayList;

public class AdaptadorRMisArchivos extends RecyclerView.Adapter<AdaptadorRMisArchivos.Holder> {

    Context context;
    ArrayList lista;
    OnReciclerListener orl;

    public AdaptadorRMisArchivos(Context context, ArrayList lista) {
        this.context = context;
        this.lista = lista;
        orl = (OnReciclerListener) context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_recycler, viewGroup,false);
        Holder h = new Holder(v);

        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final String nombre = (String) lista.get(i);
        holder.getNombreArchivo().setText(nombre);
        holder.getDescarga().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orl.onClickRecycler(nombre);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView nombreArchivo;
        Button descarga;

        public TextView getNombreArchivo() {
            return nombreArchivo;
        }

        public Button getDescarga() {
            return descarga;
        }

        public Holder(@NonNull View itemView) {
            super(itemView);

            nombreArchivo = itemView.findViewById(R.id.nombreArchivo);
            descarga = itemView.findViewById(R.id.descarga);
        }
    }

    public interface OnReciclerListener{
        void onClickRecycler(String nombre);
    }
}

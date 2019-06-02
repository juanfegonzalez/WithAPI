package com.juanfe.withapi.adaptadores;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
        View v = LayoutInflater.from(context).inflate(R.layout.item_recycler, viewGroup, false);
        Holder h = new Holder(v);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final String nombre = (String) lista.get(i);
        holder.getNombreArchivo().setText(nombre);
        holder.getLinear().setOnClickListener(new View.OnClickListener() {
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
        LinearLayout linear;

        public TextView getNombreArchivo() {
            return nombreArchivo;
        }


        public Holder(@NonNull View itemView) {
            super(itemView);

            nombreArchivo = itemView.findViewById(R.id.nombreArchivo);
            linear = itemView.findViewById(R.id.line1);
        }

        public LinearLayout getLinear() {
            return linear;
        }
    }

    public interface OnReciclerListener {
        void onClickRecycler(String nombre);
    }
}

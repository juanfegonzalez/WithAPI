package com.juanfe.withapi.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.behavior.HideBottomViewOnScrollBehavior;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.juanfe.withapi.R;
import com.juanfe.withapi.utils.Bono;

import java.util.ArrayList;

public class AdaptadorBonos extends RecyclerView.Adapter<AdaptadorBonos.HolderB> {

    ArrayList lista;
    Context context;
    OnCheckedListener ocl;

    public AdaptadorBonos(ArrayList lista, Context context) {
        this.lista = lista;
        this.context = context;
        ocl = (OnCheckedListener) context;
    }

    @NonNull
    @Override
    public HolderB onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.bonos_items,viewGroup,false);
        HolderB h = new HolderB(v);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderB holderB, int i) {

        final Bono b = (Bono) lista.get(i);
        holderB.getCantidad().setText(b.getCantidad());
        holderB.getPrecio().setText(b.getPrecio());
        holderB.getContratado().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocl.OnCheckedClick(b.getCantidad(),b.getPrecio(),b.getNombre());
            }
        });

    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    class HolderB extends RecyclerView.ViewHolder {
        TextView precio,cantidad;
        Button contratado;

        public HolderB(@NonNull View itemView) {
            super (itemView);

            precio = itemView.findViewById(R.id.precio);
            cantidad = itemView.findViewById(R.id.cantidad);
            contratado = itemView.findViewById(R.id.checkB);

        }

        public TextView getPrecio() {
            return precio;
        }

        public TextView getCantidad() {
            return cantidad;
        }

        public Button getContratado() {
            return contratado;
        }
    }

    public interface OnCheckedListener{
        void OnCheckedClick(int cantidad, int precio, String nombre);

    }
}

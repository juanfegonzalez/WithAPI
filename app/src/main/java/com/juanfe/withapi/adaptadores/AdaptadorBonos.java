package com.juanfe.withapi.adaptadores;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holderB.getDescripcion().setText(b.getDescripcion());
        holderB.getTitulo().setText(b.getNombre());
        holderB.getLinear().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocl.OnCheckedClick(b.getId());
            }
        });

    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    class HolderB extends RecyclerView.ViewHolder {
        TextView precio,cantidad,titulo, descripcion;
        CardView linear;


        public HolderB(@NonNull View itemView) {
            super (itemView);

            precio = itemView.findViewById(R.id.precio);
            cantidad = itemView.findViewById(R.id.cantidad);
            titulo = itemView.findViewById(R.id.titulo);
            descripcion = itemView.findViewById(R.id.descripcion);
            linear = itemView.findViewById(R.id.card);
        }

        public TextView getPrecio() {
            return precio;
        }

        public TextView getCantidad() {
            return cantidad;
        }

        public TextView getTitulo() {
            return titulo;
        }

        public TextView getDescripcion() {
            return descripcion;
        }

        public CardView getLinear() {
            return linear;
        }
    }

    public interface OnCheckedListener{
        void OnCheckedClick(String id);

    }
}

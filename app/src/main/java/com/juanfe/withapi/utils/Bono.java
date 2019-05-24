package com.juanfe.withapi.utils;

public class Bono {
    String nombre,id,descripcion;
    int precio,cantidad;

    public Bono(String nombre,String id, int precio, int cantidad,String descripcion ) {
        this.id=id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

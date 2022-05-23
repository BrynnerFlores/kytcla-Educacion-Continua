package com.brynnerflores.kytcla.model.POJO;

import java.io.Serializable;

public class Producto implements Serializable {

    // region Variables

    private int codigo_producto;
    private String categoria_producto;
    private String foto_producto;
    private String nombre;
    private String descripcion;
    private String precio;
    private boolean estado;

    // endregion

    // region Constructor

    public Producto(int codigo_producto, String categoria_producto, String foto_producto, String nombre, String descripcion, String precio, boolean estado) {
        this.codigo_producto = codigo_producto;
        this.categoria_producto = categoria_producto;
        this.foto_producto = foto_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
    }

    public Producto(String categoria_producto, String foto_producto, String nombre, String descripcion, String precio, boolean estado) {
        this.categoria_producto = categoria_producto;
        this.foto_producto = foto_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
    }

    // endregion

    // region Getters and Setters

    public int getCodigoProducto() {
        return codigo_producto;
    }

    public void setCodigoProducto(int codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public String getCategoriaProducto() {
        return categoria_producto;
    }

    public void setCategoriaProducto(String categoria_producto) {
        this.categoria_producto = categoria_producto;
    }

    public String getFotoProducto() {
        return foto_producto;
    }

    public void setFotoProducto(String foto_producto) {
        this.foto_producto = foto_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    // endregion

}

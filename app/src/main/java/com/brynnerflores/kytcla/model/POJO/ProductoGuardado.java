package com.brynnerflores.kytcla.model.POJO;

import java.io.Serializable;

public class ProductoGuardado implements Serializable {

    // region Variables

    private int codigo_producto_guardado;
    private Cuenta cuenta;
    private Producto producto;
    private String fecha_guardado;
    private boolean estado;

    // endregion

    // region Constructor

    public ProductoGuardado(int codigo_producto_guardado, Cuenta cuenta, Producto producto, String fecha_guardado, boolean estado) {
        this.codigo_producto_guardado = codigo_producto_guardado;
        this.cuenta = cuenta;
        this.producto = producto;
        this.fecha_guardado = fecha_guardado;
        this.estado = estado;
    }

    public ProductoGuardado(Cuenta cuenta, Producto producto, String fecha_guardado, boolean estado) {
        this.cuenta = cuenta;
        this.producto = producto;
        this.fecha_guardado = fecha_guardado;
        this.estado = estado;
    }

    // endregion

    // region Getters and Setters

    public int getCodigoProductoGuardado() {
        return codigo_producto_guardado;
    }

    public void setCodigoProductoGuardado(int codigo_producto_guardado) {
        this.codigo_producto_guardado = codigo_producto_guardado;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getFechaPublicado() {
        return fecha_guardado;
    }

    public void setFechaGuardado(String fecha_guardado) {
        this.fecha_guardado = fecha_guardado;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    // endregion
}
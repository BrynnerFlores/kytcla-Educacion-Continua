package com.brynnerflores.kytcla.model.POJO;

import java.io.Serializable;

public class ProductoPersonalizado implements Serializable {

    // region Variables

    private Producto producto;
    private boolean guardado;

    // endregion

    // region Constructor

    public ProductoPersonalizado(Producto producto, boolean guardado) {
        this.producto = producto;
        this.guardado = guardado;
    }

    // endregion

    // region Getters and Setters

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    // endregion
}
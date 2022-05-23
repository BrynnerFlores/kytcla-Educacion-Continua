package com.brynnerflores.kytcla.model.POJO;

import java.io.Serializable;

public class Cuenta implements Serializable {

    // region Variables

    private int codigo_cuenta;
    private Persona persona;
    private String correo_electronico;
    private String password;
    private boolean estado;

    // endregion

    // region Constructor

    public Cuenta(int codigo_cuenta, Persona persona, String correo_electronico, String password, boolean estado) {
        this.codigo_cuenta = codigo_cuenta;
        this.persona = persona;
        this.correo_electronico = correo_electronico;
        this.password = password;
        this.estado = estado;
    }

    public Cuenta(Persona persona, String correo_electronico, String password, boolean estado) {
        this.persona = persona;
        this.correo_electronico = correo_electronico;
        this.password = password;
        this.estado = estado;
    }

    // endregion

    // region Getters ans Setters

    public int getCodigoCuenta() {
        return codigo_cuenta;
    }

    public void setCodigoCuenta(int codigo_cuenta) {
        this.codigo_cuenta = codigo_cuenta;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getCorreoElectronico() {
        return correo_electronico;
    }

    public void setCorreoElectronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    // endregion

}

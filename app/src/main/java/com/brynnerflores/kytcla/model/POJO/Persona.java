package com.brynnerflores.kytcla.model.POJO;

import java.io.Serializable;

public class Persona implements Serializable {

    // region Variables

    private int codigo_persona;
    private String foto;
    private String nombre;
    private String apellido;
    private String fecha_nacimiento;
    private String sexo;
    private String pais;
    private String ciudad;
    private String direccion_domicilio;
    private String presentacion;
    private boolean estado;

    // endregion

    // region Constructor

    public Persona(int codigo_persona, String foto, String nombre, String apellido, String fecha_nacimiento, String sexo, String pais, String ciudad, String direccion_domicilio, String presentacion, boolean estado) {
        this.codigo_persona = codigo_persona;
        this.foto = foto;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nacimiento = fecha_nacimiento;
        this.sexo = sexo;
        this.pais = pais;
        this.ciudad = ciudad;
        this.direccion_domicilio = direccion_domicilio;
        this.presentacion = presentacion;
        this.estado = estado;
    }

    public Persona(String foto, String nombre, String apellido, String fecha_nacimiento, String sexo, String pais, String ciudad, String direccion_domicilio, String presentacion, boolean estado) {
        this.foto = foto;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nacimiento = fecha_nacimiento;
        this.sexo = sexo;
        this.pais = pais;
        this.ciudad = ciudad;
        this.direccion_domicilio = direccion_domicilio;
        this.presentacion = presentacion;
        this.estado = estado;
    }

    // endregion

    // region Getters and Setters

    public int getCodigoPersona() {
        return codigo_persona;
    }

    public void setCodigoPersona(int codigo_persona) {
        this.codigo_persona = codigo_persona;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {
        return fecha_nacimiento;
    }

    public void setFechaNacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccionDomicilio() {
        return direccion_domicilio;
    }

    public void setDireccionDomicilio(String direccion_domicilio) {
        this.direccion_domicilio = direccion_domicilio;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    // endregion

}
package com.brynnerflores.kytcla.model.POJO;

import java.io.Serializable;

public class CursoGuardado implements Serializable {

    // region Variables

    private int codigo_curso_guardado;
    private Cuenta cuenta;
    private Curso curso;
    private String fecha_guardado;
    private boolean estado;

    // endregion

    // region Constructor

    public CursoGuardado(int codigo_curso_guardado, Cuenta cuenta, Curso curso, String fecha_guardado, boolean estado) {
        this.codigo_curso_guardado = codigo_curso_guardado;
        this.cuenta = cuenta;
        this.curso = curso;
        this.fecha_guardado = fecha_guardado;
        this.estado = estado;
    }

    public CursoGuardado(Cuenta cuenta, Curso curso, String fecha_guardado, boolean estado) {
        this.cuenta = cuenta;
        this.curso = curso;
        this.fecha_guardado = fecha_guardado;
        this.estado = estado;
    }

    // endregion

    // region Getters and Setters

    public int getCodigoCursoGuardado() {
        return codigo_curso_guardado;
    }

    public void setCodigoCursoGuardado(int codigo_curso_guardado) {
        this.codigo_curso_guardado = codigo_curso_guardado;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getFechaGuardado() {
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
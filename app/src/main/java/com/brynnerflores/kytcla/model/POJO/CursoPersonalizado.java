package com.brynnerflores.kytcla.model.POJO;

import java.io.Serializable;

public class CursoPersonalizado implements Serializable {

    // region Variables

    private Curso curso;
    private boolean guardado;

    // endregion

    // region Constructor

    public CursoPersonalizado(Curso curso, boolean guardado) {
        this.curso = curso;
        this.guardado = guardado;
    }

    // endregion

    // region Getters and Setters

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    // endregion

}

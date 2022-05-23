package com.brynnerflores.kytcla.model.POJO;

import java.io.Serializable;

public class Curso implements Serializable {

    // region Variables

    private int codigo_curso_publicado;
    private String logo_curso;
    private String area_curso;
    private String nombre;
    private String objetivo;
    private String dirigido;
    private String dictado;
    private String contenidos;
    private String fecha_inicio;
    private String horarios;
    private String duracion;
    private String modalidad;
    private String costo_curso;
    private String enlace_inscripcion;
    private boolean estado;

    // endregion

    // region Constructor

    public Curso(int codigo_curso_publicado, String logo_curso, String area_curso, String nombre, String objetivo, String dirigido, String dictado, String contenidos, String fecha_inicio, String horarios, String duracion, String modalidad, String costo_curso, String enlace_inscripcion, boolean estado) {
        this.codigo_curso_publicado = codigo_curso_publicado;
        this.logo_curso = logo_curso;
        this.area_curso = area_curso;
        this.nombre = nombre;
        this.objetivo = objetivo;
        this.dirigido = dirigido;
        this.dictado = dictado;
        this.contenidos = contenidos;
        this.fecha_inicio = fecha_inicio;
        this.horarios = horarios;
        this.duracion = duracion;
        this.modalidad = modalidad;
        this.costo_curso = costo_curso;
        this.enlace_inscripcion = enlace_inscripcion;
        this.estado = estado;
    }

    public Curso(String logo_curso, String area_curso, String nombre, String objetivo, String dirigido, String dictado, String contenidos, String fecha_inicio, String horarios, String duracion, String modalidad, String costo_curso, String enlace_inscripcion, boolean estado) {
        this.logo_curso = logo_curso;
        this.area_curso = area_curso;
        this.nombre = nombre;
        this.objetivo = objetivo;
        this.dirigido = dirigido;
        this.dictado = dictado;
        this.contenidos = contenidos;
        this.fecha_inicio = fecha_inicio;
        this.horarios = horarios;
        this.duracion = duracion;
        this.modalidad = modalidad;
        this.costo_curso = costo_curso;
        this.enlace_inscripcion = enlace_inscripcion;
        this.estado = estado;
    }

    // endregion

    // region Getters and Setters

    public int getCodigoCursoPublicado() {
        return codigo_curso_publicado;
    }

    public void setCodigoCursoPublicado(int codigo_curso_publicado) {
        this.codigo_curso_publicado = codigo_curso_publicado;
    }

    public String getLogoCurso() {
        return logo_curso;
    }

    public void setLogoCurso(String logo_curso) {
        this.logo_curso = logo_curso;
    }

    public String getAreaCurso() {
        return area_curso;
    }

    public void setAreaCurso(String area_curso) {
        this.area_curso = area_curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getDirigido() {
        return dirigido;
    }

    public void setDirigido(String dirigido) {
        this.dirigido = dirigido;
    }

    public String getDictado() {
        return dictado;
    }

    public void setDictado(String dictado) {
        this.dictado = dictado;
    }

    public String getContenidos() {
        return contenidos;
    }

    public void setContenidos(String contenidos) {
        this.contenidos = contenidos;
    }

    public String getFechaInicio() {
        return fecha_inicio;
    }

    public void setFechaInicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getCostoCurso() {
        return costo_curso;
    }

    public void setCostoCurso(String costo_curso) {
        this.costo_curso = costo_curso;
    }

    public String getEnlaceInscripcion() {
        return enlace_inscripcion;
    }

    public void setEnlaceInscripcion(String enlace_inscripcion) {
        this.enlace_inscripcion = enlace_inscripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    // endregion
}
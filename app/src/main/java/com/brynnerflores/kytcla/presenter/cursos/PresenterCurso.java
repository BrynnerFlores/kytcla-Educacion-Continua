package com.brynnerflores.kytcla.presenter.cursos;

import android.content.Context;

import com.brynnerflores.kytcla.model.ModelCurso;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.Curso;
import com.brynnerflores.kytcla.model.POJO.CursoGuardado;

import java.util.ArrayList;

public class PresenterCurso implements ModelCurso.CallBackModelListarCursos, ModelCurso.CallBackModelInsertarCurso, ModelCurso.CallBackModelActualizarCurso, ModelCurso.CallBackModelGuardarCurso, ModelCurso.CallBackModelListarCursosGuardados, ModelCurso.CallBackModelEliminarCurso {

    // region Variables

    private final Context context;
    private ModelCurso modelCurso;
    private CallBackObtenerCursos callBackObtenerCursos;
    private CallBackCrearCurso callBackCrearCurso;
    private CallBackModificarCurso callBackModificarCurso;
    private CallBackGuardarCurso callBackGuardarCurso;
    private CallBackObtenerCursosGuardados callBackObtenerCursosGuardados;
    private CallBackEliminarCurso callBackEliminarCurso;

    // endregion

    // region Constructor

    public PresenterCurso(final Context context) {
        this.context = context;
    }

    // endregion

    // region Getters and Setters

    public void setCallBackObtenerCursos(CallBackObtenerCursos callBackObtenerCursos) {
        this.callBackObtenerCursos = callBackObtenerCursos;
    }

    public void setCallBackCrearCurso(CallBackCrearCurso callBackCrearCurso) {
        this.callBackCrearCurso = callBackCrearCurso;
    }

    public void setCallBackModificarCurso(CallBackModificarCurso callBackModificarCurso) {
        this.callBackModificarCurso = callBackModificarCurso;
    }

    public void setCallBackGuardarCurso(CallBackGuardarCurso callBackGuardarCurso) {
        this.callBackGuardarCurso = callBackGuardarCurso;
    }

    public void setCallBackObtenerCursosGuardados(CallBackObtenerCursosGuardados callBackObtenerCursosGuardados) {
        this.callBackObtenerCursosGuardados = callBackObtenerCursosGuardados;
    }

    public void setCallBackEliminarCurso(CallBackEliminarCurso callBackEliminarCurso) {
        this.callBackEliminarCurso = callBackEliminarCurso;
    }
    
    // endregion

    // region Metodos

    public void obtenerCursos() {
        try {
            modelCurso = new ModelCurso(context);
            modelCurso.setCallBackModelListarCursos(this);
            modelCurso.listarCursos();
        } catch (final Exception exception) {
            callBackObtenerCursos.errorDesconocidoObtenerCursos("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    public void crearCurso(final Curso curso) {
        try {
            modelCurso = new ModelCurso(context);
            modelCurso.setCallBackModelInsertarCurso(this);
            modelCurso.insertar(curso);
        } catch (final Exception exception) {
            callBackCrearCurso.errorCrearCurso("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    public void modificarCurso(final Curso curso) {
        try {
            modelCurso = new ModelCurso(context);
            modelCurso.setCallBackModelActualizarCurso(this);
            modelCurso.actualizar(curso);
        } catch (final Exception exception) {
            callBackModificarCurso.errorDesconocidoModificarCurso("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    public void guardarCurso(final Cuenta cuenta, final Curso curso) {
        try {
            modelCurso = new ModelCurso(context);
            modelCurso.setCallBackModelGuardarCurso(this);
            modelCurso.guardar(cuenta, curso);
        } catch (final Exception exception) {
            callBackGuardarCurso.errorDesconocidoGuardarCurso("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    public void obtenerCursosGuardados(final Cuenta cuenta) {
        try {
            modelCurso = new ModelCurso(context);
            modelCurso.setCallBackModelListarCursosGuardados(this);
            modelCurso.listarCursosGuardados(cuenta);
        } catch (final Exception exception) {
            callBackObtenerCursosGuardados.errorDesconocidoObtenerCursosGuardados("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }
    
    public void eliminar(final Curso curso) {
        try {
            modelCurso = new ModelCurso(context);
            modelCurso.setCallBackModelEliminarCurso(this);
            modelCurso.eliminar(curso);
        } catch (final Exception exception) {
            callBackEliminarCurso.errorDesconocidoEliminarCurso("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    // endregion

    // region CallBackModel

    @Override
    public void cursosObtenidos(final ArrayList<Curso> cursos) {
        if (cursos == null) {
            callBackObtenerCursos.errorObtenerCursos("Se produjo un error al obtener los cursos.");
        } else if (cursos.isEmpty()) {
            callBackObtenerCursos.listaCursosVacia("No hay cursos registrados.");
        } else {
            callBackObtenerCursos.cursosObtenidos(cursos);
        }
    }

    @Override
    public void cursoInsertado(final String code_response) {
        switch (code_response) {
            case "INSERTADO":
                callBackCrearCurso.cursoCreado("Curso creado!");
                break;

            case "NO_INSERTADO":
                callBackCrearCurso.errorCrearCurso("Se produjo un error al crear el curso.");
                break;

            case "CURSO_EXISTE":
                callBackCrearCurso.cursoExiste("Ya existe un curso con el mismo nombre.");
                break;

            default:
                callBackCrearCurso.errorDesconocidoCrearCurso("Se produjo un error desconocido, vuelve a intentarlo.");
                break;
        }
    }

    @Override
    public void responseActualizarCurso(final String code_response) {
        switch (code_response) {
            case "ACTUALIZADO":
                callBackModificarCurso.cursoModificado("Curso modificado.");
                break;

            case "NO_ACTUALIZADO":
                callBackModificarCurso.errorModificarCurso("Se produjo un error al modificar el curso.");
                break;

            case "CURSO_EXISTE":
                callBackModificarCurso.cursoExiste("Ya existe un curso con el mismo nombre.");
                break;

            default:
                callBackModificarCurso.errorDesconocidoModificarCurso("Se produjo un error desconocido, vuelve a intentarlo.");
                break;
        }
    }

    @Override
    public void cursoGuardado(final String code_response) {
        switch (code_response) {
            case "INSERTADO":
                callBackGuardarCurso.cursoGuardado("Curso Guardado.");
                break;

            case "NO_INSERTADO":
                callBackGuardarCurso.errorGuardarCurso("Se produjo un error al guardar el curso.");
                break;

            default:
                callBackGuardarCurso.errorDesconocidoGuardarCurso("Se produjo un error desconocido, vuelve a intentarlo.");
                break;
        }
    }

    @Override
    public void cursosGuardadosObtenidos(final ArrayList<CursoGuardado> cursosGuardados) {
        if (cursosGuardados == null) {
            callBackObtenerCursosGuardados.errorObtenerCursosGuardados("Se produjo un error al obtener los cursos.");
        } else if (cursosGuardados.isEmpty()) {
            callBackObtenerCursosGuardados.listaCursosGuardadosVacia("No hay cursos registrados.");
        } else {
            callBackObtenerCursosGuardados.cursosGuardadosObtenidos(cursosGuardados);
        }
    }

    @Override
    public void responseEliminarCurso(final String code_response) {
        switch (code_response) {
            case "ACTUALIZADO":
                callBackEliminarCurso.cursoEliminado("Curso eliminado.");
                break;

            case "NO_ACTUALIZADO":
                callBackEliminarCurso.errorEliminarCurso("Se produjo un error al eliminar el curso.");
                break;

            default:
                callBackEliminarCurso.errorDesconocidoEliminarCurso("Se produjo un error desconocido, vuelve a intentarlo.");
                break;
        }
    }


    // endregion

    // region Interfaces

    public interface CallBackObtenerCursos {
        void cursosObtenidos(final ArrayList<Curso> cursos);

        void listaCursosVacia(final String msg);

        void errorObtenerCursos(final String msg);

        void errorDesconocidoObtenerCursos(final String msg);
    }

    public interface CallBackCrearCurso {
        void cursoCreado(final String msg);

        void cursoExiste(final String msg);

        void errorCrearCurso(final String msg);

        void errorDesconocidoCrearCurso(final String msg);
    }

    public interface CallBackModificarCurso {
        void cursoModificado(final String msg);

        void cursoExiste(final String msg);

        void errorModificarCurso(final String msg);

        void errorDesconocidoModificarCurso(final String msg);
    }

    public interface CallBackGuardarCurso {
        void cursoGuardado(final String msg);

        void errorGuardarCurso(final String msg);

        void errorDesconocidoGuardarCurso(final String msg);
    }

    public interface CallBackObtenerCursosGuardados {
        void cursosGuardadosObtenidos(final ArrayList<CursoGuardado> cursosGuardados);

        void listaCursosGuardadosVacia(final String msg);

        void errorObtenerCursosGuardados(final String msg);

        void errorDesconocidoObtenerCursosGuardados(final String msg);
    }

    public interface CallBackEliminarCurso {
        void cursoEliminado(final String msg);

        void errorEliminarCurso(final String msg);

        void errorDesconocidoEliminarCurso(final String msg);
    }

    // endregion

}
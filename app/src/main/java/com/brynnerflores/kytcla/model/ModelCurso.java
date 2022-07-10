package com.brynnerflores.kytcla.model;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.Curso;
import com.brynnerflores.kytcla.model.POJO.CursoPersonalizado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModelCurso {

    // region Variables

    private final Context context;
    private CallBackModelListarCursos callBackModelListarCursos;
    private CallBackModelInsertarCurso callBackModelInsertarCurso;
    private CallBackModelActualizarCurso callBackModelActualizarCurso;
    private CallBackModelGuardarCurso callBackModelGuardarCurso;
    private CallBackModelListarCursosGuardados callBackModelListarCursosGuardados;
    private CallBackModelEliminarCurso callBackModelEliminarCurso;
    private CallBackModelEliminarCursoGuardado callBackModelEliminarCursoGuardado;

    // endregion

    // region Constructor

    public ModelCurso(final Context context) {
        this.context = context;
    }

    // endregion

    // region Getters and Setters

    public void setCallBackModelListarCursos(CallBackModelListarCursos callBackModelListarCurso) {
        this.callBackModelListarCursos = callBackModelListarCurso;
    }

    public void setCallBackModelInsertarCurso(CallBackModelInsertarCurso callBackModelInsertarCurso) {
        this.callBackModelInsertarCurso = callBackModelInsertarCurso;
    }

    public void setCallBackModelActualizarCurso(CallBackModelActualizarCurso callBackModelActualizarCurso) {
        this.callBackModelActualizarCurso = callBackModelActualizarCurso;
    }

    public void setCallBackModelGuardarCurso(CallBackModelGuardarCurso callBackModelGuardarCurso) {
        this.callBackModelGuardarCurso = callBackModelGuardarCurso;
    }

    public void setCallBackModelListarCursosGuardados(CallBackModelListarCursosGuardados callBackModelListarCursosGuardados) {
        this.callBackModelListarCursosGuardados = callBackModelListarCursosGuardados;
    }

    public void setCallBackModelEliminarCurso(CallBackModelEliminarCurso callBackModelEliminarCurso) {
        this.callBackModelEliminarCurso = callBackModelEliminarCurso;
    }

    public void setCallBackModelEliminarCursoGuardado(CallBackModelEliminarCursoGuardado callBackModelEliminarCursoGuardado) {
        this.callBackModelEliminarCursoGuardado = callBackModelEliminarCursoGuardado;
    }

    // endregion

    // region Metodos

    public void listarCursos() {
        try {
            RequestQueue requestQueue;
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();

            final String url = "https://www.kytcla.com/app/cursos/listar_cursos.php";
            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, new JSONArray(),
                    response -> {
                        ArrayList<CursoPersonalizado> cursosPersonalizados = new ArrayList<>();
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    final JSONObject jsonObject = response.getJSONObject(i);
                                    final int codigo_curso_publicado = jsonObject.getInt("id");
                                    final String logo = jsonObject.getString("logo");
                                    final String area = jsonObject.getString("area");
                                    final String nombre = jsonObject.getString("nombre");
                                    final String objetivo = jsonObject.getString("objetivo");
                                    final String dirigido = jsonObject.getString("dirigido");
                                    final String dictado = jsonObject.getString("dictado");
                                    final String contenidos = jsonObject.getString("contenidos");
                                    final String fecha_inicio = jsonObject.getString("fecha_inicio");
                                    final String horario = jsonObject.getString("horario");
                                    final String duracion = jsonObject.getString("duracion");
                                    final String modalidad = jsonObject.getString("modalidad");
                                    final String costo_curso = jsonObject.getString("costo_curso");
                                    final String enlace_inscripcion = jsonObject.getString("enlace_inscripcion");
                                    final boolean estado = jsonObject.getString("estado").equals("1");
                                    final boolean curso_guardado = jsonObject.getString("curso_guardado").equals("1");

                                    final Curso curso = new Curso(codigo_curso_publicado, logo, area, nombre, objetivo, dirigido, dictado, contenidos, fecha_inicio, horario, duracion, modalidad, costo_curso, enlace_inscripcion, estado);
                                    final CursoPersonalizado cursoPersonalizado = new CursoPersonalizado(curso, curso_guardado);
                                    cursosPersonalizados.add(cursoPersonalizado);
                                } catch (JSONException exception) {
                                    exception.printStackTrace();
                                    cursosPersonalizados = null;
                                }
                            }
                        }
                        callBackModelListarCursos.cursosObtenidos(cursosPersonalizados);
                    },
                    error -> {
                        callBackModelListarCursos.cursosObtenidos(null);
                    });
            requestQueue.add(jsonArrayRequest);
        } catch (final Exception exception) {
            callBackModelListarCursos.cursosObtenidos(null);
        }
    }

    public void insertar(final Curso curso) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        final String url = "https://www.kytcla.com/app/cursos/crear_curso.php";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        final JSONArray jsonArray = new JSONArray(response);
                        final JSONObject jsonObject = jsonArray.getJSONObject(0);
                        switch (jsonObject.getString("RESULT")) {
                            case "INSERTADO":
                                callBackModelInsertarCurso.cursoInsertado("INSERTADO");
                                break;

                            case "NO_INSERTADO":
                                callBackModelInsertarCurso.cursoInsertado("NO_INSERTADO");
                                break;

                            case "CURSO_EXISTE":
                                callBackModelInsertarCurso.cursoInsertado("CURSO_EXISTE");
                                break;

                            default:
                                break;
                        }
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                        callBackModelInsertarCurso.cursoInsertado("ERROR");
                    }
                },
                error -> {
                    callBackModelInsertarCurso.cursoInsertado("ERROR");
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("logo", curso.getLogoCurso());
                params.put("area", curso.getAreaCurso());
                params.put("nombre", curso.getNombre());
                params.put("objetivo", curso.getObjetivo());
                params.put("dirigido", curso.getDirigido());
                params.put("dictado", curso.getDictado());
                params.put("contenidos", curso.getContenidos());
                params.put("fecha_inicio", curso.getFechaInicio());
                params.put("horario", curso.getHorarios());
                params.put("duracion", curso.getDuracion());
                params.put("modalidad", curso.getModalidad());
                params.put("costo_curso", curso.getCostoCurso());
                params.put("enlace_inscripcion", curso.getEnlaceInscripcion());
                params.put("estado", curso.isEstado() == true ? "T" : "F");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void actualizar(final Curso curso) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        final String url = "https://www.kytcla.com/app/cursos/actualizar_curso.php";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        final JSONArray jsonArray = new JSONArray(response);
                        callBackModelActualizarCurso.responseActualizarCurso(jsonArray.getJSONObject(0).getString("RESPONSE"));
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                        callBackModelActualizarCurso.responseActualizarCurso("ERROR_DESCONOCIDO");
                    }
                },
                error -> {
                    callBackModelActualizarCurso.responseActualizarCurso("ERROR");
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("codigo_curso_publicado", String.valueOf(curso.getCodigoCursoPublicado()));
                params.put("logo", curso.getLogoCurso());
                params.put("area", curso.getAreaCurso());
                params.put("nombre", curso.getNombre());
                params.put("objetivo", curso.getObjetivo());
                params.put("dirigido", curso.getDirigido());
                params.put("dictado", curso.getDictado());
                params.put("contenidos", curso.getContenidos());
                params.put("fecha_inicio", curso.getFechaInicio());
                params.put("horario", curso.getHorarios());
                params.put("duracion", curso.getDuracion());
                params.put("modalidad", curso.getModalidad());
                params.put("costo_curso", curso.getCostoCurso());
                params.put("enlace_inscripcion", curso.getEnlaceInscripcion());
                params.put("estado", curso.isEstado() == true ? "T" : "F");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void guardar(final Cuenta cuenta, final Curso curso) {
        try {
            RequestQueue requestQueue;
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();

            final String url = "https://www.kytcla.com/app/cursos/guardar_curso.php";
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            final JSONArray jsonArray = new JSONArray(response);
                            callBackModelGuardarCurso.cursoGuardado(jsonArray.getJSONObject(0).getString("RESPONSE"));
                        } catch (final JSONException jsonException) {
                            callBackModelGuardarCurso.cursoGuardado("ERROR_DESCONOCIDO");
                        }
                    },
                    error -> {
                        callBackModelGuardarCurso.cursoGuardado("ERROR");
                    }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    final Map<String, String> params = new HashMap<>();
                    params.put("codigo_cuenta", String.valueOf(cuenta.getCodigoCuenta()));
                    params.put("codigo_curso", String.valueOf(curso.getCodigoCursoPublicado()));
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } catch (final Exception exception) {
            callBackModelGuardarCurso.cursoGuardado("ERROR");
        }
    }

    public void listarCursosGuardados(final Cuenta cuenta) {
        try {
            RequestQueue requestQueue;
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();

            final String url = "https://www.kytcla.com/app/cursos/listar_cursos_guardados.php";
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            final JSONArray jsonArray = new JSONArray(response);
                            ArrayList<CursoPersonalizado> cursosPersonalizados = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                final JSONObject jsonObject = jsonArray.getJSONObject(i);
                                final int codigo_curso_guardado = jsonObject.getInt("codigo_curso_guardado");
                                final String fecha_guardado = jsonObject.getString("fecha_guardado");
                                final boolean estado_curso_guardado = jsonObject.getString("estado_curso_guardado").equals("1");
                                final int codigo_curso_publicado = jsonObject.getInt("codigo_curso_publicado");
                                final String logo = jsonObject.getString("logo");
                                final String area = jsonObject.getString("area");
                                final String nombre = jsonObject.getString("nombre");
                                final String objetivo = jsonObject.getString("objetivo");
                                final String dirigido = jsonObject.getString("dirigido");
                                final String dictado = jsonObject.getString("dictado");
                                final String contenidos = jsonObject.getString("contenidos");
                                final String fecha_inicio = jsonObject.getString("fecha_inicio");
                                final String horario = jsonObject.getString("horario");
                                final String duracion = jsonObject.getString("duracion");
                                final String modalidad = jsonObject.getString("modalidad");
                                final String costo_curso = jsonObject.getString("costo_curso");
                                final String enlace_inscripcion = jsonObject.getString("enlace_inscripcion");
                                final boolean estado_curso_publicado = jsonObject.getString("estado_curso_publicado").equals("1");
                                final Curso curso = new Curso(codigo_curso_publicado, logo, area, nombre, objetivo, dirigido, dictado, contenidos, fecha_inicio, horario, duracion, modalidad, costo_curso, enlace_inscripcion, estado_curso_publicado);
                                cursosPersonalizados.add(new CursoPersonalizado(curso, true));
                            }
                            callBackModelListarCursosGuardados.cursosGuardadosObtenidos(cursosPersonalizados);
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                            callBackModelListarCursosGuardados.cursosGuardadosObtenidos(null);
                        }
                    },
                    error -> {
                        callBackModelListarCursosGuardados.cursosGuardadosObtenidos(null);
                    }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    final Map<String, String> params = new HashMap<>();
                    params.put("codigo_cuenta", String.valueOf(cuenta.getCodigoCuenta()));
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } catch (final Exception exception) {
            callBackModelListarCursosGuardados.cursosGuardadosObtenidos(null);
        }
    }

    public void eliminar(final Curso curso) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        final String url = "https://www.kytcla.com/app/cursos/eliminar_curso.php";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        final JSONArray jsonArray = new JSONArray(response);
                        callBackModelEliminarCurso.responseEliminarCurso(jsonArray.getJSONObject(0).getString("RESPONSE"));
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                        callBackModelEliminarCurso.responseEliminarCurso("ERROR_DESCONOCIDO");
                    }
                },
                error -> {
                    callBackModelEliminarCurso.responseEliminarCurso("ERROR");
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("codigo_curso_publicado", String.valueOf(curso.getCodigoCursoPublicado()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void eliminarCursoGuardado(final Cuenta cuenta, final Curso curso) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        final String url = "https://www.kytcla.com/app/cursos/eliminar_curso_guardado.php";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        final JSONArray jsonArray = new JSONArray(response);
                        callBackModelEliminarCursoGuardado.responseEliminarCursoGuardado(jsonArray.getJSONObject(0).getString("RESPONSE"));
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                        callBackModelEliminarCursoGuardado.responseEliminarCursoGuardado("ERROR_DESCONOCIDO");
                    }
                },
                error -> {
                    callBackModelEliminarCursoGuardado.responseEliminarCursoGuardado("ERROR");
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("codigo_cuenta", String.valueOf(cuenta.getCodigoCuenta()));
                params.put("codigo_curso_publicado", String.valueOf(curso.getCodigoCursoPublicado()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    // endregion

    // region Interface

    public interface CallBackModelListarCursos {
        void cursosObtenidos(final ArrayList<CursoPersonalizado> cursosPersonalizados);
    }

    public interface CallBackModelInsertarCurso {
        void cursoInsertado(final String code_response);
    }

    public interface CallBackModelActualizarCurso {
        void responseActualizarCurso(final String code_response);
    }

    public interface CallBackModelGuardarCurso {
        void cursoGuardado(final String code_response);
    }

    public interface CallBackModelListarCursosGuardados {
        void cursosGuardadosObtenidos(final ArrayList<CursoPersonalizado> cursosPersonalizados);
    }

    public interface CallBackModelEliminarCurso {
        void responseEliminarCurso(final String code_response);
    }

    public interface CallBackModelEliminarCursoGuardado {
        void responseEliminarCursoGuardado(final String code_response);
    }

    // endregion
}
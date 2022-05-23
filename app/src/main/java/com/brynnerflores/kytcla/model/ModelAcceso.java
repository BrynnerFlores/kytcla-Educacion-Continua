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
import com.android.volley.toolbox.StringRequest;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.Persona;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ModelAcceso {

    // region Variables

    private final Context context;
    private CallBackIniciarSesion callBackIniciarSesion;
    private CallBackRegistrarse callBackRegistrarse;

    // endregion

    // region Constructor

    public ModelAcceso(final Context context) {
        this.context = context;
    }

    // endregion

    // region Setters

    public void setCallBackIniciarSesion(CallBackIniciarSesion callBackIniciarSesion) {
        this.callBackIniciarSesion = callBackIniciarSesion;
    }

    public void setCallBackRegistrarse(CallBackRegistrarse callBackRegistrarse) {
        this.callBackRegistrarse = callBackRegistrarse;
    }

    // endregion

    // region Metodos

    public void iniciarSesion(final String correoElectronico, final String password) {
        try {
            RequestQueue requestQueue;
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);

            requestQueue.start();

            final String url = "http://192.168.0.104/kytcla/acceso/iniciar_sesion.php";

            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            final JSONArray jsonArray = new JSONArray(response);
                            final int codigo_persona = jsonArray.getJSONObject(0).getInt("codigo_persona");
                            final String foto = jsonArray.getJSONObject(0).getString("foto");
                            final String nombre = jsonArray.getJSONObject(0).getString("nombre");
                            final String apellido = jsonArray.getJSONObject(0).getString("apellido");
                            final String fecha_nacimiento = jsonArray.getJSONObject(0).getString("fecha_nacimiento");
                            final String sexo = jsonArray.getJSONObject(0).getString("sexo");
                            final String pais = jsonArray.getJSONObject(0).getString("pais");
                            final String ciudad = jsonArray.getJSONObject(0).getString("ciudad");
                            final String direccion_domicilio = jsonArray.getJSONObject(0).getString("direccion_domicilio");
                            final String presentacion = jsonArray.getJSONObject(0).getString("presentacion");
                            final boolean estado_persona = jsonArray.getJSONObject(0).getString("estado_persona").equals("1") ? true : false;

                            final int codigo_cuenta = jsonArray.getJSONObject(0).getInt("codigo_cuenta");
                            final String correo_electronico = jsonArray.getJSONObject(0).getString("correo_electronico");
                            final boolean estado_cuenta = jsonArray.getJSONObject(0).getString("estado_cuenta").equals("1") ? true : false;

                            final Persona persona = new Persona(codigo_persona, foto, nombre, apellido, fecha_nacimiento, sexo, pais, ciudad, direccion_domicilio, presentacion, estado_persona);
                            final Cuenta cuenta = new Cuenta(codigo_cuenta, persona, correo_electronico, null, estado_cuenta);

                            callBackIniciarSesion.iniciarSesionResponse("CREDENCIALES_VALIDOS", cuenta);
                        } catch (final JSONException jsonException) {
                            callBackIniciarSesion.iniciarSesionResponse("ERROR_DESCONOCIDO", null);
                        }
                    }, error -> {
                if (error.networkResponse.statusCode == 401) {
                    callBackIniciarSesion.iniciarSesionResponse("CREDENCIALES_NO_VALIDOS", null);
                } else {
                    callBackIniciarSesion.iniciarSesionResponse("ERROR_DESCONOCIDO", null);
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    final Map<String, String> params = new HashMap<>();
                    params.put("email", correoElectronico);
                    params.put("password", password);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } catch (final Exception exception) {
            callBackIniciarSesion.iniciarSesionResponse("ERROR_DESCONOCIDO", null);
        }
    }

    public void registrar(final Cuenta cuenta) {
        try {
            RequestQueue requestQueue;
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);

            requestQueue.start();

            final String url = "http://192.168.0.104/kytcla/acceso/registrarse.php";

            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            final JSONArray jsonArray = new JSONArray(response);
                            callBackRegistrarse.registrarseResponse(jsonArray.getJSONObject(0).getString("RESPONSE"));
                        } catch (final JSONException jsonException) {
                            callBackRegistrarse.registrarseResponse("ERROR_DESCONOCIDO");
                        }
                    }, error -> {
                callBackRegistrarse.registrarseResponse("ERROR");
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    final Map<String, String> params = new HashMap<>();
                    params.put("persona_foto", cuenta.getPersona().getFoto());
                    params.put("persona_nombre", cuenta.getPersona().getNombre());
                    params.put("persona_apellido", cuenta.getPersona().getApellido());
                    params.put("persona_fecha_nacimiento", cuenta.getPersona().getFechaNacimiento());
                    params.put("persona_sexo", cuenta.getPersona().getSexo());
                    params.put("persona_pais_residencia", cuenta.getPersona().getPais());
                    params.put("persona_ciudad_residencia", cuenta.getPersona().getCiudad());
                    params.put("persona_direccion_domicilio", cuenta.getPersona().getDireccionDomicilio());
                    params.put("persona_presentacion", cuenta.getPersona().getPresentacion());
                    params.put("persona_estado", cuenta.getPersona().isEstado() == true ? "T" : "F");
                    params.put("cuenta_email", cuenta.getCorreoElectronico());
                    params.put("cuenta_pass", cuenta.getPassword());
                    params.put("cuenta_estado", cuenta.isEstado() == true ? "T" : "F");
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } catch (final Exception exception) {
            callBackRegistrarse.registrarseResponse("ERROR_DESCONOCIDO");
        }
    }

    // endregion

    // region Interfaces

    public interface CallBackIniciarSesion {
        void iniciarSesionResponse(final String code_response, final Cuenta cuenta);
    }

    public interface CallBackRegistrarse {
        void registrarseResponse(final String code_response);
    }

    // endregion

}

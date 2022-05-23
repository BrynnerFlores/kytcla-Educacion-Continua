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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ModelCuenta {

    // region Variables

    private final Context context;
    private CallBackModelActualizar callBackModelActualizar;
    private CallBackModelActualizarPassword callBackModelActualizarPassword;
    private CallBackModelRecuperarCuenta callBackModelRecuperarCuenta;

    // endregion

    // region Constructor

    public ModelCuenta(final Context context) {
        this.context = context;
    }

    // endregion

    // region Setters

    public void setCallBackModelActualizar(CallBackModelActualizar callBackModelActualizar) {
        this.callBackModelActualizar = callBackModelActualizar;
    }

    public void setCallBackModelActualizarPassword(CallBackModelActualizarPassword callBackModelActualizarPassword) {
        this.callBackModelActualizarPassword = callBackModelActualizarPassword;
    }

    public void setCallBackModelRecuperarCuenta(CallBackModelRecuperarCuenta callBackModelRecuperarCuenta) {
        this.callBackModelRecuperarCuenta = callBackModelRecuperarCuenta;
    }

    // endregion

    // region Metodos

    public void actualizarCuenta(final Cuenta cuenta) {
        try {
            RequestQueue requestQueue;
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);

            requestQueue.start();

            final String url = "http://192.168.0.104/kytcla/cuenta/actualizar_cuenta.php";

            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            final JSONArray jsonArray = new JSONArray(response);
                            callBackModelActualizar.responseActualizarCuenta(jsonArray.getJSONObject(0).getString("RESPONSE"));
                        } catch (final JSONException jsonException) {
                            callBackModelActualizar.responseActualizarCuenta("ERROR_DESCONOCIDO");
                        }
                    }, error -> {
                callBackModelActualizar.responseActualizarCuenta("ERROR");
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    final Map<String, String> params = new HashMap<>();
                    params.put("codigo_persona", String.valueOf(cuenta.getPersona().getCodigoPersona()));
                    params.put("persona_foto", cuenta.getPersona().getFoto());
                    params.put("persona_nombre", cuenta.getPersona().getNombre());
                    params.put("persona_apellido", cuenta.getPersona().getApellido());
                    params.put("persona_fecha_nacimiento", cuenta.getPersona().getFechaNacimiento());
                    params.put("persona_sexo", cuenta.getPersona().getSexo());
                    params.put("persona_pais", cuenta.getPersona().getPais());
                    params.put("persona_ciudad", cuenta.getPersona().getCiudad());
                    params.put("persona_direccion_domicilio", cuenta.getPersona().getDireccionDomicilio());
                    params.put("persona_presentacion", cuenta.getPersona().getPresentacion());
                    params.put("codigo_cuenta", String.valueOf(cuenta.getCodigoCuenta()));
                    params.put("cuenta_correo_electronico", cuenta.getCorreoElectronico());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } catch (final Exception exception) {
            callBackModelActualizar.responseActualizarCuenta("ERROR_DESCONOCIDO");
        }
    }

    public void actualizarPassword(final Cuenta cuenta, final String newPassword) {
        try {
            RequestQueue requestQueue;
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);

            requestQueue.start();

            final String url = "http://192.168.0.104/kytcla/cuenta/actualizar_password.php";

            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            final JSONArray jsonArray = new JSONArray(response);
                            callBackModelActualizarPassword.responseActualizarPassword(jsonArray.getJSONObject(0).getString("RESPONSE"));
                        } catch (final JSONException jsonException) {
                            callBackModelActualizarPassword.responseActualizarPassword("ERROR_DESCONOCIDO");
                        }
                    }, error -> {
                callBackModelActualizarPassword.responseActualizarPassword("ERROR");
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    final Map<String, String> params = new HashMap<>();
                    params.put("codigo_cuenta", String.valueOf(cuenta.getCodigoCuenta()));
                    params.put("nuevo_password", newPassword);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } catch (final Exception exception) {
            callBackModelActualizarPassword.responseActualizarPassword("ERROR_DESCONOCIDO");
        }
    }

    public void recuperarCuenta(final String correo_electronico) {
        try {
            RequestQueue requestQueue;
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);

            requestQueue.start();

            final String url = "http://192.168.0.104/kytcla/cuenta/recuperar_cuenta.php";

            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            final JSONArray jsonArray = new JSONArray(response);
                            callBackModelRecuperarCuenta.responseRecuperarCuenta(jsonArray.getJSONObject(0).getString("RESPONSE"));
                        } catch (final JSONException jsonException) {
                            callBackModelRecuperarCuenta.responseRecuperarCuenta("ERROR_DESCONOCIDO");
                        }
                    }, error -> {
                callBackModelRecuperarCuenta.responseRecuperarCuenta("ERROR");
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    final Map<String, String> params = new HashMap<>();
                    params.put("correo_electronico", correo_electronico);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } catch (final Exception exception) {
            callBackModelRecuperarCuenta.responseRecuperarCuenta("ERROR_DESCONOCIDO");
        }
    }

    // endregion

    // region Interfaces

    public interface CallBackModelActualizar {
        void responseActualizarCuenta(final String code_response);
    }

    public interface CallBackModelActualizarPassword {
        void responseActualizarPassword(final String code_response);
    }

    public interface CallBackModelRecuperarCuenta {
        void responseRecuperarCuenta(final String code_response);
    }

    // endregion

}

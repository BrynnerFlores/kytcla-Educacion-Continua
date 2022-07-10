package com.brynnerflores.kytcla.model;

import android.content.Context;
import android.util.Log;

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
import com.brynnerflores.kytcla.model.POJO.Curso;
import com.brynnerflores.kytcla.model.POJO.Producto;
import com.brynnerflores.kytcla.model.POJO.ProductoGuardado;
import com.brynnerflores.kytcla.model.POJO.ProductoPersonalizado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModelProducto {

    // region Variables

    private final Context context;
    private CallBackModelListarProductos callBackModelListarProductos;
    private CallBackModelInsertarProducto callBackModelInsertarProducto;
    private CallBackModelModificarProducto callBackModelModificarProducto;
    private CallBackModelListarProductosGuardados callBackModelListarProductosGuardados;
    private CallBackModelEliminarProducto callBackModelEliminarProducto;
    private CallBackModelGuardarProducto callBackModelGuardarProducto;
    private CallBackModelEliminarProductoGuardado callBackModelEliminarProductoGuardado;

    // endregion

    // region Constructor

    public ModelProducto(Context context) {
        this.context = context;
    }

    // endregion

    // region Getters and Setters

    public void setCallBackModelListarProductos(CallBackModelListarProductos callBackModelListarProductos) {
        this.callBackModelListarProductos = callBackModelListarProductos;
    }

    public void setCallBackModelInsertarProducto(CallBackModelInsertarProducto callBackModelInsertarProducto) {
        this.callBackModelInsertarProducto = callBackModelInsertarProducto;
    }

    public void setCallBackModelModificarProducto(CallBackModelModificarProducto callBackModelModificarProducto) {
        this.callBackModelModificarProducto = callBackModelModificarProducto;
    }

    public void setCallBackModelListarProductosGuardados(CallBackModelListarProductosGuardados callBackModelListarProductosGuardados) {
        this.callBackModelListarProductosGuardados = callBackModelListarProductosGuardados;
    }

    public void setCallBackModelEliminarProducto(CallBackModelEliminarProducto callBackModelEliminarProducto) {
        this.callBackModelEliminarProducto = callBackModelEliminarProducto;
    }

    public void setCallBackModelGuardarProducto(CallBackModelGuardarProducto callBackModelGuardarProducto) {
        this.callBackModelGuardarProducto = callBackModelGuardarProducto;
    }

    public void setCallBackModelEliminarProductoGuardado(CallBackModelEliminarProductoGuardado callBackModelEliminarProductoGuardado) {
        this.callBackModelEliminarProductoGuardado = callBackModelEliminarProductoGuardado;
    }

    // endregion

    // region Metodos

    public void listarProductos(final String categoria) {
        try {
            RequestQueue requestQueue;
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();

            final String url = "https://www.kytcla.com/app/productos/listar_productos.php";
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            final JSONArray jsonArray = new JSONArray(response);
                            ArrayList<ProductoPersonalizado> productosPersonalizados = new ArrayList<>();
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        final JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        final int codigo_producto = jsonObject.getInt("id");
                                        final String logo = jsonObject.getString("logo");
                                        final String categoria_producto = jsonObject.getString("categoria");
                                        final String nombre = jsonObject.getString("nombre");
                                        final String descripcion = jsonObject.getString("descripcion");
                                        final String precio = jsonObject.getString("precio");
                                        final boolean estado = jsonObject.getString("estado").equals("1");
                                        final boolean producto_guardado = jsonObject.getString("producto_guardado").equals("1");
                                        final Producto producto = new Producto(codigo_producto, categoria_producto, logo, nombre, descripcion, precio, estado);
                                        productosPersonalizados.add(new ProductoPersonalizado(producto, producto_guardado));
                                    } catch (final JSONException exception) {
                                        exception.printStackTrace();
                                        productosPersonalizados = null;
                                    }
                                }
                            }
                            callBackModelListarProductos.responseListarProductos(productosPersonalizados);
                        } catch (final JSONException exception) {
                            callBackModelListarProductos.responseListarProductos(null);
                        }
                    },
                    error -> {
                        callBackModelListarProductos.responseListarProductos(null);
                    }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    final Map<String, String> params = new HashMap<>();
                    params.put("CATEGORIA", categoria);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } catch (final Exception exception) {
            callBackModelListarProductos.responseListarProductos(null);
        }
    }

    public void insertar(final Producto producto) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        final String url = "https://www.kytcla.com/app/productos/insertar_producto.php";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        final JSONArray jsonArray = new JSONArray(response);
                        callBackModelInsertarProducto.responseProductoInsertado(jsonArray.getJSONObject(0).getString("RESPONSE"));
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                        callBackModelInsertarProducto.responseProductoInsertado("ERROR_DESCONOCIDO");
                    }
                },
                error -> {
                    callBackModelInsertarProducto.responseProductoInsertado("ERROR");
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("categoria", producto.getCategoriaProducto());
                params.put("foto", producto.getFotoProducto());
                params.put("nombre", producto.getNombre());
                params.put("descripcion", producto.getDescripcion());
                params.put("precio", producto.getPrecio());
                params.put("estado", producto.isEstado() == true ? "T" : "F");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void modificar(final Producto producto) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        final String url = "https://www.kytcla.com/app/productos/actualizar_producto.php";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        final JSONArray jsonArray = new JSONArray(response);
                        callBackModelModificarProducto.responseProductoModificado(jsonArray.getJSONObject(0).getString("RESPONSE"));
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                        callBackModelModificarProducto.responseProductoModificado("ERROR");
                    }
                },
                error -> {
                    callBackModelModificarProducto.responseProductoModificado("ERROR");
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("codigo_producto", String.valueOf(producto.getCodigoProducto()));
                params.put("foto", producto.getFotoProducto());
                params.put("categoria", producto.getCategoriaProducto());
                params.put("nombre", producto.getNombre());
                params.put("descripcion", producto.getDescripcion());
                params.put("precio", producto.getPrecio());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void listarProductosGuardados(final Cuenta cuenta) {
        try {
            RequestQueue requestQueue;
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();

            final String url = "https://www.kytcla.com/app/productos/listar_productos_guardados.php";
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            final JSONArray jsonArray = new JSONArray(response);
                            ArrayList<ProductoGuardado> productosGuardados = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    final JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final int codigo_producto_guardado = jsonObject.getInt("codigo_producto_guardado");
                                    final String fecha_guardado = jsonObject.getString("fecha_guardado");
                                    final boolean estado_producto_guardado = jsonObject.getString("estado_producto_guardado").equals("1");
                                    final int codigo_producto = jsonObject.getInt("codigo_producto");
                                    final String foto = jsonObject.getString("foto");
                                    final String categoria_producto = jsonObject.getString("categoria");
                                    final String nombre = jsonObject.getString("nombre");
                                    final String descripcion = jsonObject.getString("descripcion");
                                    final String precio = jsonObject.getString("precio");
                                    final boolean estado = jsonObject.getString("estado").equals("1");
                                    final Producto producto = new Producto(codigo_producto, categoria_producto, foto, nombre, descripcion, precio, estado);
                                    final ProductoGuardado productoGuardado = new ProductoGuardado(codigo_producto_guardado, cuenta, producto, fecha_guardado, estado_producto_guardado);
                                    productosGuardados.add(productoGuardado);
                                } catch (JSONException exception) {
                                    exception.printStackTrace();
                                    productosGuardados = null;
                                }
                            }
                            callBackModelListarProductosGuardados.responseListarProductosGuardados(productosGuardados);
                        } catch (final Exception exception) {
                            callBackModelListarProductosGuardados.responseListarProductosGuardados(null);
                        }
                    },
                    error -> {
                        callBackModelListarProductosGuardados.responseListarProductosGuardados(null);
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
            callBackModelListarProductosGuardados.responseListarProductosGuardados(null);
        }
    }

    public void eliminar(final Producto producto) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        final String url = "https://www.kytcla.com/app/productos/eliminar_producto.php";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d("RESPONSE", response);
                    try {
                        final JSONArray jsonArray = new JSONArray(response);
                        callBackModelEliminarProducto.responseEliminarProducto(jsonArray.getJSONObject(0).getString("RESPONSE"));
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                        callBackModelEliminarProducto.responseEliminarProducto("ERROR_DESCONOCIDO");
                    }
                },
                error -> {
                    callBackModelEliminarProducto.responseEliminarProducto("ERROR");
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("codigo_producto", String.valueOf(producto.getCodigoProducto()));
                params.put("nombre", producto.getNombre());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void guardar(final Cuenta cuenta, final Producto producto) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        final String url = "https://www.kytcla.com/app/productos/guardar_producto.php";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        final JSONArray jsonArray = new JSONArray(response);
                        callBackModelGuardarProducto.responseGuardarProducto(jsonArray.getJSONObject(0).getString("RESPONSE"));
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                        callBackModelGuardarProducto.responseGuardarProducto("ERROR_DESCONOCIDO");
                    }
                },
                error -> {
                    callBackModelGuardarProducto.responseGuardarProducto("ERROR");
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("codigo_cuenta", String.valueOf(cuenta.getCodigoCuenta()));
                params.put("codigo_producto", String.valueOf(producto.getCodigoProducto()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void eliminarProductoGuardado(final Cuenta cuenta, final Producto producto) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        final String url = "https://www.kytcla.com/app/productos/eliminar_producto_guardado.php";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        final JSONArray jsonArray = new JSONArray(response);
                        callBackModelEliminarProductoGuardado.responseEliminarProductoGuardado(jsonArray.getJSONObject(0).getString("RESPONSE"));
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                        callBackModelEliminarProductoGuardado.responseEliminarProductoGuardado("ERROR_DESCONOCIDO");
                    }
                },
                error -> {
                    callBackModelEliminarProductoGuardado.responseEliminarProductoGuardado("ERROR");
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("codigo_cuenta", String.valueOf(cuenta.getCodigoCuenta()));
                params.put("codigo_producto", String.valueOf(producto.getCodigoProducto()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    // endregion

    // region Interface

    public interface CallBackModelListarProductos {
        void responseListarProductos(final ArrayList<ProductoPersonalizado> productosPersonalizados);
    }

    public interface CallBackModelInsertarProducto {
        void responseProductoInsertado(final String code_response);
    }

    public interface CallBackModelModificarProducto {
        void responseProductoModificado(final String code_response);
    }

    public interface CallBackModelListarProductosGuardados {
        void responseListarProductosGuardados(final ArrayList<ProductoGuardado> productosGuardados);
    }

    public interface CallBackModelEliminarProducto {
        void responseEliminarProducto(final String code_response);
    }

    public interface CallBackModelGuardarProducto {
        void responseGuardarProducto(final String code_response);
    }

    public interface CallBackModelEliminarProductoGuardado {
        void responseEliminarProductoGuardado(final String code_response);
    }

    // endregion

}
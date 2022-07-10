package com.brynnerflores.kytcla.presenter.productos;

import android.content.Context;

import com.brynnerflores.kytcla.model.ModelProducto;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.Producto;
import com.brynnerflores.kytcla.model.POJO.ProductoGuardado;
import com.brynnerflores.kytcla.model.POJO.ProductoPersonalizado;

import java.util.ArrayList;

public class PresenterProducto implements ModelProducto.CallBackModelListarProductos, ModelProducto.CallBackModelInsertarProducto, ModelProducto.CallBackModelModificarProducto, ModelProducto.CallBackModelListarProductosGuardados, ModelProducto.CallBackModelEliminarProducto, ModelProducto.CallBackModelGuardarProducto, ModelProducto.CallBackModelEliminarProductoGuardado {

    // region Variables

    private final Context context;
    private CallBackObtenerProductos callBackObtenerProductos;
    private CallBackRegistrarProducto callBackRegistrarProducto;
    private CallBackModificarProducto callBackModificarProducto;
    private CallBackObtenerProductosGuardados callBackObtenerProductosGuardados;
    private CallBackEliminarProducto callBackEliminarProducto;
    private CallBackGuardarProducto callBackGuardarProducto;
    private CallBackEliminarProductoGuardado callBackEliminarProductoGuardado;

    // endregion

    // region Constructor

    public PresenterProducto(Context context) {
        this.context = context;
    }

    // endregion

    // region Getters ans Setters

    public void setCallBackObtenerProductos(CallBackObtenerProductos callBackObtenerProductos) {
        this.callBackObtenerProductos = callBackObtenerProductos;
    }

    public void setCallBackRegistrarProducto(CallBackRegistrarProducto callBackRegistrarProducto) {
        this.callBackRegistrarProducto = callBackRegistrarProducto;
    }

    public void setCallBackModificarProducto(CallBackModificarProducto callBackModificarProducto) {
        this.callBackModificarProducto = callBackModificarProducto;
    }

    public void setCallBackObtenerProductosGuardados(CallBackObtenerProductosGuardados callBackObtenerProductosGuardados) {
        this.callBackObtenerProductosGuardados = callBackObtenerProductosGuardados;
    }

    public void setCallBackEliminarProducto(CallBackEliminarProducto callBackEliminarProducto) {
        this.callBackEliminarProducto = callBackEliminarProducto;
    }

    public void setCallBackGuardarProducto(CallBackGuardarProducto callBackGuardarProducto) {
        this.callBackGuardarProducto = callBackGuardarProducto;
    }

    public void setCallBackEliminarProductoGuardado(CallBackEliminarProductoGuardado callBackEliminarProductoGuardado) {
        this.callBackEliminarProductoGuardado = callBackEliminarProductoGuardado;
    }

    // endregion

    // region Metodos

    public void obtenerProductos(final String categoria) {
        try {
            final ModelProducto modelProducto = new ModelProducto(context);
            modelProducto.setCallBackModelListarProductos(this);
            modelProducto.listarProductos(categoria);
        } catch (final Exception exception) {
            callBackObtenerProductos.errorDesconocidoObtenerProductos("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    public void registrarProducto(final Producto producto) {
        try {
            final ModelProducto modelProducto = new ModelProducto(context);
            modelProducto.setCallBackModelInsertarProducto(this);
            modelProducto.insertar(producto);
        } catch (final Exception exception) {
            callBackRegistrarProducto.errorDesconocidoRegistrarProducto("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    public void modificarProducto(final Producto producto) {
        try {
            final ModelProducto modelProducto = new ModelProducto(context);
            modelProducto.setCallBackModelModificarProducto(this);
            modelProducto.modificar(producto);
        } catch (final Exception exception) {
            callBackModificarProducto.errorDesconocidoModificarProducto("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    public void obtenerProductosGuardados(final Cuenta cuenta) {
        try {
            final ModelProducto modelProducto = new ModelProducto(context);
            modelProducto.setCallBackModelListarProductosGuardados(this);
            modelProducto.listarProductosGuardados(cuenta);
        } catch (final Exception exception) {
            callBackObtenerProductosGuardados.errorDesconocidoObtenerProductosGuardados("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    public void eliminarProducto(final Producto producto) {
        try {
            final ModelProducto modelProducto = new ModelProducto(context);
            modelProducto.setCallBackModelEliminarProducto(this);
            modelProducto.eliminar(producto);
        } catch (final Exception exception) {
            callBackEliminarProducto.errorDesconocidoEliminarProducto("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    public void guardarProducto(final Cuenta cuenta, final Producto producto) {
        try {
            final ModelProducto modelProducto = new ModelProducto(context);
            modelProducto.setCallBackModelGuardarProducto(this);
            modelProducto.guardar(cuenta, producto);
        } catch (final Exception exception) {
            callBackGuardarProducto.errorDesconocidoGuardarProducto("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    public void eliminarProductoGuardado(final Cuenta cuenta, final Producto producto) {
        try {
            final ModelProducto modelProducto = new ModelProducto(context);
            modelProducto.setCallBackModelEliminarProductoGuardado(this);
            modelProducto.eliminarProductoGuardado(cuenta, producto);
        } catch (final Exception exception) {
            callBackEliminarProductoGuardado.errorDesconocidoEliminarProductoGuardado("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    // endregion

    // region CallBack Model

    @Override
    public void responseListarProductos(final ArrayList<ProductoPersonalizado> productosPersonalizados) {
        if (productosPersonalizados == null) {
            callBackObtenerProductos.errorObtenerProductos("Se produjo un error al obtener los productos.");
        } else if (productosPersonalizados.isEmpty()) {
            callBackObtenerProductos.listaProductosVacia("No se encontraron productos registrados.");
        } else {
            callBackObtenerProductos.productosObtenidos(productosPersonalizados);
        }
    }

    @Override
    public void responseProductoInsertado(final String code_response) {
        switch (code_response) {
            case "INSERTADO":
                callBackRegistrarProducto.productoRegistrado("Registrado, actualiza la lista.");
                break;

            case "NO_INSERTADO":
                callBackRegistrarProducto.errorRegistrarProducto("Se produjo un error al registrar el producto.");
                break;

            case "PRODUCTO_EXISTE":
                callBackRegistrarProducto.productoExiste("El producto ya se encuentra registrado.");
                break;

            default:
                callBackRegistrarProducto.errorDesconocidoRegistrarProducto("Se produjo un error desconocido, vuelve a intentarlo.");
                break;
        }
    }

    @Override
    public void responseProductoModificado(final String code_response) {
        switch (code_response) {
            case "ACTUALIZADO":
                callBackModificarProducto.productoModificado("Modificado.");
                break;

            case "NO_ACTUALIZADO":
                callBackModificarProducto.errorModificarProducto("Se produjo un error al modificar los datos del producto.");
                break;

            case "PRODUCTO_EXISTE":
                callBackModificarProducto.productoExiste("Ya existe un producto con el mismo nombre.");
                break;

            default:
                callBackModificarProducto.errorDesconocidoModificarProducto("Se produjo un error desconocido, vuelve a intentarlo.");
                break;
        }
    }

    @Override
    public void responseListarProductosGuardados(final ArrayList<ProductoGuardado> productosGuardados) {
        if (productosGuardados == null) {
            callBackObtenerProductosGuardados.errorObtenerProductosGuardados("Se produjo un error al obtener los productos guardados.");
        } else if (productosGuardados.isEmpty()) {
            callBackObtenerProductosGuardados.listaProductosGuardadosVacia("No se encontraron productos guardados.");
        } else {
            callBackObtenerProductosGuardados.productosGuardadosObtenidos(productosGuardados);
        }
    }

    @Override
    public void responseEliminarProducto(final String code_response) {
        switch (code_response) {
            case "ACTUALIZADO":
                callBackEliminarProducto.productoEliminado("Eliminado.");
                break;

            case "NO_ACTUALIZADO":
                callBackEliminarProducto.errorEliminarProducto("Se produjo un error al eliminar el producto.");
                break;

            default:
                callBackEliminarProducto.errorDesconocidoEliminarProducto("Se produjo un error desconocido, vuelve a intentarlo.");
                break;
        }
    }

    @Override
    public void responseGuardarProducto(final String code_response) {
        switch (code_response) {
            case "INSERTADO":
                callBackGuardarProducto.productoGuardado("Guardado.");
                break;

            case "NO_INSERTADO":
                callBackGuardarProducto.errorGuardarProducto("Se produjo un error al guardar el producto.");
                break;

            default:
                callBackGuardarProducto.errorDesconocidoGuardarProducto("Se produjo un error desconocido, vuelve a intentarlo.");
                break;
        }
    }

    @Override
    public void responseEliminarProductoGuardado(final String code_response) {
        switch (code_response) {
            case "ACTUALIZADO":
                callBackEliminarProductoGuardado.productoGuardadoEliminado("Eliminado de elementos guardado.");
                break;

            case "NO_ACTUALIZADO":
                callBackEliminarProductoGuardado.errorEliminarProductoGuardado("Se produjo un error al eliminar el producto guardado.");
                break;

            default:
                callBackEliminarProductoGuardado.errorDesconocidoEliminarProductoGuardado("Se produjo un error desconocido, vuelve a intentarlo.");
                break;
        }
    }

    // endregion

    // region Interface

    public interface CallBackObtenerProductos {
        void productosObtenidos(final ArrayList<ProductoPersonalizado> productosPersonalizados);

        void listaProductosVacia(final String msg);

        void errorObtenerProductos(final String msg);

        void errorDesconocidoObtenerProductos(final String msg);
    }

    public interface CallBackRegistrarProducto {
        void productoRegistrado(final String msg);

        void productoExiste(final String msg);

        void errorRegistrarProducto(final String msg);

        void errorDesconocidoRegistrarProducto(final String msg);
    }

    public interface CallBackModificarProducto {
        void productoModificado(final String msg);

        void productoExiste(final String msg);

        void errorModificarProducto(final String msg);

        void errorDesconocidoModificarProducto(final String msg);
    }

    public interface CallBackObtenerProductosGuardados {
        void productosGuardadosObtenidos(final ArrayList<ProductoGuardado> productosGuardados);

        void listaProductosGuardadosVacia(final String msg);

        void errorObtenerProductosGuardados(final String msg);

        void errorDesconocidoObtenerProductosGuardados(final String msg);
    }

    public interface CallBackEliminarProducto {
        void productoEliminado(final String msg);

        void errorEliminarProducto(final String msg);

        void errorDesconocidoEliminarProducto(final String msg);
    }

    public interface CallBackGuardarProducto {
        void productoGuardado(final String msg);

        void errorGuardarProducto(final String msg);

        void errorDesconocidoGuardarProducto(final String msg);
    }

    public interface CallBackEliminarProductoGuardado {
        void productoGuardadoEliminado(final String msg);

        void errorEliminarProductoGuardado(final String msg);

        void errorDesconocidoEliminarProductoGuardado(final String msg);
    }

    // endregion

}
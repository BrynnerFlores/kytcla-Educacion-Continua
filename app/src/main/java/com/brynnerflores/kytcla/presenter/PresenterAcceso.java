package com.brynnerflores.kytcla.presenter;

import android.content.Context;

import com.brynnerflores.kytcla.model.ModelAcceso;
import com.brynnerflores.kytcla.model.POJO.Cuenta;

public class PresenterAcceso implements ModelAcceso.CallBackIniciarSesion, ModelAcceso.CallBackRegistrarse {

    // region Variables

    private final Context context;
    private ModelAcceso modelAcceso;
    private CallBackIniciarSesion callBackIniciarSesion;
    private CallBackRegistrarse callBackRegistrarse;

    // endregion

    // region Constructor

    public PresenterAcceso(final Context context) {
        this.context = context;
    }

    // endregion

    // region Getters and Setters

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
            modelAcceso = new ModelAcceso(context);
            modelAcceso.setCallBackIniciarSesion(this);
            modelAcceso.iniciarSesion(correoElectronico, password);
        } catch (final Exception exception) {
            callBackIniciarSesion.errorDesconocidoIniciarSesion("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    public void registrarse(final Cuenta cuenta) {
        try {
            modelAcceso = new ModelAcceso(context);
            modelAcceso.setCallBackRegistrarse(this);
            modelAcceso.registrar(cuenta);
        } catch (final Exception exception) {
            callBackRegistrarse.errorDesconocidoRegistrar("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    // endregion

    // region Interfaces

    public interface CallBackIniciarSesion {
        void accesoConcedido(final String msg, final Cuenta cuenta);
        void credencialesInvalidos(final String msg);
        void errorIniciarSesion(final String msg);
        void errorDesconocidoIniciarSesion(final String msg);
    }

    public interface CallBackRegistrarse {
        void registrado(final String msg);
        void emailExiste(final String msg);
        void errorRegistrar(final String msg);
        void errorDesconocidoRegistrar(final String msg);
    }

    // endregion

    // region CallBack

    @Override
    public void iniciarSesionResponse(final String code_response, final Cuenta cuenta) {
        switch (code_response) {
            case "ERROR":
                callBackIniciarSesion.errorIniciarSesion("Se produjo un error, vuelve a intentarlo.");
                break;

            case "CREDENCIALES_NO_VALIDOS":
                callBackIniciarSesion.credencialesInvalidos("Correo Electrónico y/o Contraseña incorrectos");
                break;

            case "CREDENCIALES_VALIDOS":
                callBackIniciarSesion.accesoConcedido("Ingresando...", cuenta);
                break;

            default:
                callBackIniciarSesion.errorDesconocidoIniciarSesion("Se produjo un error desconocido, vuelve a intentarlo.");
                break;
        }
    }

    @Override
    public void registrarseResponse(final String code_response) {
        switch (code_response) {
            case "EMAIL_EXISTE":
                callBackRegistrarse.emailExiste("El correo electronico ya esta asociado a otra cuenta.");
                break;

            case "ERROR":
                callBackRegistrarse.errorRegistrar("Se produjo un error, vuelve a intentarlo.");
                break;

            case "REGISTRADO":
                callBackRegistrarse.registrado("Cuenta registrada. Iniciar sesión para acceder");
                break;

            default:
                callBackRegistrarse.errorRegistrar("Se produjo un error desconocido, vuelve a intentarlo.");
                break;
        }
    }

    // endregion
}
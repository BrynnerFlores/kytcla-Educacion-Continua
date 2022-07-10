package com.brynnerflores.kytcla.presenter.cuenta;

import android.content.Context;

import com.brynnerflores.kytcla.model.ModelCuenta;
import com.brynnerflores.kytcla.model.POJO.Cuenta;

public class PresenterCuenta implements ModelCuenta.CallBackModelActualizar, ModelCuenta.CallBackModelActualizarPassword, ModelCuenta.CallBackModelRecuperarCuenta {

    // region Variables
    
    private Context context;
    private ModelCuenta modelCuenta;
    private CallBackModificarCuenta callBackModificarCuenta;
    private CallBackModificarPassword callBackModificarPassword;
    private CallBackRecuperarCuenta callBackRecuperarCuenta;

    // endregion

    // region Constructor

    public PresenterCuenta(Context context) {
        this.context = context;
    }

    // endregion

    // region Getters and Setters

    public void setCallBackModificarCuenta(CallBackModificarCuenta callBackModificarCuenta) {
        this.callBackModificarCuenta = callBackModificarCuenta;
    }

    public void setCallBackModificarPassword(CallBackModificarPassword callBackModificarPassword) {
        this.callBackModificarPassword = callBackModificarPassword;
    }

    public void setCallBackRecuperarCuenta(CallBackRecuperarCuenta callBackRecuperarCuenta) {
        this.callBackRecuperarCuenta = callBackRecuperarCuenta;
    }

    // endregion

    // region Metodos

    public void modificarCuenta(final Cuenta cuenta) {
        try {
            modelCuenta = new ModelCuenta(context);
            modelCuenta.setCallBackModelActualizar(this);
            modelCuenta.actualizarCuenta(cuenta);
        } catch (final Exception exception) {
            callBackModificarCuenta.errorDesconocidoModificarCuenta("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    public void modificarPassword(final Cuenta cuenta, final String password, final String newPassword) {
        try {
            modelCuenta = new ModelCuenta(context);
            modelCuenta.setCallBackModelActualizarPassword(this);
            modelCuenta.actualizarPassword(cuenta, password, newPassword);
        } catch (final Exception exception) {
            callBackModificarPassword.errorDesconocidoModificarPassword("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    public void recuperarCuenta(final String correo_electronico) {
        try {
            modelCuenta = new ModelCuenta(context);
            modelCuenta.setCallBackModelRecuperarCuenta(this);
            modelCuenta.recuperarCuenta(correo_electronico);
        } catch (final Exception exception) {
            callBackRecuperarCuenta.errorDesconocidoRecuperarCuenta("Se produjo un error desconocido, vuelve a intentarlo.");
        }
    }

    // endregion

    // region Interfaces

    public interface CallBackModificarCuenta {
        void cuentaModificada(final String msg);
        void correoElectronicoExiste(final String msg);
        void errorModificarCuenta(final String msg);
        void errorDesconocidoModificarCuenta(final String msg);
    }

    public interface CallBackModificarPassword {
        void passwordModificado(final String msg);
        void passwordIncorrecto(final String msg);
        void errorModificarPassword(final String msg);
        void errorDesconocidoModificarPassword(final String msg);
    }

    public interface CallBackRecuperarCuenta {
        void enlaceEnviado(final String msg);
        void errorRecuperarCuenta(final String msg);
        void errorDesconocidoRecuperarCuenta(final String msg);
    }

    // endregion

    // region CallBack

    @Override
    public void responseActualizarCuenta(final String code_response) {
        switch (code_response) {
            case "ACTUALIZADO":
                callBackModificarCuenta.cuentaModificada("Datos Modificados.");
                break;

            case "NO_ACTUALIZADO":
                callBackModificarCuenta.errorModificarCuenta("Se produjo un error al modificar los datos.");
                break;

            case "EMAIL_EXISTE":
                callBackModificarCuenta.correoElectronicoExiste("El correo electrónico ya está asociado a otra cuenta.");
                break;

            default:
                callBackModificarCuenta.errorDesconocidoModificarCuenta("Se produjo un error desconocido, vuelve a intentarlo.");
                break;

        }
    }


    @Override
    public void responseActualizarPassword(final String code_response) {
        switch (code_response) {
            case "PASSWORD_INCORRECTO":
                callBackModificarPassword.passwordIncorrecto("Contraseña Incorrecta.");
                break;

            case "ACTUALIZADO":
                callBackModificarPassword.passwordModificado("Contraseña Modificada.");
                break;

            case "NO_ACTUALIZADO":
                callBackModificarPassword.errorModificarPassword("Se produjo un error.");
                break;

            default:
                callBackModificarPassword.errorDesconocidoModificarPassword("Se produjo un error desconocido, vuelve a intentarlo");
                break;

        }
    }

    @Override
    public void responseRecuperarCuenta(String code_response) {
        switch (code_response) {
            case "ENVIADO":
                callBackModificarPassword.passwordModificado("Contraseña Modificada.");
                break;

            case "NO_ENVIADO":
                callBackModificarPassword.errorModificarPassword("Se produjo un error.");
                break;

            default:
                callBackModificarPassword.errorDesconocidoModificarPassword("Se produjo un error desconocido, vuelve a intentarlo");
                break;

        }
    }
    
    // endregion
}
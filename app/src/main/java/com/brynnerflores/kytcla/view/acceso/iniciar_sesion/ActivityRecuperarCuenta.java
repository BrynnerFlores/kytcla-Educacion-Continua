package com.brynnerflores.kytcla.view.acceso.iniciar_sesion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.SplashActivity;
import com.brynnerflores.kytcla.presenter.cuenta.PresenterCuenta;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ActivityRecuperarCuenta extends AppCompatActivity implements View.OnClickListener, PresenterCuenta.CallBackRecuperarCuenta {

    // region Variables

    private TextInputLayout textInputLayoutCorreoElectronico;
    private TextInputEditText textInputEditTextCorreoElectronico;
    private MaterialButton materialButtonEnviarEnlace;

    private PresenterCuenta presenterCuenta;

    // endregion

    // region Metodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_cuenta);

        textInputLayoutCorreoElectronico = findViewById(R.id.text_input_layout_recuperar_cuenta_correo_electronico);
        textInputEditTextCorreoElectronico = findViewById(R.id.text_input_edit_text_recuperar_cuenta_correo_electronico);
        materialButtonEnviarEnlace = findViewById(R.id.material_button_recuperar_cuenta_enviar_enlace);

        materialButtonEnviarEnlace.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.material_button_recuperar_cuenta_enviar_enlace:
                recuperarCuenta();
                break;

            default:break;
        }
    }

    private void recuperarCuenta() {
        final String correo_electronico = textInputEditTextCorreoElectronico.getText().toString();

        if (correo_electronico.isEmpty() || correo_electronico == null) {
            textInputLayoutCorreoElectronico.setError("Ingresa tu correo electrónico");
        } else {
            textInputLayoutCorreoElectronico.setError(null);

            presenterCuenta = new PresenterCuenta(this);
            presenterCuenta.setCallBackRecuperarCuenta(this);
            presenterCuenta.recuperarCuenta(correo_electronico);
        }
    }

    // endregion

    // region CallBack

    @Override
    public void enlaceEnviado(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this, SplashActivity.class));
    }

    @Override
    public void errorRecuperarCuenta(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoRecuperarCuenta(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    // endregion
}
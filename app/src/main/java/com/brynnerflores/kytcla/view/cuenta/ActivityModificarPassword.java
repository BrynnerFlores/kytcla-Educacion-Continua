package com.brynnerflores.kytcla.view.cuenta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.SplashActivity;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.Persona;
import com.brynnerflores.kytcla.presenter.cuenta.PresenterCuenta;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ActivityModificarPassword extends AppCompatActivity implements View.OnClickListener,  PresenterCuenta.CallBackModificarPassword {

    private TextInputLayout textInputLayoutIngresaPasswordActual;
    private TextInputLayout textInputLayoutIngresaNuevoPassword;
    private TextInputLayout textInputLayoutRepiteNuevoPassword;
    private TextInputEditText textInputEditTextIngresaPasswordActual;
    private TextInputEditText textInputEditTextIngresaNuevoPassword;
    private TextInputEditText textInputEditTextRepiteNuevoPassword;
    private MaterialButton materialButtonCambiarPassword;

    private PresenterCuenta presenterCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_password);

        textInputLayoutIngresaPasswordActual = findViewById(R.id.text_input_layout_modificar_password_ingresa_tu_password_actual);
        textInputLayoutIngresaNuevoPassword = findViewById(R.id.text_input_layout_modificar_password_ingresa_tu_nuevo_password);
        textInputLayoutRepiteNuevoPassword = findViewById(R.id.text_input_layout_modificar_password_repite_tu_nuevo_password);
        textInputEditTextIngresaPasswordActual = findViewById(R.id.text_input_edit_text_modificar_password_ingresa_tu_password_actual);
        textInputEditTextIngresaNuevoPassword = findViewById(R.id.text_input_edit_text_modificar_password_ingresa_tu_nuevo_password);
        textInputEditTextRepiteNuevoPassword = findViewById(R.id.text_input_edit_text_modificar_password_repite_tu_nuevo_password);
        materialButtonCambiarPassword = findViewById(R.id.material_button_modificar_password_cambiar_password);
        materialButtonCambiarPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.material_button_modificar_password_cambiar_password:
                presenterCuenta = new PresenterCuenta(this);
                presenterCuenta.setCallBackModificarPassword(this);
                cambiarPassword();
                break;

            default:break;
        }
    }

    private void cambiarPassword() {
        final Cuenta cuenta = obtenerCuenta();

        if (cuenta == null) {
            Toast.makeText(this, "Se produjo un error al obtener la cuenta.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            final String password_actual = textInputEditTextIngresaPasswordActual.getText().toString();
            final String nuevo_password = textInputEditTextIngresaNuevoPassword.getText().toString();
            final String repetir_password = textInputEditTextRepiteNuevoPassword.getText().toString();

            if (password_actual == null || password_actual.isEmpty()) {
                textInputLayoutIngresaPasswordActual.setError("Ingresa tu contraseña actual.");
            } else if (nuevo_password == null || nuevo_password.isEmpty()) {
                textInputLayoutIngresaPasswordActual.setError(null);
                textInputLayoutIngresaNuevoPassword.setError("Ingresa tu nueva contraseña.");
            } else if (repetir_password == null || repetir_password.isEmpty()) {
                textInputLayoutIngresaNuevoPassword.setError(null);
                textInputLayoutRepiteNuevoPassword.setError("Repite tu nueva contraseña.");
            } else if (!nuevo_password.equals(repetir_password)) {
                textInputLayoutIngresaNuevoPassword.setError("La contraseña no coincide.");
                textInputLayoutRepiteNuevoPassword.setError("La contraseña no coincide.");
                Toast.makeText(this, "La nueva contraseña no coincide", Toast.LENGTH_SHORT).show();
            } else {
                textInputLayoutIngresaNuevoPassword.setError(null);
                textInputLayoutRepiteNuevoPassword.setError(null);

                presenterCuenta.modificarPassword(cuenta, password_actual, nuevo_password);
            }
        }
    }

    private Cuenta obtenerCuenta() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("kytcla", Context.MODE_PRIVATE);
            final int codigo_persona = sharedPreferences.getInt("codigo_persona", 0);
            final String foto = sharedPreferences.getString("foto", null);
            final String nombre = sharedPreferences.getString("nombre", null);
            final String apellido = sharedPreferences.getString("apellido", null);
            final String fecha_nacimiento = sharedPreferences.getString("fecha_nacimiento", null);
            final String sexo = sharedPreferences.getString("sexo", null);
            final String pais = sharedPreferences.getString("pais", null);
            final String ciudad = sharedPreferences.getString("ciudad", null);
            final String direccion_domicilio = sharedPreferences.getString("direccion_domicilio", null);
            final String presentacion = sharedPreferences.getString("presentacion", null);
            final boolean estado_persona = sharedPreferences.getBoolean("estado_persona", false);

            final int codigo_cuenta = sharedPreferences.getInt("codigo_cuenta", 0);
            final String correo_electronico = sharedPreferences.getString("correo_electronico", null);
            final boolean estado_cuenta = sharedPreferences.getBoolean("estado_cuenta", false);

            if (codigo_persona <= 0 || foto == null || nombre == null || apellido == null || fecha_nacimiento == null || sexo == null || estado_persona == false || codigo_cuenta <= 0 || correo_electronico == null || estado_cuenta == false) {
                return null;
            } else {
                final Persona persona = new Persona(codigo_persona, foto, nombre, apellido, fecha_nacimiento, sexo, pais, ciudad, direccion_domicilio, presentacion, true);
                return new Cuenta(codigo_cuenta, persona, correo_electronico, null, true);
            }
        } catch (final Exception exception) {
            Toast.makeText(this, "Se produjo un error al obtener la cuenta.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    // region CallBack

    @Override
    public void passwordModificado(final String msg) {
        new MaterialAlertDialogBuilder(this).
                setTitle("Kytcla - Educación Contínua").
                setMessage("Contraseña Modificada\n\nVuelve a iniciar sesión.").
                setPositiveButton("Aceptar", (dialogInterface, i) -> {
                    getSharedPreferences("kytcla", Context.MODE_PRIVATE).edit().clear().commit();
                    finish();
                    startActivity(new Intent(this, SplashActivity.class));
                }).
                show();
        getSharedPreferences("kytcla", Context.MODE_PRIVATE).edit().clear().commit();
        finish();
        startActivity(new Intent(this, SplashActivity.class));
    }

    @Override
    public void passwordIncorrecto(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorModificarPassword(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoModificarPassword(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    // endregion
}
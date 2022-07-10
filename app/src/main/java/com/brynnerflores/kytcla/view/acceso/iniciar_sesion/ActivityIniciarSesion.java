package com.brynnerflores.kytcla.view.acceso.iniciar_sesion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.brynnerflores.kytcla.MainActivity;
import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.presenter.PresenterAcceso;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ActivityIniciarSesion extends AppCompatActivity implements View.OnClickListener, PresenterAcceso.CallBackIniciarSesion {

    // region Variables

    private TextInputLayout textInputLayoutCorreoElectronico;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText textInputEditTextCorreoElectronico;
    private TextInputEditText textInputEditTextPassword;
    private MaterialCheckBox materialCheckBoxMantenerSesionIniciada;
    private MaterialButton materialButtonIngresar;
    private MaterialButton materialButtonOlvideMiContrasena;

    private PresenterAcceso presenterAcceso;

    private AlertDialog alertDialog;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    // endregion

    // region Metodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        textInputLayoutCorreoElectronico = findViewById(R.id.text_input_layout_iniciar_sesion_correo_electronico);
        textInputLayoutPassword = findViewById(R.id.text_input_layout_iniciar_sesion_correo_electronico);
        textInputEditTextCorreoElectronico = findViewById(R.id.text_input_edit_text_iniciar_sesion_correo_electronico);
        textInputEditTextPassword = findViewById(R.id.text_input_edit_text_iniciar_sesion_password);
        materialCheckBoxMantenerSesionIniciada = findViewById(R.id.material_check_box_iniciar_sexion_mantener_sesion_iniciada);
        materialButtonIngresar = findViewById(R.id.material_button_iniciar_sesion_ingresar);
        materialButtonOlvideMiContrasena = findViewById(R.id.material_button_iniciar_sesion_olvide_mi_contrasena);

        materialButtonIngresar.setOnClickListener(this);
        materialButtonOlvideMiContrasena.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.material_button_iniciar_sesion_ingresar:
                final String correoElectronico = textInputEditTextCorreoElectronico.getText().toString();
                final String password = textInputEditTextPassword.getText().toString();

                if (correoElectronico.isEmpty()) {
                    textInputLayoutCorreoElectronico.setError("Ingresa tu correo electrónico");
                    Toast.makeText(this, "Completa todos los campos.", Toast.LENGTH_LONG).show();
                } else if (password.isEmpty()) {
                    textInputLayoutCorreoElectronico.setError(null);
                    textInputLayoutPassword.setError("Ingresa tu contraseña");
                    Toast.makeText(this, "Completa todos los campos.", Toast.LENGTH_LONG).show();
                } else {
                    textInputLayoutPassword.setError(null);

                    materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                    materialAlertDialogBuilder.setCancelable(false);
                    materialAlertDialogBuilder.setView(R.layout.progres_dialog);
                    alertDialog = materialAlertDialogBuilder.show();

                    presenterAcceso = new PresenterAcceso(this);
                    presenterAcceso.setCallBackIniciarSesion(this);
                    presenterAcceso.iniciarSesion(correoElectronico, password);
                }
                break;

            case R.id.material_button_iniciar_sesion_olvide_mi_contrasena:
                startActivity(new Intent(this, ActivityRecuperarCuenta.class));
                break;

            default:
                break;
        }
    }

    // endregion

    // region CallBack

    @Override
    public void accesoConcedido(final String msg, final Cuenta cuenta) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("kytcla", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("codigo_persona", cuenta.getPersona().getCodigoPersona());
            editor.putString("foto", cuenta.getPersona().getFoto());
            editor.putString("nombre", cuenta.getPersona().getNombre());
            editor.putString("apellido", cuenta.getPersona().getApellido());
            editor.putString("fecha_nacimiento", cuenta.getPersona().getFechaNacimiento());
            editor.putString("sexo", cuenta.getPersona().getSexo());
            editor.putString("pais", cuenta.getPersona().getPais());
            editor.putString("ciudad", cuenta.getPersona().getCiudad());
            editor.putString("direccion_domicilio", cuenta.getPersona().getDireccionDomicilio());
            editor.putString("presentacion", cuenta.getPersona().getPresentacion());
            editor.putBoolean("estado_persona", cuenta.getPersona().isEstado());

            editor.putInt("codigo_cuenta", cuenta.getCodigoCuenta());
            editor.putString("correo_electronico", cuenta.getCorreoElectronico());
            editor.putBoolean("estado_cuenta", cuenta.isEstado());

            editor.commit();

            alertDialog.dismiss();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        } catch (final Exception exception) {
            alertDialog.dismiss();
            Toast.makeText(this, "Se produjo un error, vuelve a iniciar sesión", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(this, ActivityIniciarSesion.class));
        }
    }

    @Override
    public void credencialesInvalidos(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorIniciarSesion(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoIniciarSesion(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    // endregion
}
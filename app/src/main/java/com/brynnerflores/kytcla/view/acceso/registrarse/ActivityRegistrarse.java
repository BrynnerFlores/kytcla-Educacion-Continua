package com.brynnerflores.kytcla.view.acceso.registrarse;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.Persona;
import com.brynnerflores.kytcla.presenter.PresenterAcceso;
import com.brynnerflores.kytcla.view.acceso.iniciar_sesion.ActivityIniciarSesion;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Calendar;

public class ActivityRegistrarse extends AppCompatActivity implements View.OnClickListener, PresenterAcceso.CallBackRegistrarse {

    // region Variables

    private TextInputLayout textInputLayoutNombre;
    private TextInputLayout textInputLayoutApellido;
    private TextInputLayout textInputLayoutFechaNacimiento;
    private TextInputLayout textInputLayoutSexo;
    private TextInputLayout textInputLayoutCorreoElectronico;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText textInputEditTextNombre;
    private TextInputEditText textInputEditTextApellido;
    private TextInputEditText textInputEditTextFechaNacimiento;
    private AutoCompleteTextView autoCompleteTextViewSexo;
    private TextInputEditText textInputEditTextCorreoElectronico;
    private TextInputEditText textInputEditTextPassword;
    private MaterialButton materialButtonRegistrarse;

    private PresenterAcceso presenterAcceso;

    private AlertDialog alertDialog;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    // endregion

    // region Metodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        // Enlazando componentes
        textInputLayoutNombre = findViewById(R.id.text_input_layout_registrarse_nombre);
        textInputLayoutApellido = findViewById(R.id.text_input_layout_registrarse_apellido);
        textInputLayoutFechaNacimiento = findViewById(R.id.text_input_layout_registrarse_fecha_nacimiento);
        textInputLayoutSexo = findViewById(R.id.text_input_layout_registrarse_sexo);
        textInputLayoutCorreoElectronico = findViewById(R.id.text_input_layout_registrarse_correo_electronico);
        textInputLayoutPassword = findViewById(R.id.text_input_layout_registrarse_password);
        textInputEditTextNombre = findViewById(R.id.text_input_edit_text_registrarse_nombre);
        textInputEditTextApellido = findViewById(R.id.text_input_edit_text_registrarse_apellido);
        textInputEditTextFechaNacimiento = findViewById(R.id.text_input_edit_text_registrarse_fecha_nacimiento);
        autoCompleteTextViewSexo = findViewById(R.id.auto_complete_text_view_registrarse_sexo);
        textInputEditTextCorreoElectronico = findViewById(R.id.text_input_edit_text_registrarse_correo_electronico);
        textInputEditTextPassword = findViewById(R.id.text_input_edit_text_registrarse_password);
        materialButtonRegistrarse = findViewById(R.id.material_button_registrarse_registrarme);

        final String item_sexo [] = {"Masculino", "Femenino"};
        final ArrayAdapter<String> arrayAdapterSexo = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, item_sexo);
        autoCompleteTextViewSexo.setAdapter(arrayAdapterSexo);

        textInputEditTextFechaNacimiento.setOnClickListener(this);
        materialButtonRegistrarse.setOnClickListener(this);
    }

    // endregion

    // region Metodos

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.material_button_registrarse_registrarme:
                final String nombre = textInputEditTextNombre.getText().toString();
                final String apellido = textInputEditTextApellido.getText().toString();
                final String fecha_nacimiento = textInputEditTextFechaNacimiento.getText().toString();
                final String sexo = autoCompleteTextViewSexo.getText().toString();
                final String correo_electronico = textInputEditTextCorreoElectronico.getText().toString();
                final String password = textInputEditTextPassword.getText().toString();

                if (nombre.isEmpty()) {
                    textInputLayoutNombre.setError("Ingresa tu nombre");
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                } else if (apellido.isEmpty()) {
                    textInputLayoutNombre.setError(null);
                    textInputLayoutApellido.setError("Ingresa tu apellido");
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                } else if (fecha_nacimiento.isEmpty()) {
                    textInputLayoutApellido.setError(null);
                    textInputLayoutFechaNacimiento.setError("Selecciona tu fecha de nacimiento");
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                } else if (sexo.isEmpty()) {
                    textInputLayoutFechaNacimiento.setError(null);
                    textInputLayoutSexo.setError("Selecciona tu sexo");
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                } else if (correo_electronico.isEmpty()) {
                    textInputLayoutSexo.setError(null);
                    textInputLayoutCorreoElectronico.setError("Ingresa tu correo electrónico");
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    textInputLayoutCorreoElectronico.setError(null);
                    textInputLayoutPassword.setError("Ingresa una contraseña");
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    textInputLayoutPassword.setError(null);

                    final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blank_profile);
                    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    final byte[] imageByte = byteArrayOutputStream.toByteArray();
                    final String foto = Base64.getEncoder().encodeToString(imageByte);

                    final Persona persona = new Persona(foto, nombre, apellido, fecha_nacimiento, sexo, "", "", "", "", true);
                    final Cuenta cuenta = new Cuenta(persona, correo_electronico, password, true);

                    materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                    materialAlertDialogBuilder.setCancelable(false);
                    materialAlertDialogBuilder.setView(R.layout.progres_dialog);
                    alertDialog = materialAlertDialogBuilder.show();

                    presenterAcceso = new PresenterAcceso(this);
                    presenterAcceso.setCallBackRegistrarse(this);
                    presenterAcceso.registrarse(cuenta);
                }
                break;

            case R.id.text_input_edit_text_registrarse_fecha_nacimiento:
                final Calendar calendar = Calendar.getInstance();
                final DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, dayOfMonth) -> textInputEditTextFechaNacimiento.setText(year + "-" + (month + 1) +"-" + dayOfMonth), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
                datePickerDialog.show();
                break;

            default:break;
        }
    }

    // endregion

    // region CallBack

    @Override
    public void registrado(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, ActivityIniciarSesion.class));
    }

    @Override
    public void emailExiste(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorRegistrar(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoRegistrar(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    // endregion
}
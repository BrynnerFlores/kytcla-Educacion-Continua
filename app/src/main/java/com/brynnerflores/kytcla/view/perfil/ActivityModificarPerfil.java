package com.brynnerflores.kytcla.view.perfil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.SplashActivity;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.presenter.cuenta.PresenterCuenta;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Calendar;

public class ActivityModificarPerfil extends AppCompatActivity implements View.OnClickListener, PresenterCuenta.CallBackModificarCuenta {
    
    // region Variables
    
    private Cuenta cuenta;
    private MaterialToolbar materialToolbar;
    private PresenterCuenta presenterCuenta;

    private ShapeableImageView shapeableImageViewFotoPerfil;
    private TextInputLayout textInputLayoutNombre;
    private TextInputLayout textInputLayoutApellido;
    private TextInputLayout textInputLayoutPresentacion;
    private TextInputLayout textInputLayoutCorreoElectronico;
    private TextInputLayout textInputLayoutFechaNacimiento;
    private TextInputLayout textInputLayoutSexo;
    private TextInputLayout textInputLayoutPais;
    private TextInputLayout textInputLayoutCiudad;
    private TextInputLayout textInputLayoutDireccionDomicilio;
    private TextInputEditText textInputEditTextNombre;
    private TextInputEditText textInputEditTextApellido;
    private TextInputEditText textInputEditTextPresentacion;
    private TextInputEditText textInputEditTextCorreoElectronico;
    private TextInputEditText textInputEditTextFechaNacimiento;
    private AutoCompleteTextView autoCompleteTextViewSexo;
    private TextInputEditText textInputEditTextPais;
    private TextInputEditText textInputEditTextCiudad;
    private TextInputEditText textInputEditTextDireccionDomicilio;
    private MaterialButton materialButtonCancelar;
    private MaterialButton materialButtonGuardar;

    private String fotoBase64;

    private AlertDialog alertDialog;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;
    
    // endregion
    
    // region Metodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_perfil);

        cuenta = (Cuenta) getIntent().getSerializableExtra("CUENTA");

        if (cuenta == null) {
            Toast.makeText(this, "No se pudo obtener la cuenta.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            materialToolbar = findViewById(R.id.material_toolbar_modificar_perfil_title);
            shapeableImageViewFotoPerfil = findViewById(R.id.shapeable_image_view_modificar_perfil_foto);
            textInputLayoutNombre = findViewById(R.id.text_input_layout_modificar_perfil_nombre);
            textInputLayoutApellido = findViewById(R.id.text_input_layout_modificar_perfil_apellido);
            textInputLayoutPresentacion = findViewById(R.id.text_input_layout_modificar_perfil_presentacion);
            textInputLayoutCorreoElectronico = findViewById(R.id.text_input_layout_modificar_perfil_correo_electronico);
            textInputLayoutFechaNacimiento = findViewById(R.id.text_input_layout_modificar_perfil_fecha_nacimiento);
            textInputLayoutSexo = findViewById(R.id.text_input_layout_modificar_perfil_sexo);
            textInputLayoutPais = findViewById(R.id.text_input_layout_modificar_perfil_pais);
            textInputLayoutCiudad = findViewById(R.id.text_input_layout_modificar_perfil_ciudad);
            textInputLayoutDireccionDomicilio = findViewById(R.id.text_input_layout_modificar_perfil_direccion_domicilio);
            textInputEditTextNombre = findViewById(R.id.text_input_edit_text_modificar_perfil_nombre);
            textInputEditTextApellido = findViewById(R.id.text_input_edit_text_modificar_perfil_apellido);
            textInputEditTextPresentacion = findViewById(R.id.text_input_edit_text_modificar_perfil_presentacion);
            textInputEditTextCorreoElectronico = findViewById(R.id.text_input_edit_text_modificar_perfil_correo_electronico);
            textInputEditTextFechaNacimiento = findViewById(R.id.text_input_edit_text_modificar_perfil_fecha_nacimiento);
            autoCompleteTextViewSexo = findViewById(R.id.auto_complete_text_view_modificar_perfil_sexo);
            textInputEditTextPais = findViewById(R.id.text_input_edit_text_modificar_perfil_pais);
            textInputEditTextCiudad = findViewById(R.id.text_input_edit_text_modificar_perfil_ciudad);
            textInputEditTextDireccionDomicilio = findViewById(R.id.text_input_edit_text_modificar_perfil_direccion_domicilio);
            materialButtonCancelar = findViewById(R.id.material_button_modificar_perfil_cancelar);
            materialButtonGuardar = findViewById(R.id.material_button_modificar_perfil_guardar);

            fotoBase64 = cuenta.getPersona().getFoto();
            final byte[] bytes = Base64.getDecoder().decode(cuenta.getPersona().getFoto());
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            shapeableImageViewFotoPerfil.setImageBitmap(bitmap);
            textInputEditTextNombre.setText(cuenta.getPersona().getNombre());
            textInputEditTextApellido.setText(cuenta.getPersona().getApellido());
            textInputEditTextPresentacion.setText(cuenta.getPersona().getPresentacion());
            textInputEditTextCorreoElectronico.setText(cuenta.getCorreoElectronico());
            textInputEditTextFechaNacimiento.setText(cuenta.getPersona().getFechaNacimiento());
            autoCompleteTextViewSexo.setText(cuenta.getPersona().getSexo().equals("M") ? "Masculino" : "Femenino");
            textInputEditTextPais.setText(cuenta.getPersona().getPais());
            textInputEditTextCiudad.setText(cuenta.getPersona().getCiudad());
            textInputEditTextDireccionDomicilio.setText(cuenta.getPersona().getDireccionDomicilio());

            setSupportActionBar(materialToolbar);

            materialToolbar.setNavigationOnClickListener(view -> {
                finish();
            });

            final String[] sexo = {"Masculino", "Femenino"};
            final ArrayAdapter<String> arrayAdapterSexo = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, sexo);
            autoCompleteTextViewSexo.setAdapter(arrayAdapterSexo);

            shapeableImageViewFotoPerfil.setOnClickListener(this);
            textInputEditTextFechaNacimiento.setOnClickListener(this);
            materialButtonCancelar.setOnClickListener(this);
            materialButtonGuardar.setOnClickListener(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {
                final InputStream inputStream = getContentResolver().openInputStream(data.getData());
                final byte[] byteArray = getImageBytes(inputStream);
                fotoBase64 = Base64.getEncoder().encodeToString(byteArray);
                shapeableImageViewFotoPerfil.setImageURI(data.getData());
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        } catch (final Exception exception) {
            Toast.makeText(this, "Se produjo un error, vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.shapeable_image_view_modificar_perfil_foto:
                final String items[] = {"Abrir Camara", "Abrir Galeria", "Eliminar Foto"};
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Kytcla")
                        .setItems(items, (dialogInterface, i) -> {
                            switch (i) {
                                case 0:
                                    ImagePicker.Companion.with(this)
                                            .crop(1f, 1f)
                                            .cameraOnly()
                                            .compress(90)
                                            .start();
                                    break;

                                case 1:
                                    ImagePicker.Companion.with(this)
                                            .crop(1f, 1f)
                                            .galleryOnly()
                                            .compress(90)
                                            .start();
                                    break;

                                case 2:
                                    final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blank_profile);
                                    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                                    final byte[] imageByte = byteArrayOutputStream.toByteArray();
                                    fotoBase64 = Base64.getEncoder().encodeToString(imageByte);
                                    shapeableImageViewFotoPerfil.setImageDrawable(getDrawable(R.drawable.blank_profile));
                                    break;

                                default:
                                    break;
                            }
                        })
                        .show();
                break;

            case R.id.text_input_edit_text_modificar_perfil_fecha_nacimiento:
                final Calendar calendar = Calendar.getInstance();
                final DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, dayOfMonth) -> textInputEditTextFechaNacimiento.setText(year + "-" + (month + 1) +"-" + dayOfMonth), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
                datePickerDialog.show();
                break;

            case R.id.material_button_modificar_perfil_cancelar:
                finish();
                break;

            case R.id.material_button_modificar_perfil_guardar:
                materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                materialAlertDialogBuilder.setCancelable(false);
                materialAlertDialogBuilder.setView(R.layout.progres_dialog);
                alertDialog = materialAlertDialogBuilder.show();

                modificarCuenta();

                break;

            default:break;
        }
    }

    private void modificarCuenta() {
        presenterCuenta = new PresenterCuenta(this);
        presenterCuenta.setCallBackModificarCuenta(this);

        final String nombre = textInputEditTextNombre.getText().toString();
        final String apellido = textInputEditTextApellido.getText().toString();
        final String presentacion = textInputEditTextPresentacion.getText().toString();
        final String correo_electronico = textInputEditTextCorreoElectronico.getText().toString();
        final String fecha_nacimiento = textInputEditTextFechaNacimiento.getText().toString();
        final String sexo = autoCompleteTextViewSexo.getText().toString();
        final String pais = textInputEditTextPais.getText().toString();
        final String ciudad = textInputEditTextCiudad.getText().toString();
        final String direccion_domicilio = textInputEditTextDireccionDomicilio.getText().toString();

        if (fotoBase64.isEmpty() || fotoBase64 == null) {
            Toast.makeText(this, "Selecciona una foto de perfil.", Toast.LENGTH_SHORT).show();
        } else if (nombre.isEmpty()) {
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
        } else {
            cuenta.getPersona().setFoto(fotoBase64);
            cuenta.getPersona().setNombre(nombre);
            cuenta.getPersona().setApellido(apellido);
            cuenta.getPersona().setFechaNacimiento(fecha_nacimiento);
            cuenta.getPersona().setSexo(sexo.equals("Masculino")? "M" : "F");
            cuenta.getPersona().setPais(pais);
            cuenta.getPersona().setCiudad(ciudad);
            cuenta.getPersona().setDireccionDomicilio(direccion_domicilio);
            cuenta.getPersona().setPresentacion(presentacion);

            cuenta.setCorreoElectronico(correo_electronico);

            presenterCuenta.modificarCuenta(cuenta);
        }
    }

    private byte[] getImageBytes(final InputStream inputStream) {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len = 0;

            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (final Exception exception) {
            return null;
        }
    }

    // endregion

    // region CallBack

    @Override
    public void cuentaModificada(final String msg) {
        alertDialog.dismiss();
        new MaterialAlertDialogBuilder(this).
                setCancelable(false).
                setTitle("Kytcla - Educación Contínua").
                setMessage("Tu información fue modificada.\n\nDebes volver a iniciar sesión para actualizar los cambios.").
                setPositiveButton("Aceptar", (dialogInterface, i) -> {
                    getSharedPreferences("kytcla", Context.MODE_PRIVATE).edit().clear().commit();
                    finish();
                    startActivity(new Intent(this, SplashActivity.class));
                }).show();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void correoElectronicoExiste(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorModificarCuenta(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoModificarCuenta(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    // endregion
}
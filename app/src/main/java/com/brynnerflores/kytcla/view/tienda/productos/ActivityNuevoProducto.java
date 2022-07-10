package com.brynnerflores.kytcla.view.tienda.productos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Producto;
import com.brynnerflores.kytcla.presenter.productos.PresenterProducto;
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

public class ActivityNuevoProducto extends AppCompatActivity implements View.OnClickListener, PresenterProducto.CallBackRegistrarProducto {

    // region Variables

    private MaterialToolbar materialToolbar;
    private TextInputLayout textInputLayoutNombre;
    private TextInputLayout textInputLayoutCategoria;
    private TextInputLayout textInputLayoutDescripcion;
    private TextInputLayout textInputLayoutPrecio;
    private ShapeableImageView shapeableImageViewFoto;
    private TextInputEditText textInputEditTextNombre;
    private AutoCompleteTextView autoCompleteTextViewCategoria;
    private TextInputEditText textInputEditTextDescripcion;
    private TextInputEditText textInputEditTextPrecio;
    private MaterialButton materialButtonCancelar;
    private MaterialButton materialButtonRegistrar;

    private PresenterProducto presenterProducto;

    private String fotoBase64;

    private AlertDialog alertDialog;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    //endregion

    // region Metodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        materialToolbar = findViewById(R.id.material_toolbar_activity_nuevo_producto);
        textInputLayoutNombre = findViewById(R.id.text_input_layout_nuevo_producto_nombre);
        textInputLayoutCategoria = findViewById(R.id.text_input_layout_nuevo_producto_categoria);
        textInputLayoutDescripcion = findViewById(R.id.text_input_layout_nuevo_producto_descripcion);
        textInputLayoutPrecio = findViewById(R.id.text_input_layout_nuevo_producto_precio);
        shapeableImageViewFoto = findViewById(R.id.shapeable_image_view_nuevo_producto_foto);
        textInputEditTextNombre = findViewById(R.id.text_input_edit_text_nuevo_producto_nombre);
        autoCompleteTextViewCategoria = findViewById(R.id.auto_complete_text_view_nuevo_producto_categoria);
        textInputEditTextDescripcion = findViewById(R.id.text_input_edit_text_nuevo_producto_descripcion);
        textInputEditTextPrecio = findViewById(R.id.text_input_edit_text_nuevo_producto_precio);
        materialButtonCancelar = findViewById(R.id.material_button_nuevo_producto_cancelar);
        materialButtonRegistrar = findViewById(R.id.material_button_nuevo_producto_registrar);

        setSupportActionBar(materialToolbar);

        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        final String[] categoria = {"Hogar"};
        final ArrayAdapter<String> arrayAdapterCategoria = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, categoria);
        autoCompleteTextViewCategoria.setAdapter(arrayAdapterCategoria);

        fotoBase64 = getBase64ImageDefault();

        shapeableImageViewFoto.setOnClickListener(this);
        materialButtonCancelar.setOnClickListener(this);
        materialButtonRegistrar.setOnClickListener(this);
    }

    private void registrar() {
        try {
            final String nombre = textInputEditTextNombre.getText().toString();
            final String categoria = autoCompleteTextViewCategoria.getText().toString();
            final String descripcion = textInputEditTextDescripcion.getText().toString();
            final String precio = textInputEditTextPrecio.getText().toString();

            if (nombre.isEmpty()) {
                textInputLayoutNombre.setError("Ingresa el nombre.");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (categoria.isEmpty()) {
                textInputLayoutNombre.setError(null);
                textInputLayoutCategoria.setError("Selecciona la categoria.");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (descripcion.isEmpty()) {
                textInputLayoutCategoria.setError(null);
                textInputLayoutDescripcion.setError("Ingresa una descripción.");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (precio.isEmpty()) {
                textInputLayoutDescripcion.setError(null);
                textInputLayoutPrecio.setError("Ingresa el precio.");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                textInputLayoutPrecio.setError(null);

                final Producto producto = new Producto(categoria, fotoBase64, nombre, descripcion, precio, true);
                presenterProducto = new PresenterProducto(this);
                presenterProducto.setCallBackRegistrarProducto(this);
                presenterProducto.registrarProducto(producto);
            }
        } catch (final Exception exception) {
            Toast.makeText(this, "Se produjo un error desconocido, vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getBase64ImageDefault() {
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_placeholder);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        final byte[] imageByte = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageByte);
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
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.shapeable_image_view_nuevo_producto_foto:
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
                                    final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_placeholder);
                                    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                    final byte[] imageByte = byteArrayOutputStream.toByteArray();
                                    fotoBase64 = Base64.getEncoder().encodeToString(imageByte);
                                    shapeableImageViewFoto.setImageDrawable(getDrawable(R.drawable.image_placeholder));
                                    break;

                                default:
                                    break;
                            }
                        })
                        .show();
                break;

            case R.id.material_button_nuevo_producto_cancelar:
                finish();
                break;

            case R.id.material_button_nuevo_producto_registrar:
                materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                materialAlertDialogBuilder.setCancelable(false);
                materialAlertDialogBuilder.setView(R.layout.progres_dialog);
                alertDialog = materialAlertDialogBuilder.show();

                registrar();

                break;

            default:break;
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
                shapeableImageViewFoto.setImageURI(data.getData());
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
    public void productoRegistrado(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void productoExiste(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorRegistrarProducto(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoRegistrarProducto(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    // endregion
}
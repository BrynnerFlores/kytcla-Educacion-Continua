package com.brynnerflores.kytcla.view.curso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Curso;
import com.brynnerflores.kytcla.presenter.cursos.PresenterCurso;
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

public class ActivityNuevoCurso extends AppCompatActivity implements View.OnClickListener, PresenterCurso.CallBackCrearCurso {

    // region Variables

    private MaterialToolbar materialToolbar;
    private TextInputLayout textInputLayoutArea;
    private TextInputLayout textInputLayoutNombre;
    private TextInputLayout textInputLayoutObjetivo;
    private TextInputLayout textInputLayoutDirigido;
    private TextInputLayout textInputLayoutDictado;
    private TextInputLayout textInputLayoutContenido;
    private TextInputLayout textInputLayoutFechaInicio;
    private TextInputLayout textInputLayoutHorarios;
    private TextInputLayout textInputLayoutDuracion;
    private TextInputLayout textInputLayoutModalidad;
    private TextInputLayout textInputLayoutCosto;
    private TextInputLayout textInputLayoutEnlaceInscripcion;
    private ShapeableImageView shapeableImageViewLogoCurso;
    private AutoCompleteTextView autoCompleteTextViewAreaCurso;
    private TextInputEditText textInputEditTextNombre;
    private TextInputEditText textInputEditTextObjetivo;
    private TextInputEditText textInputEditTextDirigido;
    private TextInputEditText textInputEditTextDictado;
    private TextInputEditText textInputEditTextContenido;
    private TextInputEditText textInputEditTextFechaInicio;
    private TextInputEditText textInputEditTextHorarios;
    private TextInputEditText textInputEditTextDuracion;
    private AutoCompleteTextView autoCompleteTextViewModalidad;
    private TextInputEditText textInputEditTextCosto;
    private TextInputEditText textInputEditTextEnlaceInscripcion;
    private MaterialButton materialButtonCancelar;
    private MaterialButton materialButtonCrearCurso;

    private PresenterCurso presenterCurso;

    private String logoBase64;

    private AlertDialog alertDialog;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    // endregion

    // region Metodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_curso);

        // Enlazando componentes
        materialToolbar = findViewById(R.id.material_toolbar_activity_nuevo_curso);
        textInputLayoutArea = findViewById(R.id.text_input_layout_nuevo_curso_area);
        textInputLayoutNombre = findViewById(R.id.text_input_layout_nuevo_curso_nombre);
        textInputLayoutObjetivo = findViewById(R.id.text_input_layout_nuevo_curso_objetivo);
        textInputLayoutDirigido = findViewById(R.id.text_input_layout_nuevo_curso_dirigido);
        textInputLayoutDictado = findViewById(R.id.text_input_layout_nuevo_curso_dictado);
        textInputLayoutContenido = findViewById(R.id.text_input_layout_nuevo_curso_contenido);
        textInputLayoutFechaInicio = findViewById(R.id.text_input_layout_nuevo_curso_fecha_inicio);
        textInputLayoutHorarios = findViewById(R.id.text_input_layout_nuevo_curso_horarios);
        textInputLayoutDuracion = findViewById(R.id.text_input_layout_nuevo_curso_duracion);
        textInputLayoutModalidad = findViewById(R.id.text_input_layout_nuevo_curso_modalidad);
        textInputLayoutCosto = findViewById(R.id.text_input_layout_nuevo_curso_costo);
        textInputLayoutEnlaceInscripcion = findViewById(R.id.text_input_layout_nuevo_curso_enlace_inscripcion);
        shapeableImageViewLogoCurso = findViewById(R.id.shapeable_img_view_nuevo_curso_logo);
        autoCompleteTextViewAreaCurso = findViewById(R.id.auto_complete_txtview_nuevo_curso_area);
        textInputEditTextNombre = findViewById(R.id.txtinp_edtxt_nuevo_curso_nombre);
        textInputEditTextObjetivo = findViewById(R.id.txtinp_edtxt_nuevo_curso_objetivo);
        textInputEditTextDirigido = findViewById(R.id.txtinp_edtxt_nuevo_curso_dirigido);
        textInputEditTextDictado = findViewById(R.id.txtinp_edtxt_nuevo_curso_dictado);
        textInputEditTextContenido = findViewById(R.id.txtinp_edtxt_nuevo_curso_contenido);
        textInputEditTextFechaInicio = findViewById(R.id.txtinp_edtxt_nuevo_curso_fecha_inicio);
        textInputEditTextHorarios = findViewById(R.id.txtinp_edtxt_nuevo_curso_horarios);
        textInputEditTextDuracion = findViewById(R.id.txtinp_edtxt_nuevo_curso_duracion);
        autoCompleteTextViewModalidad = findViewById(R.id.auto_complete_txtview_nuevo_curso_modalidad);
        textInputEditTextCosto = findViewById(R.id.txtinp_edtxt_nuevo_curso_costo);
        textInputEditTextEnlaceInscripcion = findViewById(R.id.txtinp_edtxt_nuevo_curso_enlace_inscripcion);
        materialButtonCancelar = findViewById(R.id.mtrl_button_nuevo_curso_cancelar);
        materialButtonCrearCurso = findViewById(R.id.mtrl_button_nuevo_curso_crear_curso);

        setSupportActionBar(materialToolbar);
        materialToolbar.setNavigationOnClickListener(view -> { finish(); });

        shapeableImageViewLogoCurso.setOnClickListener(this);

        final String[] area = {"Area de Idiomas", "Area Tributaria y Finanzas", "Area de Comercio Exterior y Aduanas", "Area de las TIC"};
        final String[] modalidad = {"Virtual", "Semipresencial", "Presencial"};
        final ArrayAdapter<String> arrayAdapterArea = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, area);
        final ArrayAdapter<String> arrayAdapterModalidad = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, modalidad);
        autoCompleteTextViewAreaCurso.setAdapter(arrayAdapterArea);
        autoCompleteTextViewModalidad.setAdapter(arrayAdapterModalidad);

        logoBase64 = getBase64ImageDefault();

        materialButtonCancelar.setOnClickListener(this);
        materialButtonCrearCurso.setOnClickListener(this);
    }

    private String getBase64ImageDefault() {
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_placeholder);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        final byte[] imageByte = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageByte);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.shapeable_img_view_nuevo_curso_logo:
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
                                    logoBase64 = Base64.getEncoder().encodeToString(imageByte);
                                    shapeableImageViewLogoCurso.setImageDrawable(getDrawable(R.drawable.image_placeholder));
                                    break;

                                default:
                                    break;
                            }
                        })
                        .show();
                break;

            case R.id.mtrl_button_nuevo_curso_cancelar:
                finish();
                break;

            case R.id.mtrl_button_nuevo_curso_crear_curso:
                materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                materialAlertDialogBuilder.setCancelable(false);
                materialAlertDialogBuilder.setView(R.layout.progres_dialog);
                alertDialog = materialAlertDialogBuilder.show();

                crearCurso();

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
                logoBase64 = Base64.getEncoder().encodeToString(byteArray);
                shapeableImageViewLogoCurso.setImageURI(data.getData());
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

    private void crearCurso() {
        try {
            final String area_curso = autoCompleteTextViewAreaCurso.getText().toString();
            final String nombre = textInputEditTextNombre.getText().toString();
            final String objetivo = textInputEditTextObjetivo.getText().toString();
            final String dirigido = textInputEditTextDirigido.getText().toString();
            final String dictado = textInputEditTextDictado.getText().toString();
            final String contenido = textInputEditTextContenido.getText().toString();
            final String fecha_inicio = textInputEditTextFechaInicio.getText().toString();
            final String horarios = textInputEditTextHorarios.getText().toString();
            final String duracion = textInputEditTextDuracion.getText().toString();
            final String modalidad = autoCompleteTextViewModalidad.getText().toString();
            final String costo = textInputEditTextCosto.getText().toString();
            final String enlace_inscripcion = textInputEditTextEnlaceInscripcion.getText().toString();
            
            if (logoBase64 == null || logoBase64.isEmpty()) {
                Toast.makeText(this, "Selecciona el logo del curso", Toast.LENGTH_SHORT).show();
            } else if (area_curso.isEmpty()) {
                textInputLayoutArea.setError("Selecciona el área");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (nombre.isEmpty()) {
                textInputLayoutArea.setError(null);
                textInputLayoutNombre.setError("Ingresa el nombre del curso");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (objetivo.isEmpty()) {
                textInputLayoutNombre.setError(null);
                textInputLayoutObjetivo.setError("Ingresa el objetivo");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (dirigido.isEmpty()) {
                textInputLayoutObjetivo.setError(null);
                textInputLayoutDirigido.setError("Ingresa a quién esta dirigido el curso");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (dictado.isEmpty()) {
                textInputLayoutDirigido.setError(null);
                textInputLayoutDictado.setError("Ingresa quién dictará el curso");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (contenido.isEmpty()) {
                textInputLayoutDictado.setError(null);
                textInputLayoutContenido.setError("Ingresa el contenido del curso");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (fecha_inicio.isEmpty()) {
                textInputLayoutContenido.setError(null);
                textInputLayoutFechaInicio.setError("Selecciona la fecha de inicio");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (horarios.isEmpty()) {
                textInputLayoutFechaInicio.setError(null);
                textInputLayoutHorarios.setError("Ingresa el horario del curso");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (duracion.isEmpty()) {
                textInputLayoutHorarios.setError(null);
                textInputLayoutDuracion.setError("Ingresa la duracion del curso");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (modalidad.isEmpty()) {
                textInputLayoutDuracion.setError(null);
                textInputLayoutModalidad.setError("Selecciona la modalidad");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (enlace_inscripcion.isEmpty()) {
                textInputLayoutDuracion.setError(null);
                textInputLayoutEnlaceInscripcion.setError("Ingresa el enlace de inscipción");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else if (costo.isEmpty()) {
                textInputLayoutEnlaceInscripcion.setError(null);
                textInputLayoutCosto.setError("Ingresa el costo del curso");
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                /*
                final Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), logoUri);
                final Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 200, 200, false);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmapScaled.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                bitmapScaled.recycle();
                 */

                final Curso curso = new Curso(logoBase64, area_curso, nombre, objetivo, dirigido, dictado, contenido, fecha_inicio, horarios, duracion, modalidad, costo, enlace_inscripcion,true);

                presenterCurso = new PresenterCurso(this);
                presenterCurso.setCallBackCrearCurso(this);
                presenterCurso.crearCurso(curso);
            }
        } catch (final Exception exception) {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
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

    @Override
    public void cursoCreado(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void cursoExiste(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorCrearCurso(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoCrearCurso(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    // endregion
}
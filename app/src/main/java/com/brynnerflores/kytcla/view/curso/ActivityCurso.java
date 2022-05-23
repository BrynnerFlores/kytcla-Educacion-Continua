package com.brynnerflores.kytcla.view.curso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.brynnerflores.kytcla.ActivityWebView;
import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.Curso;
import com.brynnerflores.kytcla.presenter.cursos.PresenterCurso;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import java.util.Base64;

public class ActivityCurso extends AppCompatActivity implements View.OnClickListener, PresenterCurso.CallBackGuardarCurso, PresenterCurso.CallBackEliminarCurso {

    // region Variables

    private Curso curso;
    private MaterialToolbar materialToolbar;
    private AppCompatImageView appCompatImageViewLogo;
    private MaterialTextView materialTextViewNombre;
    private MaterialTextView materialTextViewObjetivo;
    private MaterialTextView materialTextViewDirigido;
    private MaterialTextView materialTextViewDictado;
    private MaterialTextView materialTextViewContenido;
    private MaterialTextView materialTextViewFechaInicio;
    private MaterialTextView materialTextViewHorarios;
    private MaterialTextView materialTextViewDuracion;
    private MaterialTextView materialTextViewModalidad;
    private MaterialTextView materialTextViewCosto;
    private MaterialButton materialButtonInscribirme;
    private MaterialButton materialButtonContactarPorWhatsApp;

    private PresenterCurso presenterCurso;

    // endregion

    // region Metodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);

        curso = (Curso) getIntent().getSerializableExtra("CURSO");

        if (curso == null) {
            Toast.makeText(this, "No se recibió el parámetro esperado.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            materialToolbar = findViewById(R.id.material_toolbar_activity_curso);
            materialToolbar.setTitle(curso.getNombre());
            setSupportActionBar(materialToolbar);

            materialToolbar.setNavigationOnClickListener(view -> {
                finish();
            });

            appCompatImageViewLogo = findViewById(R.id.app_compact_image_view_curso_logo);
            materialTextViewNombre = findViewById(R.id.material_text_view_curso_nombre);
            materialTextViewObjetivo = findViewById(R.id.material_text_view_curso_objetivo);
            materialTextViewDirigido = findViewById(R.id.material_text_view_curso_dirigido);
            materialTextViewDictado = findViewById(R.id.material_text_view_curso_dictado);
            materialTextViewContenido = findViewById(R.id.material_text_view_curso_contenido);
            materialTextViewFechaInicio = findViewById(R.id.material_text_view_curso_fecha_inicio);
            materialTextViewHorarios = findViewById(R.id.material_text_view_curso_horarios);
            materialTextViewDuracion = findViewById(R.id.material_text_view_curso_duracion);
            materialTextViewModalidad = findViewById(R.id.material_text_view_curso_modalidad);
            materialTextViewCosto = findViewById(R.id.material_text_view_curso_costo);
            materialButtonInscribirme = findViewById(R.id.material_button_curso_inscribirme_al_curso);
            materialButtonContactarPorWhatsApp = findViewById(R.id.material_button_curso_enviar_mensaje_por_whatsapp);

            final byte[] bytes = Base64.getDecoder().decode(curso.getLogoCurso());
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            appCompatImageViewLogo.setImageBitmap(bitmap);
            materialTextViewNombre.setText(curso.getNombre());
            materialTextViewObjetivo.setText(curso.getObjetivo());
            materialTextViewDirigido.setText(curso.getDirigido());
            materialTextViewDictado.setText(curso.getDictado());
            materialTextViewContenido.setText(curso.getContenidos());
            materialTextViewFechaInicio.setText(curso.getFechaInicio());
            materialTextViewHorarios.setText(curso.getHorarios());
            materialTextViewDuracion.setText(curso.getDuracion());
            materialTextViewModalidad.setText(curso.getModalidad());
            materialTextViewCosto.setText(curso.getCostoCurso());
            materialButtonInscribirme.setOnClickListener(this);
            materialButtonContactarPorWhatsApp.setOnClickListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_curso, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toolbar_curso_modificar:
                startActivity(new Intent(this, ActivityModificarCurso.class).putExtra("CURSO", curso));
                return true;

            case R.id.menu_toolbar_curso_eliminar:
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Kytcla")
                        .setMessage("¿Eliminar el curso?")
                        .setNegativeButton("No", (dialogInterface, i) -> {

                        })
                        .setPositiveButton("Si", (dialogInterface, i) -> {
                            presenterCurso = new PresenterCurso(this);
                            presenterCurso.setCallBackEliminarCurso(this);
                            presenterCurso.eliminar(curso);
                        })
                        .show();
                return true;

            case R.id.menu_toolbar_curso_guardar:
                presenterCurso = new PresenterCurso(this);
                presenterCurso.setCallBackGuardarCurso(this);

                final Cuenta cuenta = obtenerCuenta();
                if (cuenta == null) {
                    Toast.makeText(this, "Se produjo un error al obtener la cuenta.", Toast.LENGTH_SHORT).show();
                } else {
                    presenterCurso.guardarCurso(cuenta, curso);
                }
                return true;

            default:return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.material_button_curso_inscribirme_al_curso:
                startActivity(new Intent(this, ActivityWebView.class).putExtra("URL", curso.getEnlaceInscripcion()));
                break;

            case R.id.material_button_curso_enviar_mensaje_por_whatsapp:
                break;

            default:break;
        }
    }

    private Cuenta obtenerCuenta() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("kytcla", Context.MODE_PRIVATE);
            final int codigo_cuenta = sharedPreferences.getInt("codigo_cuenta", 0);
            return new Cuenta(codigo_cuenta, null,null, null, true);
        } catch (final Exception exception) {
            return null;
        }
    }

    // endregion

    // region CallBack

    @Override
    public void cursoGuardado(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorGuardarCurso(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoGuardarCurso(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void cursoEliminado(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void errorEliminarCurso(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoEliminarCurso(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    // endregion
}
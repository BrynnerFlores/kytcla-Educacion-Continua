package com.brynnerflores.kytcla.view.curso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

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

import com.brynnerflores.kytcla.view.ActivityWebView;
import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.CursoPersonalizado;
import com.brynnerflores.kytcla.model.POJO.Persona;
import com.brynnerflores.kytcla.presenter.cursos.PresenterCurso;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import java.util.Base64;

public class ActivityCurso extends AppCompatActivity implements View.OnClickListener, PresenterCurso.CallBackGuardarCurso, PresenterCurso.CallBackEliminarCurso, PresenterCurso.CallBackEliminarCursoGuardado {

    // region Variables

    private CursoPersonalizado cursoPersonalizado;
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

    private AlertDialog alertDialog;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    // endregion

    // region Metodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);

        cursoPersonalizado = (CursoPersonalizado) getIntent().getSerializableExtra("CURSO_PERSONALIZADO");

        if (cursoPersonalizado == null) {
            Toast.makeText(this, "No se recibió el parámetro esperado.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            materialToolbar = findViewById(R.id.material_toolbar_activity_curso);
            materialToolbar.setTitle(cursoPersonalizado.getCurso().getNombre());
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

            final byte[] bytes = Base64.getDecoder().decode(cursoPersonalizado.getCurso().getLogoCurso());
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            appCompatImageViewLogo.setImageBitmap(bitmap);
            materialTextViewNombre.setText(cursoPersonalizado.getCurso().getNombre());
            materialTextViewObjetivo.setText(cursoPersonalizado.getCurso().getObjetivo());
            materialTextViewDirigido.setText(cursoPersonalizado.getCurso().getDirigido());
            materialTextViewDictado.setText(cursoPersonalizado.getCurso().getDictado());
            materialTextViewContenido.setText(cursoPersonalizado.getCurso().getContenidos());
            materialTextViewFechaInicio.setText(cursoPersonalizado.getCurso().getFechaInicio());
            materialTextViewHorarios.setText(cursoPersonalizado.getCurso().getHorarios());
            materialTextViewDuracion.setText(cursoPersonalizado.getCurso().getDuracion());
            materialTextViewModalidad.setText(cursoPersonalizado.getCurso().getModalidad());
            materialTextViewCosto.setText(cursoPersonalizado.getCurso().getCostoCurso());
            materialButtonInscribirme.setOnClickListener(this);
            materialButtonContactarPorWhatsApp.setOnClickListener(this);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (cursoPersonalizado.isGuardado()) {
            menu.getItem(2).setIcon(ContextCompat.getDrawable(this, R.drawable.round_bookmark_remove_white_24dp));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final Cuenta cuenta = getAccount();

        if (cuenta == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Kytcla - Educación Contínua")
                    .setMessage("Se produjo un error al obtener los datos de la cuenta. \nVuelve a iniciar sesión.")
                    .setPositiveButton("Aceptar", (dialogInterface, i) -> {
                        finish();
                    })
                    .show();
            finish();
        } else {
            final MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_toolbar_curso, menu);

            if (!cuenta.getCorreoElectronico().equals("sterangav@kytcla.com") && !cuenta.getCorreoElectronico().equals("brynnerflores@outlook.com")) {
                final MenuItem menuItemCursoModificar = menu.findItem(R.id.menu_toolbar_curso_modificar);
                final MenuItem menuItemCursoEliminar = menu.findItem(R.id.menu_toolbar_curso_eliminar);

                menuItemCursoModificar.setEnabled(false);
                menuItemCursoModificar.setVisible(false);
                menuItemCursoEliminar.setEnabled(false);
                menuItemCursoEliminar.setVisible(false);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toolbar_curso_modificar:
                startActivity(new Intent(this, ActivityModificarCurso.class).putExtra("CURSO", cursoPersonalizado.getCurso()));
                return true;

            case R.id.menu_toolbar_curso_eliminar:
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Kytcla")
                        .setMessage("¿Eliminar el curso?")
                        .setNegativeButton("No", (dialogInterface, i) -> {

                        })
                        .setPositiveButton("Si", (dialogInterface, i) -> {
                            materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                            materialAlertDialogBuilder.setCancelable(false);
                            materialAlertDialogBuilder.setView(R.layout.progres_dialog);
                            alertDialog = materialAlertDialogBuilder.show();

                            presenterCurso = new PresenterCurso(this);
                            presenterCurso.setCallBackEliminarCurso(this);
                            presenterCurso.eliminar(cursoPersonalizado.getCurso());
                        })
                        .show();
                return true;

            case R.id.menu_toolbar_curso_guardar:
                materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                materialAlertDialogBuilder.setCancelable(false);
                materialAlertDialogBuilder.setView(R.layout.progres_dialog);
                alertDialog = materialAlertDialogBuilder.show();

                presenterCurso = new PresenterCurso(this);
                presenterCurso.setCallBackGuardarCurso(this);
                presenterCurso.setCallBackEliminarCursoGuardado(this);

                final Cuenta cuenta = getAccount();
                if (cuenta == null) {
                    Toast.makeText(this, "Se produjo un error al obtener la cuenta.", Toast.LENGTH_SHORT).show();
                } else if (cursoPersonalizado.isGuardado()) {
                    presenterCurso.eliminarCursoGuardado(cuenta, cursoPersonalizado.getCurso());
                } else {
                    presenterCurso.guardarCurso(cuenta, cursoPersonalizado.getCurso());
                }
                return true;

            default:return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.material_button_curso_inscribirme_al_curso:
                startActivity(new Intent(this, ActivityWebView.class).putExtra("URL", cursoPersonalizado.getCurso().getEnlaceInscripcion()));
                break;

            case R.id.material_button_curso_enviar_mensaje_por_whatsapp:
                startActivity(new Intent(this, ActivityWebView.class).putExtra("URL", "https://api.whatsapp.com/send?phone=59173561646&text=Hola,%20me%20interesa%20uno%20de%20sus%20cursos."));
                break;

            default:break;
        }
    }

    private Cuenta getAccount() {
        try {
            final SharedPreferences sharedPreferences = getSharedPreferences("kytcla", Context.MODE_PRIVATE);
            final int codigo_persona = sharedPreferences.getInt("codigo_persona", 0);
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

            if (codigo_persona <= 0 || nombre == null || apellido == null || fecha_nacimiento == null || sexo == null || estado_persona == false || codigo_cuenta <= 0 || correo_electronico == null || estado_cuenta == false) {
                return null;
            } else {
                final Persona persona = new Persona(codigo_persona, null, nombre, apellido, fecha_nacimiento, sexo, pais, ciudad, direccion_domicilio, presentacion, estado_persona);
                return new Cuenta(codigo_cuenta, persona, correo_electronico, null, estado_cuenta);
            }
        } catch (final Exception exception) {
            return null;
        }
    }

    // endregion

    // region CallBack

    @Override
    public void cursoGuardado(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void errorGuardarCurso(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoGuardarCurso(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void cursoEliminado(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void errorEliminarCurso(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoEliminarCurso(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void cursoGuardadoEliminado(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void errorEliminarCursoGuardado(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoEliminarCursoGuardado(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    // endregion
}
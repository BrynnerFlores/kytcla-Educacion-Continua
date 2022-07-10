package com.brynnerflores.kytcla.view.tienda.productos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.Persona;
import com.brynnerflores.kytcla.model.POJO.ProductoPersonalizado;
import com.brynnerflores.kytcla.presenter.productos.PresenterProducto;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.Base64;

public class ActivityProducto extends AppCompatActivity implements View.OnClickListener, PresenterProducto.CallBackEliminarProducto, PresenterProducto.CallBackGuardarProducto, PresenterProducto.CallBackEliminarProductoGuardado {

    // region Variables

    private MaterialToolbar materialToolbar;
    private ShapeableImageView shapeableImageViewFoto;
    private MaterialTextView materialTextViewNombre;
    private MaterialTextView materialTextViewCategoria;
    private MaterialTextView materialTextViewDescripcion;
    private MaterialTextView materialTextViewPrecio;
    private MaterialButton materialButtonContactarPorWhatsApp;

    private ProductoPersonalizado productoPersonalizado;
    
    private PresenterProducto presenterProducto;

    private AlertDialog alertDialog;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    // endregion

    // region Metodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        productoPersonalizado = (ProductoPersonalizado) getIntent().getSerializableExtra("PRODUCTO_PERSONALIZADO");

        if (productoPersonalizado == null) {
            Toast.makeText(this, "No se recibió el parámetro esperado.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            materialToolbar = findViewById(R.id.material_toolbar_activity_producto);
            materialToolbar.setTitle(productoPersonalizado.getProducto().getNombre());
            setSupportActionBar(materialToolbar);
            materialToolbar.setNavigationOnClickListener(view -> {
                finish();
            });

            shapeableImageViewFoto = findViewById(R.id.shapeable_image_view_producto_foto);
            materialTextViewNombre = findViewById(R.id.material_text_view_producto_nombre);
            materialTextViewCategoria = findViewById(R.id.material_text_view_producto_categoria);
            materialTextViewDescripcion = findViewById(R.id.material_text_view_producto_descripcion);
            materialTextViewPrecio = findViewById(R.id.material_text_view_producto_precio);
            materialButtonContactarPorWhatsApp = findViewById(R.id.material_button_producto_contactar_vendedor_por_whatsapp);

            final byte[] bytes = Base64.getDecoder().decode(productoPersonalizado.getProducto().getFotoProducto());
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            shapeableImageViewFoto.setImageBitmap(bitmap);
            materialTextViewNombre.setText(productoPersonalizado.getProducto().getNombre());
            materialTextViewCategoria.setText(productoPersonalizado.getProducto().getCategoriaProducto());
            materialTextViewDescripcion.setText(productoPersonalizado.getProducto().getDescripcion());
            materialTextViewPrecio.setText(productoPersonalizado.getProducto().getPrecio());

            materialButtonContactarPorWhatsApp.setOnClickListener(this);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (productoPersonalizado.isGuardado()) {
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

                    })
                    .show();
            finish();
        } else {
            final MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_toolbar_producto, menu);

            if (!cuenta.getCorreoElectronico().equals("sterangav@kytcla.com") && !cuenta.getCorreoElectronico().equals("brynnerflores@outlook.com")) {
                final MenuItem menuItemProductoModificar = menu.findItem(R.id.menu_toolbar_producto_modificar);
                final MenuItem menuItemProductoEliminar = menu.findItem(R.id.menu_toolbar_producto_eliminar);

                menuItemProductoModificar.setEnabled(false);
                menuItemProductoModificar.setVisible(false);
                menuItemProductoEliminar.setEnabled(false);
                menuItemProductoEliminar.setVisible(false);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toolbar_producto_modificar:
                startActivity(new Intent(this, ActivityModificarProducto.class).putExtra("PRODUCTO", productoPersonalizado.getProducto()));
                return true;

            case R.id.menu_toolbar_producto_eliminar:
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Kytcla")
                        .setMessage("¿Eliminar el producto?")
                        .setNegativeButton("No", (dialogInterface, i) -> {

                        })
                        .setPositiveButton("Si", (dialogInterface, i) -> {
                            materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                            materialAlertDialogBuilder.setCancelable(false);
                            materialAlertDialogBuilder.setView(R.layout.progres_dialog);
                            alertDialog = materialAlertDialogBuilder.show();

                            presenterProducto = new PresenterProducto(this);
                            presenterProducto.setCallBackEliminarProducto(this);
                            presenterProducto.eliminarProducto(productoPersonalizado.getProducto());
                        })
                        .show();
                return true;
                
            case R.id.menu_toolbar_producto_guardar:
                materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                materialAlertDialogBuilder.setCancelable(false);
                materialAlertDialogBuilder.setView(R.layout.progres_dialog);
                alertDialog = materialAlertDialogBuilder.show();

                presenterProducto = new PresenterProducto(this);

                final Cuenta cuenta = getAccount();
                if (cuenta == null) {
                    Toast.makeText(this, "Se produjo un error al obtener la cuenta.", Toast.LENGTH_SHORT).show();
                } else if (productoPersonalizado.isGuardado()) {
                    presenterProducto.setCallBackEliminarProductoGuardado(this);
                    presenterProducto.eliminarProductoGuardado(cuenta, productoPersonalizado.getProducto());
                } else {
                    presenterProducto.setCallBackGuardarProducto(this);
                    presenterProducto.guardarProducto(cuenta, productoPersonalizado.getProducto());
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.material_button_producto_contactar_vendedor_por_whatsapp:
                final boolean installed = whatsappIsInstalled();

                if (installed) {
                    final Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=59173561646&text=Hola,%20me%20interesa%20uno%20de%20sus%20productos."));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "WhatsApp no está instalado.", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
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

    private boolean whatsappIsInstalled() {
        final PackageManager packageManager = getPackageManager();

        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (final Exception exception) {
            return false;
        }
    }

    // endregion
    
    // region CallBack

    @Override
    public void productoEliminado(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void errorEliminarProducto(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoEliminarProducto(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void productoGuardado(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void errorGuardarProducto(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoGuardarProducto(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void productoGuardadoEliminado(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void errorEliminarProductoGuardado(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoEliminarProductoGuardado(final String msg) {
        alertDialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    // endregion
}
package com.brynnerflores.kytcla.view.tienda.productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.Producto;
import com.brynnerflores.kytcla.presenter.cursos.PresenterCurso;
import com.brynnerflores.kytcla.presenter.productos.PresenterProducto;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.Base64;

public class ActivityProducto extends AppCompatActivity implements View.OnClickListener, PresenterProducto.CallBackEliminarProducto, PresenterProducto.CallBackGuardarProducto {

    // region Variables

    private MaterialToolbar materialToolbar;
    private ShapeableImageView shapeableImageViewFoto;
    private MaterialTextView materialTextViewNombre;
    private MaterialTextView materialTextViewCategoria;
    private MaterialTextView materialTextViewDescripcion;
    private MaterialTextView materialTextViewPrecio;
    private MaterialButton materialButtonContactarPorWhatsApp;

    private Producto producto;
    
    private PresenterProducto presenterProducto;

    // endregion

    // region Metodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        producto = (Producto) getIntent().getSerializableExtra("PRODUCTO");

        if (producto == null) {
            Toast.makeText(this, "No se recibió el parámetro esperado.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            materialToolbar = findViewById(R.id.material_toolbar_activity_producto);
            materialToolbar.setTitle(producto.getNombre());
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

            final byte[] bytes = Base64.getDecoder().decode(producto.getFotoProducto());
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            shapeableImageViewFoto.setImageBitmap(bitmap);
            materialTextViewNombre.setText(producto.getNombre());
            materialTextViewCategoria.setText(producto.getCategoriaProducto());
            materialTextViewDescripcion.setText(producto.getDescripcion());
            materialTextViewPrecio.setText(producto.getPrecio());

            materialButtonContactarPorWhatsApp.setOnClickListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_producto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toolbar_producto_modificar:
                startActivity(new Intent(this, ActivityModificarProducto.class).putExtra("PRODUCTO", producto));
                return true;

            case R.id.menu_toolbar_producto_eliminar:
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Kytcla")
                        .setMessage("¿Eliminar el producto?")
                        .setNegativeButton("No", (dialogInterface, i) -> {

                        })
                        .setPositiveButton("Si", (dialogInterface, i) -> {
                            presenterProducto = new PresenterProducto(this);
                            presenterProducto.setCallBackEliminarProducto(this);
                            presenterProducto.eliminarProducto(producto);
                        })
                        .show();
                return true;
                
            case R.id.menu_toolbar_producto_guardar:
                presenterProducto = new PresenterProducto(this);
                presenterProducto.setCallBackGuardarProducto(this);

                final Cuenta cuenta = obtenerCuenta();
                if (cuenta == null) {
                    Toast.makeText(this, "Se produjo un error al obtener la cuenta.", Toast.LENGTH_SHORT).show();
                } else {
                    presenterProducto.guardarProducto(cuenta, producto);
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
                break;

            default:
                break;
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
    public void productoEliminado(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void errorEliminarProducto(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoEliminarProducto(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void productoGuardado(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void errorGuardarProducto(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoGuardarProducto(final String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    
    // endregion
}
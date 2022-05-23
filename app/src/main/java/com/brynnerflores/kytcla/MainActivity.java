package com.brynnerflores.kytcla;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    // region

    private NavController navController;
    private MaterialToolbar materialToolbar;
    private BottomNavigationView bottomNavigationView;

    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("kytcla", Context.MODE_PRIVATE);
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
            Toast.makeText(this, "Se produjo un error al cargar los datos del archivo de configuración", Toast.LENGTH_LONG).show();
            finish();
        } else {
            materialToolbar = findViewById(R.id.material_toolbar);
            setSupportActionBar(materialToolbar);

            navController = Navigation.findNavController(this, R.id.fragment_container_view);
            bottomNavigationView = findViewById(R.id.bottom_navigation_view);
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
            navController.addOnDestinationChangedListener(this);

            bottomNavigationView.getOrCreateBadge(R.id.nav_fragment_tienda).setNumber(10);
        }
    }

    @Override
    public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
        switch (navDestination.getId()) {
            case R.id.nav_fragment_inicio:
                materialToolbar.setTitle("Kytcla - Educación Continua");
                break;

            case R.id.nav_fragment_tienda:
                materialToolbar.setTitle("Tienda Virtual");
                break;

            case R.id.nav_fragment_guardado:
                materialToolbar.setTitle("Elementos Guardados");
                break;

            case R.id.nav_fragment_perfil:
                materialToolbar.setTitle("Tu Perfil");
                break;

            default:break;
        }
    }
}
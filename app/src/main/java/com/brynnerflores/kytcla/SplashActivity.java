package com.brynnerflores.kytcla;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.brynnerflores.kytcla.view.acceso.ActivityAcceso;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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
            startActivity(new Intent(this, ActivityAcceso.class));
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        finish();
    }
}
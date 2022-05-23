package com.brynnerflores.kytcla.view.acceso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.view.acceso.iniciar_sesion.ActivityIniciarSesion;
import com.brynnerflores.kytcla.view.acceso.registrarse.ActivityRegistrarse;
import com.google.android.material.button.MaterialButton;

public class ActivityAcceso extends AppCompatActivity implements View.OnClickListener {

    // region Variables

    private MaterialButton materialButtonIniciarSesion, materialButtonRegistrarse;

    // endregion

    // region Metodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);

        materialButtonIniciarSesion = findViewById(R.id.material_button_iniciar_sesion);
        materialButtonRegistrarse = findViewById(R.id.material_button_registrarse);

        materialButtonIniciarSesion.setOnClickListener(this);
        materialButtonRegistrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.material_button_iniciar_sesion:
                startActivity(new Intent(this, ActivityIniciarSesion.class));
                break;

            case R.id.material_button_registrarse:
                startActivity(new Intent(this, ActivityRegistrarse.class));
                break;

            default: break;
        }
    }

    // endregion
}
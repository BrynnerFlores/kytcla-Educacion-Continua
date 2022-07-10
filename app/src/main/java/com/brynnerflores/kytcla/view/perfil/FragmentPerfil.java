package com.brynnerflores.kytcla.view.perfil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.SplashActivity;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.Persona;
import com.brynnerflores.kytcla.view.cuenta.ActivityModificarPassword;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.Base64;

public class FragmentPerfil extends Fragment {

    // region Variables

    private ShapeableImageView shapeableImageViewFoto;
    private MaterialTextView materialTextViewNombreCompleto;
    private MaterialTextView materialTextViewPresentacion;
    private MaterialTextView materialTextViewCorreoElectronico;
    private MaterialTextView materialTextViewFechaNacimiento;
    private MaterialTextView materialTextViewSexo;
    private MaterialTextView materialTextViewPais;
    private MaterialTextView materialTextViewCiudad;
    private MaterialTextView materialTextViewDireccionDomicilio;

    private Cuenta cuenta;

    // endregion

    // region Constructor

    public FragmentPerfil() {
        // Required empty public constructor
    }

    // endregion

    // region Metodos

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cuenta = obtenerCuenta();

        if (cuenta == null) {
            Toast.makeText(getContext(), "Se produjo un error al obtener la cuenta.", Toast.LENGTH_SHORT).show();
        } else {
            shapeableImageViewFoto = view.findViewById(R.id.shapeable_image_view_perfil_foto);
            materialTextViewNombreCompleto = view.findViewById(R.id.material_text_view_perfil_nombre_completo);
            materialTextViewPresentacion = view.findViewById(R.id.material_text_view_perfil_presentacion);
            materialTextViewCorreoElectronico = view.findViewById(R.id.material_text_view_perfil_correo_electronico);
            materialTextViewFechaNacimiento = view.findViewById(R.id.material_text_view_perfil_fecha_nacimiento);
            materialTextViewSexo = view.findViewById(R.id.material_text_view_perfil_sexo);
            materialTextViewPais = view.findViewById(R.id.material_text_view_perfil_pais);
            materialTextViewCiudad = view.findViewById(R.id.material_text_view_perfil_ciudad);
            materialTextViewDireccionDomicilio = view.findViewById(R.id.material_text_view_perfil_direccion_domicilio);

            final byte[] bytes = Base64.getDecoder().decode(cuenta.getPersona().getFoto());
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            shapeableImageViewFoto.setImageBitmap(bitmap);
            materialTextViewNombreCompleto.setText(cuenta.getPersona().getNombre() + " " + cuenta.getPersona().getApellido());
            materialTextViewPresentacion.setText(cuenta.getPersona().getPresentacion());
            materialTextViewCorreoElectronico.setText(cuenta.getCorreoElectronico());
            materialTextViewFechaNacimiento.setText(cuenta.getPersona().getFechaNacimiento());
            materialTextViewSexo.setText(cuenta.getPersona().getSexo().equals("M") ? "Masculino" : "Femenino");
            materialTextViewPais.setText(cuenta.getPersona().getPais());
            materialTextViewCiudad.setText(cuenta.getPersona().getCiudad());
            materialTextViewDireccionDomicilio.setText(cuenta.getPersona().getDireccionDomicilio());
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar_perfil, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toolbar_perfil_modificar:
                startActivity(new Intent(getActivity(), ActivityModificarPerfil.class).putExtra("CUENTA", cuenta));
                return true;

            case R.id.menu_toolbar_perfil_modificar_password:
                startActivity(new Intent(getActivity(), ActivityModificarPassword.class).putExtra("CUENTA", cuenta));
                return true;

            case R.id.menu_toolbar_perfil_cerrar_sesion:
                getContext().getSharedPreferences("kytcla", Context.MODE_PRIVATE).edit().clear().commit();
                getActivity().finish();
                startActivity(new Intent(getActivity(), SplashActivity.class));
                return true;

            default:return super.onOptionsItemSelected(item);
        }
    }

    private Cuenta obtenerCuenta() {
        try {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("kytcla", Context.MODE_PRIVATE);
            final int codigo_persona = sharedPreferences.getInt("codigo_persona", 0);
            final String foto = sharedPreferences.getString("foto", null);
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

            if (codigo_persona <= 0 || foto == null || nombre == null || apellido == null || fecha_nacimiento == null || sexo == null || estado_persona == false || codigo_cuenta <= 0 || correo_electronico == null || estado_cuenta == false) {
                return null;
            } else {
                final Persona persona = new Persona(codigo_persona, foto, nombre, apellido, fecha_nacimiento, sexo, pais, ciudad, direccion_domicilio, presentacion, true);
                return new Cuenta(codigo_cuenta, persona, correo_electronico, null, true);
            }
        } catch (final Exception exception) {
            Toast.makeText(getContext(), "Se produjo un error al obtener la cuenta.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    // endregion

}
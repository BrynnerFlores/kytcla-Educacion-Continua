package com.brynnerflores.kytcla.view.tienda;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.Persona;
import com.brynnerflores.kytcla.view.tienda.productos.ActivityNuevoProducto;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FragmentTienda extends Fragment {

    // region variables

    private TiendaViewPagerTwoAdapter tiendaViewPagerTwoAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    // endregion

    // region Constructor

    public FragmentTienda() {
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
        return inflater.inflate(R.layout.fragment_tienda, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = view.findViewById(R.id.view_pager_two_tienda_productos);
        tabLayout = view.findViewById(R.id.tab_layout_tienda_categorias);

        tiendaViewPagerTwoAdapter = new TiendaViewPagerTwoAdapter(this);
        viewPager2.setAdapter(tiendaViewPagerTwoAdapter);

        //new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(position == 0 ? "Todo" : "Hogar")).attach();
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText("Todo")).attach();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        final Cuenta cuenta = getAccount();

        if (cuenta == null) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Kytcla - Educación Contínua")
                    .setMessage("Se produjo un error al obtener los datos de la cuenta. \n Vuelve a iniciar sesión.")
                    .setPositiveButton("Aceptar", (dialogInterface, i) -> {

                    })
                    .show();
            getActivity().finish();
        } else if (!cuenta.getCorreoElectronico().equals("sterangav@kytcla.com") && !cuenta.getCorreoElectronico().equals("brynnerflores@outlook.com")) {
            inflater.inflate(R.menu.menu_toolbar_tienda, menu);
            final MenuItem menuItem = menu.findItem(R.id.menu_toolbar_tienda_nuevo_producto);
            menuItem.setEnabled(false);
            menuItem.setVisible(false);
        } else {
            inflater.inflate(R.menu.menu_toolbar_tienda, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toolbar_tienda_nuevo_producto:
                startActivity(new Intent(getActivity(), ActivityNuevoProducto.class));
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    private Cuenta getAccount() {
        try {
            final SharedPreferences sharedPreferences = getContext().getSharedPreferences("kytcla", Context.MODE_PRIVATE);
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
}
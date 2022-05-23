package com.brynnerflores.kytcla.view.tienda;

import android.content.Intent;
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

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(position == 0 ? "Todo" : position == 1 ? "Hogar" : position == 2 ? "Categoria 2" : "Categoria 3")).attach();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar_tienda, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toolbar_tienda_nuevo_producto:
                startActivity(new Intent(getActivity(), ActivityNuevoProducto.class));
                return true;

            case R.id.menu_toolbar_tienda_buscar_producto:
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    // endregion
}
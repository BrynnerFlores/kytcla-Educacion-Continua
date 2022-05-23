package com.brynnerflores.kytcla.view.tienda;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.brynnerflores.kytcla.view.tienda.categorias.general.FragmentCategoriaGeneral;
import com.brynnerflores.kytcla.view.tienda.categorias.FragmentCategoria2;
import com.brynnerflores.kytcla.view.tienda.categorias.FragmentCategoria3;
import com.brynnerflores.kytcla.view.tienda.categorias.FragmentCategoria4;

public class TiendaViewPagerTwoAdapter extends FragmentStateAdapter {

    public TiendaViewPagerTwoAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentCategoriaGeneral();

            case 1:
                return new FragmentCategoria2();

            case 2:
                return new FragmentCategoria3();

            case 3:
                return new FragmentCategoria4();

            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

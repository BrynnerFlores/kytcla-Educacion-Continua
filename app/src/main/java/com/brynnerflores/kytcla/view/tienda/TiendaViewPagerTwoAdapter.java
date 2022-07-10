package com.brynnerflores.kytcla.view.tienda;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.brynnerflores.kytcla.view.tienda.categorias.general.FragmentCategoriaGeneral;
import com.brynnerflores.kytcla.view.tienda.categorias.FragmentCategoriaHogar;

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

            /*
            case 1:
                return new FragmentCategoriaHogar();
             */

            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}

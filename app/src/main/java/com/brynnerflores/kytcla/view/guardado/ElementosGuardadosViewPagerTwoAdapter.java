package com.brynnerflores.kytcla.view.guardado;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.brynnerflores.kytcla.view.guardado.elementos_guardados.cursos_guardados.FragmentCursosGuardados;
import com.brynnerflores.kytcla.view.guardado.elementos_guardados.FragmentProductosGuardados;

public class ElementosGuardadosViewPagerTwoAdapter extends FragmentStateAdapter {

    public ElementosGuardadosViewPagerTwoAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentCursosGuardados();

            case 1:
                return new FragmentProductosGuardados();

            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

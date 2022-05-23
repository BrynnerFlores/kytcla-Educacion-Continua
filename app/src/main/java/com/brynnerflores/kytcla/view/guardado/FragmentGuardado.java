package com.brynnerflores.kytcla.view.guardado;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brynnerflores.kytcla.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FragmentGuardado extends Fragment {

    // region Variables

    private ElementosGuardadosViewPagerTwoAdapter elementosGuardadosViewPagerTwoAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    // endregion

    // region Constructor

    public FragmentGuardado() {
        // Required empty public constructor
    }

    // endregion

    // region Metodos

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guardado, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tab_layout_elementos_guardados);
        viewPager2 = view.findViewById(R.id.view_pager_two_elementos_guardados);

        elementosGuardadosViewPagerTwoAdapter = new ElementosGuardadosViewPagerTwoAdapter(this);
        viewPager2.setAdapter(elementosGuardadosViewPagerTwoAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(position == 0 ? "Cursos" : "Productos")).attach();

    }

    // endregion
}
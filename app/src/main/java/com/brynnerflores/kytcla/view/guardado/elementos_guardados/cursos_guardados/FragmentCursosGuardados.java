package com.brynnerflores.kytcla.view.guardado.elementos_guardados.cursos_guardados;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.Curso;
import com.brynnerflores.kytcla.model.POJO.CursoGuardado;
import com.brynnerflores.kytcla.model.POJO.CursoPersonalizado;
import com.brynnerflores.kytcla.presenter.cursos.PresenterCurso;
import com.brynnerflores.kytcla.view.curso.ActivityCurso;
import com.brynnerflores.kytcla.view.guardado.elementos_guardados.cursos_guardados.recyclerview.AdapterCursoGuardado;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class FragmentCursosGuardados extends Fragment implements View.OnClickListener, PresenterCurso.CallBackObtenerCursosGuardados, SwipeRefreshLayout.OnRefreshListener {

    // region Variables

    private Cuenta cuenta;

    private RecyclerView recyclerViewCursosGuardados;
    private AdapterCursoGuardado adapterCursoGuardado;
    private PresenterCurso presenterCurso;
    private ConstraintLayout constraintLayoutProgressBar;
    private ConstraintLayout constraintLayoutListaVacia;
    private SwipeRefreshLayout swipeRefreshLayoutCursosGuardados;
    private MaterialButton materialButtonVolverACargar;

    // endregion

    // region Constructor

    public FragmentCursosGuardados() {
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
        return inflater.inflate(R.layout.fragment_cursos_guardados, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            cuenta = obtenerCuenta();

            if (cuenta == null) {
                Toast.makeText(getContext(), "Se produjo un error al obtener la cuenta.", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            } else {
                constraintLayoutProgressBar = view.findViewById(R.id.constraint_layout_cursos_guardados_progress_bar);
                constraintLayoutListaVacia = view.findViewById(R.id.constraint_layout_cursos_guardados_lista_vacia);
                swipeRefreshLayoutCursosGuardados = view.findViewById(R.id.swipe_refresh_layout_cursos_guardados);
                recyclerViewCursosGuardados = view.findViewById(R.id.recycler_view_cursos_guardados);
                materialButtonVolverACargar = view.findViewById(R.id.material_button_cursos_guardados_volver_a_cargar);

                swipeRefreshLayoutCursosGuardados.setOnRefreshListener(this);
                materialButtonVolverACargar.setOnClickListener(this);

                presenterCurso = new PresenterCurso(getContext());
                presenterCurso.setCallBackObtenerCursosGuardados(this);
            }
        } catch (final Exception exception) {
            Toast.makeText(getContext(), "Se produjo un error, vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        constraintLayoutListaVacia.setEnabled(false);
        constraintLayoutListaVacia.setVisibility(View.GONE);
        swipeRefreshLayoutCursosGuardados.setEnabled(false);
        swipeRefreshLayoutCursosGuardados.setVisibility(View.GONE);
        constraintLayoutProgressBar.setEnabled(true);
        constraintLayoutProgressBar.setVisibility(View.VISIBLE);

        if (cuenta == null) {
            constraintLayoutListaVacia.setEnabled(false);
            constraintLayoutListaVacia.setVisibility(View.GONE);
            swipeRefreshLayoutCursosGuardados.setEnabled(false);
            swipeRefreshLayoutCursosGuardados.setVisibility(View.GONE);
            constraintLayoutProgressBar.setEnabled(false);
            constraintLayoutProgressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Se produjo un error al obtener la cuenta.", Toast.LENGTH_SHORT).show();
        } else {
            presenterCurso.obtenerCursosGuardados(cuenta);
        }
    }

    @Override
    public void onClick(final View view) {
        constraintLayoutListaVacia.setEnabled(false);
        constraintLayoutListaVacia.setVisibility(View.GONE);
        swipeRefreshLayoutCursosGuardados.setEnabled(false);
        swipeRefreshLayoutCursosGuardados.setVisibility(View.GONE);
        constraintLayoutProgressBar.setEnabled(true);
        constraintLayoutProgressBar.setVisibility(View.VISIBLE);

        switch (view.getId()) {
            case R.id.material_button_cursos_guardados_volver_a_cargar:
                if (cuenta == null) {
                    constraintLayoutListaVacia.setEnabled(false);
                    constraintLayoutListaVacia.setVisibility(View.GONE);
                    swipeRefreshLayoutCursosGuardados.setEnabled(false);
                    swipeRefreshLayoutCursosGuardados.setVisibility(View.GONE);
                    constraintLayoutProgressBar.setEnabled(false);
                    constraintLayoutProgressBar.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Se produjo un error al obtener la cuenta.", Toast.LENGTH_SHORT).show();
                } else {
                    presenterCurso.obtenerCursosGuardados(cuenta);
                }
                break;

            default:break;
        }
    }

    @Override
    public void onRefresh() {
        constraintLayoutListaVacia.setEnabled(false);
        constraintLayoutListaVacia.setVisibility(View.GONE);
        swipeRefreshLayoutCursosGuardados.setEnabled(false);
        swipeRefreshLayoutCursosGuardados.setVisibility(View.GONE);
        constraintLayoutProgressBar.setEnabled(true);
        constraintLayoutProgressBar.setVisibility(View.VISIBLE);

        if (cuenta == null) {
            Toast.makeText(getContext(), "Se produjo un error al obtener la cuenta.", Toast.LENGTH_SHORT).show();

            constraintLayoutListaVacia.setEnabled(false);
            constraintLayoutListaVacia.setVisibility(View.GONE);
            swipeRefreshLayoutCursosGuardados.setEnabled(false);
            swipeRefreshLayoutCursosGuardados.setVisibility(View.GONE);
            constraintLayoutProgressBar.setEnabled(false);
            constraintLayoutProgressBar.setVisibility(View.GONE);
        } else {
            presenterCurso.obtenerCursosGuardados(cuenta);
        }
    }

    private Cuenta obtenerCuenta() {
        try {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("kytcla", Context.MODE_PRIVATE);
            final int codigo_cuenta = sharedPreferences.getInt("codigo_cuenta", 0);
            return new Cuenta(codigo_cuenta, null,null, null, true);
        } catch (final Exception exception) {
            return null;
        }
    }

    // endregion

    // region CallBack

    @Override
    public void cursosGuardadosObtenidos(final ArrayList<CursoPersonalizado> cursosPersonalizados) {
        constraintLayoutListaVacia.setEnabled(false);
        constraintLayoutListaVacia.setVisibility(View.GONE);
        constraintLayoutProgressBar.setEnabled(false);
        constraintLayoutProgressBar.setVisibility(View.GONE);
        swipeRefreshLayoutCursosGuardados.setEnabled(true);
        swipeRefreshLayoutCursosGuardados.setVisibility(View.VISIBLE);

        adapterCursoGuardado = new AdapterCursoGuardado(cursosPersonalizados);
        recyclerViewCursosGuardados.setAdapter(adapterCursoGuardado);

        swipeRefreshLayoutCursosGuardados.setRefreshing(false);

        adapterCursoGuardado.setOnClickListener(view -> {
            final CursoPersonalizado cursoPersonalizado = adapterCursoGuardado.getCurso(recyclerViewCursosGuardados.getChildViewHolder(view).getLayoutPosition());
            startActivity(new Intent(getActivity(), ActivityCurso.class).putExtra("CURSO_PERSONALIZADO", cursoPersonalizado));
        });
    }

    @Override
    public void listaCursosGuardadosVacia(final String msg) {
        swipeRefreshLayoutCursosGuardados.setEnabled(false);
        swipeRefreshLayoutCursosGuardados.setVisibility(View.GONE);
        constraintLayoutProgressBar.setEnabled(false);
        constraintLayoutProgressBar.setVisibility(View.GONE);
        constraintLayoutListaVacia.setEnabled(true);
        constraintLayoutListaVacia.setVisibility(View.VISIBLE);
    }

    @Override
    public void errorObtenerCursosGuardados(final String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoObtenerCursosGuardados(final String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    // endregion
}
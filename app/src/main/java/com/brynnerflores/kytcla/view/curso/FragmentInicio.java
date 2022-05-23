package com.brynnerflores.kytcla.view.curso;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Curso;
import com.brynnerflores.kytcla.presenter.cursos.PresenterCurso;
import com.brynnerflores.kytcla.view.curso.recyclerview.AdapterCurso;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class FragmentInicio extends Fragment implements View.OnClickListener, PresenterCurso.CallBackObtenerCursos, SwipeRefreshLayout.OnRefreshListener {

    // region Variables

    private AdapterCurso adapterCurso;
    private RecyclerView recyclerViewCursos;
    private PresenterCurso presenterCurso;
    private ConstraintLayout constraintLayoutProgressBar;
    private ConstraintLayout constraintLayoutListaVacia;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MaterialButton materialButtonVolverACargar;

    // endregion

    // region Constructor

    public FragmentInicio() {
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
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        constraintLayoutProgressBar = view.findViewById(R.id.constraint_layout_cursos_progress_bar);
        constraintLayoutListaVacia = view.findViewById(R.id.constraint_layout_cursos_lista_vacia);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_cursos);
        recyclerViewCursos = view.findViewById(R.id.recycler_view_cursos);
        materialButtonVolverACargar = view.findViewById(R.id.material_button_cursos_volver_a_cargar);

        swipeRefreshLayout.setOnRefreshListener(this);
        materialButtonVolverACargar.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        constraintLayoutListaVacia.setEnabled(false);
        constraintLayoutListaVacia.setVisibility(View.GONE);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setVisibility(View.GONE);
        constraintLayoutProgressBar.setEnabled(true);
        constraintLayoutProgressBar.setVisibility(View.VISIBLE);

        presenterCurso = new PresenterCurso(getContext());
        presenterCurso.setCallBackObtenerCursos(this);
        presenterCurso.obtenerCursos();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar_inicio, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toolbar_inicio_agregar_nuevo_curso:
                startActivity(new Intent(getActivity(), ActivityNuevoCurso.class));
                return true;

            default:return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.material_button_cursos_volver_a_cargar:

                constraintLayoutListaVacia.setEnabled(false);
                constraintLayoutListaVacia.setVisibility(View.GONE);
                swipeRefreshLayout.setEnabled(false);
                swipeRefreshLayout.setVisibility(View.GONE);
                constraintLayoutProgressBar.setEnabled(true);
                constraintLayoutProgressBar.setVisibility(View.VISIBLE);

                presenterCurso.obtenerCursos();

                break;

            default:break;
        }
    }

    @Override
    public void onRefresh() {

        constraintLayoutListaVacia.setEnabled(false);
        constraintLayoutListaVacia.setVisibility(View.GONE);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setVisibility(View.GONE);
        constraintLayoutProgressBar.setEnabled(true);
        constraintLayoutProgressBar.setVisibility(View.VISIBLE);

        presenterCurso.obtenerCursos();
    }

    // endregion

    // region CallBack

    @Override
    public void cursosObtenidos(final ArrayList<Curso> cursos) {
        try {
            constraintLayoutListaVacia.setEnabled(false);
            constraintLayoutListaVacia.setVisibility(View.GONE);
            constraintLayoutProgressBar.setEnabled(false);
            constraintLayoutProgressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setEnabled(true);
            swipeRefreshLayout.setVisibility(View.VISIBLE);

            adapterCurso = new AdapterCurso(getContext(), cursos);
            recyclerViewCursos.setAdapter(adapterCurso);

            swipeRefreshLayout.setRefreshing(false);

            adapterCurso.setOnClickListener(v -> {
                final Curso curso = adapterCurso.getCurso(recyclerViewCursos.getChildViewHolder(v).getLayoutPosition());
                startActivity(new Intent(getActivity(), ActivityCurso.class).putExtra("CURSO", curso));
            });
        } catch (final Exception exception) {
            Toast.makeText(getContext(), "Se produjo un error desconocido, vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void listaCursosVacia(final String msg) {
        constraintLayoutProgressBar.setEnabled(false);
        constraintLayoutProgressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setVisibility(View.GONE);
        constraintLayoutListaVacia.setEnabled(true);
        constraintLayoutListaVacia.setVisibility(View.VISIBLE);
    }

    @Override
    public void errorObtenerCursos(final String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorDesconocidoObtenerCursos(final String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    // endregion
}
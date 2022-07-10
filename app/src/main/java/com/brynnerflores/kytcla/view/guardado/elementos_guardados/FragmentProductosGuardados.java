package com.brynnerflores.kytcla.view.guardado.elementos_guardados;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Cuenta;
import com.brynnerflores.kytcla.model.POJO.Producto;
import com.brynnerflores.kytcla.model.POJO.ProductoGuardado;
import com.brynnerflores.kytcla.model.POJO.ProductoPersonalizado;
import com.brynnerflores.kytcla.presenter.productos.PresenterProducto;
import com.brynnerflores.kytcla.view.tienda.productos.ActivityProducto;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class FragmentProductosGuardados extends Fragment implements View.OnClickListener, PresenterProducto.CallBackObtenerProductosGuardados, SwipeRefreshLayout.OnRefreshListener {

    // region Variables

    private Cuenta cuenta;

    private PresenterProducto presenterProducto;
    private AdapterProductoGuardado adapterProductoGuardado;
    private RecyclerView recyclerViewProductosGuardados;
    private ConstraintLayout constraintLayoutProgressBar;
    private ConstraintLayout constraintLayoutListaVacia;
    private SwipeRefreshLayout swipeRefreshLayoutProductosGuardados;
    private MaterialButton materialButtonVolverACargar;
    
    // endregion

    // region Constructor

    public FragmentProductosGuardados() {
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
        return inflater.inflate(R.layout.fragment_productos_guardados, container, false);
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
                recyclerViewProductosGuardados = view.findViewById(R.id.recycler_view_productos_guardados);
                constraintLayoutProgressBar = view.findViewById(R.id.constraint_layout_productos_guardados_progress_bar);
                constraintLayoutListaVacia = view.findViewById(R.id.constraint_layout_productos_guardados_lista_vacia);
                swipeRefreshLayoutProductosGuardados = view.findViewById(R.id.swipe_refresh_layout_productos_guardados);
                materialButtonVolverACargar = view.findViewById(R.id.material_button_productos_guardados_volver_a_cargar);

                swipeRefreshLayoutProductosGuardados.setOnRefreshListener(this);
                materialButtonVolverACargar.setOnClickListener(this);

                presenterProducto = new PresenterProducto(getContext());
                presenterProducto.setCallBackObtenerProductosGuardados(this);
            }
        } catch (final Exception exception) {
            Toast.makeText(getContext(), "Se produjo un error, vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        showProgress();

        try {
            cuenta = obtenerCuenta();

            if (cuenta == null) {
                showError();
                Toast.makeText(getContext(), "Se produjo un error al obtener la cuenta, vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            } else {
                presenterProducto.obtenerProductosGuardados(cuenta);
            }
        } catch (final Exception exception) {
            showError();
            Toast.makeText(getContext(), "Se produjo un error, vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.material_button_productos_guardados_volver_a_cargar:
                final Cuenta cuenta = obtenerCuenta();

                if (cuenta == null) {
                    Toast.makeText(getContext(), "Se produjo un error al obtener la cuenta, vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else {
                    presenterProducto = new PresenterProducto(getContext());
                    presenterProducto.setCallBackObtenerProductosGuardados(this);
                    presenterProducto.obtenerProductosGuardados(cuenta);
                }
                break;

            default:break;
        }
    }

    @Override
    public void onRefresh() {
        showProgress();

        cuenta = obtenerCuenta();

        if (cuenta == null) {
            showError();
            Toast.makeText(getContext(), "Se produjo un error al obtener la cuenta, vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } else {
            presenterProducto.obtenerProductosGuardados(cuenta);
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

    private void showProgress() {
        constraintLayoutListaVacia.setEnabled(false);
        constraintLayoutListaVacia.setVisibility(View.GONE);
        swipeRefreshLayoutProductosGuardados.setEnabled(false);
        swipeRefreshLayoutProductosGuardados.setVisibility(View.GONE);
        constraintLayoutProgressBar.setEnabled(true);
        constraintLayoutProgressBar.setVisibility(View.VISIBLE);
    }

    private void showLista() {
        constraintLayoutProgressBar.setEnabled(false);
        constraintLayoutProgressBar.setVisibility(View.GONE);
        constraintLayoutListaVacia.setEnabled(false);
        constraintLayoutListaVacia.setVisibility(View.GONE);
        swipeRefreshLayoutProductosGuardados.setEnabled(true);
        swipeRefreshLayoutProductosGuardados.setVisibility(View.VISIBLE);
    }

    private void showListaVacia() {
        swipeRefreshLayoutProductosGuardados.setEnabled(false);
        swipeRefreshLayoutProductosGuardados.setVisibility(View.GONE);
        constraintLayoutProgressBar.setEnabled(false);
        constraintLayoutProgressBar.setVisibility(View.GONE);
        constraintLayoutListaVacia.setEnabled(true);
        constraintLayoutListaVacia.setVisibility(View.VISIBLE);
    }

    private void showError() {
        swipeRefreshLayoutProductosGuardados.setEnabled(false);
        swipeRefreshLayoutProductosGuardados.setVisibility(View.GONE);
        constraintLayoutProgressBar.setEnabled(false);
        constraintLayoutProgressBar.setVisibility(View.GONE);
        constraintLayoutListaVacia.setEnabled(false);
        constraintLayoutListaVacia.setVisibility(View.GONE);
    }

    // endregion
    
    // region CallBack

    @Override
    public void productosGuardadosObtenidos(final ArrayList<ProductoGuardado> productosGuardados) {
        adapterProductoGuardado = new AdapterProductoGuardado(productosGuardados);
        recyclerViewProductosGuardados.setAdapter(adapterProductoGuardado);

        showLista();

        adapterProductoGuardado.setOnClickListener(view -> {
            final ProductoPersonalizado productoPersonalizado = adapterProductoGuardado.getProducto(recyclerViewProductosGuardados.getChildViewHolder(view).getLayoutPosition());
            startActivity(new Intent(getActivity(), ActivityProducto.class).putExtra("PRODUCTO_PERSONALIZADO", productoPersonalizado));
        });
    }

    @Override
    public void listaProductosGuardadosVacia(final String msg) {
        showListaVacia();

        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorObtenerProductosGuardados(final String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoObtenerProductosGuardados(final String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    // endregion
}
package com.brynnerflores.kytcla.view.tienda.categorias.general;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Producto;
import com.brynnerflores.kytcla.presenter.productos.PresenterProducto;
import com.brynnerflores.kytcla.view.tienda.categorias.general.recycler.AdapterTiendaProductosCategoriaGeneral;
import com.brynnerflores.kytcla.view.tienda.productos.ActivityProducto;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class FragmentCategoriaGeneral extends Fragment implements View.OnClickListener, PresenterProducto.CallBackObtenerProductos, SwipeRefreshLayout.OnRefreshListener {

    // region Variables

    private AdapterTiendaProductosCategoriaGeneral adapterTiendaProductosCategoriaGeneral;
    private RecyclerView recyclerViewProductos;
    private PresenterProducto presenterProducto;
    private ConstraintLayout constraintLayoutProgressBar;
    private ConstraintLayout constraintLayoutListaVacia;
    private SwipeRefreshLayout swipeRefreshLayoutProductos;
    private MaterialButton materialButtonVolverACargar;

    // endregion

    // region Constructor

    public FragmentCategoriaGeneral() {
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
        return inflater.inflate(R.layout.fragment_categoria_general, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        constraintLayoutProgressBar = view.findViewById(R.id.constraint_layout_tienda_productos_categoria_general_progress_bar);
        constraintLayoutListaVacia = view.findViewById(R.id.constraint_layout_tienda_productos_categoria_general_lista_vacia);
        swipeRefreshLayoutProductos = view.findViewById(R.id.swipe_refresh_layout_tienda_productos_categoria_general);
        recyclerViewProductos = view.findViewById(R.id.recycler_view_tienda_categoria_uno_productos);
        materialButtonVolverACargar = view.findViewById(R.id.material_button_productos_categoria_general_volver_a_cargar);

        swipeRefreshLayoutProductos.setOnRefreshListener(this);
        materialButtonVolverACargar.setOnClickListener(this);

        presenterProducto = new PresenterProducto(getContext());
        presenterProducto.setCallBackObtenerProductos(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        constraintLayoutListaVacia.setEnabled(false);
        constraintLayoutListaVacia.setVisibility(View.GONE);
        swipeRefreshLayoutProductos.setEnabled(false);
        swipeRefreshLayoutProductos.setVisibility(View.GONE);
        constraintLayoutProgressBar.setEnabled(true);
        constraintLayoutProgressBar.setVisibility(View.VISIBLE);

        presenterProducto.obtenerProductos("GENERAL");
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.material_button_productos_categoria_general_volver_a_cargar:
                constraintLayoutListaVacia.setEnabled(false);
                constraintLayoutListaVacia.setVisibility(View.GONE);
                swipeRefreshLayoutProductos.setEnabled(false);
                swipeRefreshLayoutProductos.setVisibility(View.GONE);
                constraintLayoutProgressBar.setEnabled(true);
                constraintLayoutProgressBar.setVisibility(View.VISIBLE);

                presenterProducto.obtenerProductos("GENERAL");
                break;

            default:break;
        }
    }

    @Override
    public void onRefresh() {
        constraintLayoutListaVacia.setEnabled(false);
        constraintLayoutListaVacia.setVisibility(View.GONE);
        swipeRefreshLayoutProductos.setEnabled(false);
        swipeRefreshLayoutProductos.setVisibility(View.GONE);
        constraintLayoutProgressBar.setEnabled(true);
        constraintLayoutProgressBar.setVisibility(View.VISIBLE);

        presenterProducto.obtenerProductos("GENERAL");
    }

    // endregion

    // region CallBackPresenter

    @Override
    public void productosObtenidos(final ArrayList<Producto> productos) {
        try {
            constraintLayoutListaVacia.setEnabled(false);
            constraintLayoutListaVacia.setVisibility(View.GONE);
            constraintLayoutProgressBar.setEnabled(false);
            constraintLayoutProgressBar.setVisibility(View.GONE);
            swipeRefreshLayoutProductos.setEnabled(true);
            swipeRefreshLayoutProductos.setVisibility(View.VISIBLE);

            adapterTiendaProductosCategoriaGeneral = new AdapterTiendaProductosCategoriaGeneral(getContext(), productos);

            recyclerViewProductos.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            recyclerViewProductos.setAdapter(adapterTiendaProductosCategoriaGeneral);

            swipeRefreshLayoutProductos.setRefreshing(false);

            adapterTiendaProductosCategoriaGeneral.setOnClickListener(v -> {
                final Producto producto = adapterTiendaProductosCategoriaGeneral.getProducto(recyclerViewProductos.getChildViewHolder(v).getLayoutPosition());
                startActivity(new Intent(getActivity(), ActivityProducto.class).putExtra("PRODUCTO", producto));
            });
        } catch (final Exception exception) {
            Toast.makeText(getContext(), "Se produjo un error desconocido, vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void listaProductosVacia(final String msg) {
        swipeRefreshLayoutProductos.setEnabled(false);
        swipeRefreshLayoutProductos.setVisibility(View.GONE);
        constraintLayoutProgressBar.setEnabled(false);
        constraintLayoutProgressBar.setVisibility(View.GONE);
        constraintLayoutListaVacia.setEnabled(true);
        constraintLayoutListaVacia.setVisibility(View.VISIBLE);
    }

    @Override
    public void errorObtenerProductos(final String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDesconocidoObtenerProductos(final String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    // endregion
}
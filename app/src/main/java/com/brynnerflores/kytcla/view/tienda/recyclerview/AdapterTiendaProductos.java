package com.brynnerflores.kytcla.view.tienda.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Producto;

import java.util.ArrayList;

public class AdapterTiendaProductos extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    // region Variables

    private Context context;
    private ArrayList<Producto> productos;
    private View.OnClickListener listener;

    // endregion

    // region Constructor

    public AdapterTiendaProductos(Context context, ArrayList<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }

    // endregion

    // region Metodos

    @Override
    public void onClick(final View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public void setOnClickListener(final View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    // endregion
}

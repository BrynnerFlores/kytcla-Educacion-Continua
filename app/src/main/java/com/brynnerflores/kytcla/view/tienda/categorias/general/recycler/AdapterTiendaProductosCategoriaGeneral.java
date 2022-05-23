package com.brynnerflores.kytcla.view.tienda.categorias.general.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Producto;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Base64;

public class AdapterTiendaProductosCategoriaGeneral  extends RecyclerView.Adapter<AdapterTiendaProductosCategoriaGeneral.ViewHolder> implements View.OnClickListener{

    // region Variables

    private Context context;
    private ArrayList<Producto> productos;
    private View.OnClickListener listener;

    // endregion

    // region Constructor

    public AdapterTiendaProductosCategoriaGeneral(Context context, ArrayList<Producto> productos) {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent,false);
        view.setOnClickListener(this);
        return new AdapterTiendaProductosCategoriaGeneral.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Producto producto = productos.get(position);
        final byte[] bytes = Base64.getDecoder().decode(producto.getFotoProducto());
        final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.shapeableImageViewFoto.setImageBitmap(bitmap);
        holder.materialTextViewNombre.setText(producto.getNombre());
        holder.materialTextViewCategoria.setText(producto.getCategoriaProducto());
        holder.materialTextViewPrecio.setText(producto.getPrecio());
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public void setOnClickListener(final View.OnClickListener listener) {
        this.listener = listener;
    }

    public Producto getProducto(final int index) {
        return productos.get(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ShapeableImageView shapeableImageViewFoto;
        private MaterialTextView materialTextViewNombre;
        private MaterialTextView materialTextViewCategoria;
        private MaterialTextView materialTextViewPrecio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shapeableImageViewFoto = itemView.findViewById(R.id.shapeable_image_view_item_producto_foto);
            materialTextViewNombre = itemView.findViewById(R.id.material_text_view_item_producto_nombre);
            materialTextViewCategoria = itemView.findViewById(R.id.material_text_view_item_producto_categoria);
            materialTextViewPrecio = itemView.findViewById(R.id.material_text_view_item_producto_precio);
        }
    }

    // endregion

}
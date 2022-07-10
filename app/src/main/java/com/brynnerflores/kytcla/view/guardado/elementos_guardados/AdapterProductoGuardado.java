package com.brynnerflores.kytcla.view.guardado.elementos_guardados;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brynnerflores.kytcla.R;
import com.brynnerflores.kytcla.model.POJO.Curso;
import com.brynnerflores.kytcla.model.POJO.Producto;
import com.brynnerflores.kytcla.model.POJO.ProductoGuardado;
import com.brynnerflores.kytcla.model.POJO.ProductoPersonalizado;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Base64;

public class AdapterProductoGuardado extends RecyclerView.Adapter<AdapterProductoGuardado.ViewHolder> implements View.OnClickListener {

    // region Variables

    private final ArrayList<ProductoGuardado> productosGuardados;
    private View.OnClickListener listener;

    // endregion

    // region Constructor

    public AdapterProductoGuardado(ArrayList<ProductoGuardado> productosGuardados) {
        this.productosGuardados = productosGuardados;
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
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_elemento, parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductoGuardado.ViewHolder holder, int position) {
        final Producto producto = productosGuardados.get(position).getProducto();
        final byte[] bytes = Base64.getDecoder().decode(producto.getFotoProducto());
        final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.shapeableImageViewImagen.setImageBitmap(bitmap);
        holder.materialTextViewTitulo.setText(producto.getNombre());
        holder.materialTextViewSubTitulo.setText(producto.getDescripcion());
        holder.materialTextViewTiempo.setText(producto.getCategoriaProducto());
    }

    @Override
    public int getItemCount() {
        return productosGuardados.size();
    }

    public ProductoPersonalizado getProducto(final int index) {
        final Producto producto = productosGuardados.get(index).getProducto();
        return new ProductoPersonalizado(producto, true);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ShapeableImageView shapeableImageViewImagen;
        private MaterialTextView materialTextViewTitulo;
        private MaterialTextView materialTextViewSubTitulo;
        private MaterialTextView materialTextViewTiempo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shapeableImageViewImagen = itemView.findViewById(R.id.shapeable_image_view_elemento_imagen);
            materialTextViewTitulo = itemView.findViewById(R.id.material_text_view_elemento_titulo);
            materialTextViewSubTitulo = itemView.findViewById(R.id.material_text_view_elemento_subtitulo);
            materialTextViewTiempo = itemView.findViewById(R.id.material_text_view_elemento_tiempo);
        }
    }

    // endregion
}

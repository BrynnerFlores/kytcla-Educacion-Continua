package com.brynnerflores.kytcla.view.guardado.elementos_guardados.cursos_guardados.recyclerview;

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
import com.brynnerflores.kytcla.model.POJO.CursoGuardado;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Base64;

public class AdapterCursoGuardado extends RecyclerView.Adapter<AdapterCursoGuardado.ViewHolder> implements View.OnClickListener {

    // region Variables

    private final ArrayList<CursoGuardado> cursosGuardados;
    private View.OnClickListener listener;

    // endregion

    // region Constructor

    public AdapterCursoGuardado(ArrayList<CursoGuardado> cursosGuardados) {
        this.cursosGuardados = cursosGuardados;
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
    public void onBindViewHolder(@NonNull AdapterCursoGuardado.ViewHolder holder, int position) {
        final Curso curso = cursosGuardados.get(position).getCurso();
        final byte[] bytes = Base64.getDecoder().decode(curso.getLogoCurso());
        final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.shapeableImageViewImagen.setImageBitmap(bitmap);
        holder.materialTextViewTitulo.setText(curso.getNombre());
        holder.materialTextViewSubTitulo.setText(curso.getObjetivo());
        holder.materialTextViewTiempo.setText(curso.getAreaCurso());
    }

    @Override
    public int getItemCount() {
        return cursosGuardados.size();
    }

    public Curso getCurso(final int index) {
        return cursosGuardados.get(index).getCurso();
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

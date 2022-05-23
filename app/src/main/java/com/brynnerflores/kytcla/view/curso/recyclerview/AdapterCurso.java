package com.brynnerflores.kytcla.view.curso.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brynnerflores.kytcla.R;

import java.util.ArrayList;
import java.util.Base64;

import com.brynnerflores.kytcla.model.POJO.Curso;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class AdapterCurso extends RecyclerView.Adapter<AdapterCurso.ViewHolder>  implements View.OnClickListener {

    // region Variables

    private final Context context;
    private ArrayList<Curso> cursos;
    private View.OnClickListener listener;

    // endregion

    // region Constructor

    public AdapterCurso(Context context, ArrayList<Curso> cursos) {
        this.context = context;
        this.cursos = cursos;
    }

    // endregion

    // region Metodos

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curso, parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Curso curso = cursos.get(position);
        final byte[] bytes = Base64.getDecoder().decode(curso.getLogoCurso());
        final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.shapeableImageViewLogo.setImageBitmap(bitmap);
        holder.materialTextViewNombre.setText(curso.getNombre());
        holder.materialTextViewArea.setText(curso.getAreaCurso());
        holder.materialTextViewObjetivo.setText(curso.getObjetivo());
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public Curso getCurso(final int index) {
        return cursos.get(index);
    }

    @Override
    public void onClick(final View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ShapeableImageView shapeableImageViewLogo;
        private MaterialTextView materialTextViewNombre;
        private MaterialTextView materialTextViewArea;
        private MaterialTextView materialTextViewObjetivo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shapeableImageViewLogo = itemView.findViewById(R.id.shapeable_img_view_item_nuevo_curso_logo);
            materialTextViewNombre = itemView.findViewById(R.id.mtrl_txtview_curso_nombre);
            materialTextViewArea = itemView.findViewById(R.id.mtrl_txtview_curso_area);
            materialTextViewObjetivo = itemView.findViewById(R.id.mtrl_txtview_curso_objetivo);
        }
    }

    // endregion

}
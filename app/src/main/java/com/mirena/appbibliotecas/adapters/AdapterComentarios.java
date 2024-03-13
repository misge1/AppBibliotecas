package com.mirena.appbibliotecas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mirena.appbibliotecas.R;
import com.mirena.appbibliotecas.objects.Comentario;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterComentarios extends RecyclerView.Adapter<AdapterComentarios.ViewHolder> {

    Context context;

    List<Comentario> comentarioList;

    public AdapterComentarios(Context c, List<Comentario> lista_comentarios){
        context = c;
        comentarioList = lista_comentarios;
    }

    @NonNull
    @Override
    public AdapterComentarios.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_comentario, parent, false);
        return new AdapterComentarios.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterComentarios.ViewHolder holder, int position) {
        TextView textViewNombre = holder.getTextViewNombre();
        TextView textViewComentario = holder.getTextViewComentario();
        TextView textViewFecha = holder.getTextViewFecha();

        Comentario comentario = comentarioList.get(position);

        textViewComentario.setText(comentario.getTexto());
        textViewNombre.setText(comentario.getUsuario());
        textViewFecha.setText(comentario.getFecha());
    }

    @Override
    public int getItemCount() {
        return comentarioList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView_nombre;
        private final TextView textView_comentario;
        private final TextView textView_fecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_comentario = (TextView) itemView.findViewById(R.id.comentarioLibro);
            textView_nombre = (TextView) itemView.findViewById(R.id.nombreUsuarioComentario);
            textView_fecha = (TextView) itemView.findViewById(R.id.fechaComentario);
            itemView.setTag(this);
        }

        public TextView getTextViewNombre(){return textView_nombre;}
        public TextView getTextViewComentario(){return textView_comentario;}
        public TextView getTextViewFecha(){return textView_fecha;}
    }
}

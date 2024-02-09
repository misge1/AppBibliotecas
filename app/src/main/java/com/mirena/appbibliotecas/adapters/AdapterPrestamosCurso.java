package com.mirena.appbibliotecas.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirena.appbibliotecas.R;
import com.mirena.appbibliotecas.objects.PrestamoUsuario;
import com.mirena.appbibliotecas.ui.prestamos.EnCurso.PrestamosEnCursoActivity;
import com.mirena.appbibliotecas.ui.prestamos.EnCurso.prestamoIndividual.PrestamoEnCursoActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPrestamosCurso extends RecyclerView.Adapter<AdapterPrestamosCurso.ViewHolder> {

    Context context;
    View.OnClickListener mOnItemClickListener;
    List<PrestamoUsuario> lista;

    public AdapterPrestamosCurso(Context c, List<PrestamoUsuario> lista_prestamos) {
        context = c;
        lista = lista_prestamos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_prestamo_encurso, parent, false);
        view.setOnClickListener(mOnItemClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView textViewTitulo = holder.getTextview_titulo();
        TextView textViewFechaComienza = holder.getTextView_fecha_comienzo();
        TextView textViewFechaDevolucion = holder.getTextView_fecha_devolucion();
        ImageView imagen = holder.getImagen();

        textViewTitulo.setText(lista.get(position).getTitulo());
        textViewFechaDevolucion.setText(lista.get(position).getFecha_devolucion());
        textViewFechaComienza.setText(lista.get(position).getFecha_recogida());

        Picasso.get()
                .load(lista.get(position).getImage())
                .into(imagen);
        PrestamoUsuario prestamoUsuario = lista.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrestamoEnCursoActivity.class);
                intent.putExtra("id", prestamoUsuario.getId());
                intent.putExtra("titulo", prestamoUsuario.getTitulo());
                intent.putExtra("autor", prestamoUsuario.getAutor());
                intent.putExtra("biblioteca", prestamoUsuario.getNombre());
                intent.putExtra("editorial", prestamoUsuario.getEditorial());
                intent.putExtra("isbn", prestamoUsuario.getIsbn());
                intent.putExtra("fechaFin", prestamoUsuario.getFecha_devolucion());
                intent.putExtra("fechaInicio", prestamoUsuario.getFecha_recogida());
                intent.putExtra("imagen", prestamoUsuario.getImage());
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textview_titulo;
        private final TextView textView_fecha_comienzo;
        private final TextView textView_fecha_devolucion;

        private final ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_titulo = itemView.findViewById(R.id.textview_titulo_prestamo_curso);
            textView_fecha_comienzo= itemView.findViewById(R.id.textview_fecha_prestamo_comienzo);
            textView_fecha_devolucion= itemView.findViewById(R.id.textview_fecha_devolucion);
            imagen = itemView.findViewById(R.id.imagePrestamoCurso);
        }

        public TextView getTextview_titulo() {
            return textview_titulo;
        }

        public TextView getTextView_fecha_comienzo() {
            return textView_fecha_comienzo;
        }

        public TextView getTextView_fecha_devolucion() {
            return textView_fecha_devolucion;
        }

        public ImageView getImagen() {
            return imagen;
        }

        public void setOnItemClickListener(View.OnClickListener itemClickListener) {
            mOnItemClickListener = itemClickListener;
        }
    }
}

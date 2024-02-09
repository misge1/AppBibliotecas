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
import com.mirena.appbibliotecas.ui.prestamos.ARecoger.prestamoIndividual.PrestamoRecogerActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPrestamosRecoger extends RecyclerView.Adapter<AdapterPrestamosRecoger.ViewHolder> {

    Context context;
    View.OnClickListener mOnItemClickListener;
    List<PrestamoUsuario> lista;

    public AdapterPrestamosRecoger(Context c, List<PrestamoUsuario> lista_prestamos){
        context = c;
        lista = lista_prestamos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_prestamo, parent, false);
        view.setOnClickListener(mOnItemClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView textViewTitulo = holder.getTextview_titulo();
        TextView textViewFechaPrestamo = holder.getTextView_fecha_prestamo();
        TextView textViewFechaRecogida = holder.getTextView_fecha_recogida();
        ImageView imagen = holder.getImagen();

        textViewTitulo.setText(lista.get(position).getTitulo());
        textViewFechaPrestamo.setText(lista.get(position).getFecha_prestamo());
        textViewFechaRecogida.setText(lista.get(position).getFecha_recogida());

        Picasso.get()
                .load(lista.get(position).getImage())
                .into(imagen);
        PrestamoUsuario prestamoUsuario = lista.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrestamoRecogerActivity.class);
                intent.putExtra("id", prestamoUsuario.getId());
                intent.putExtra("titulo", prestamoUsuario.getTitulo());
                intent.putExtra("autor", prestamoUsuario.getAutor());
                intent.putExtra("biblioteca", prestamoUsuario.getNombre());
                intent.putExtra("editorial", prestamoUsuario.getEditorial());
                intent.putExtra("isbn", prestamoUsuario.getIsbn());
                intent.putExtra("fechaRecogida", prestamoUsuario.getFecha_recogida());
                intent.putExtra("fechaInicio", prestamoUsuario.getFecha_prestamo());
                intent.putExtra("imagen", prestamoUsuario.getImage());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textview_titulo;
        private final TextView textView_fecha_prestamo;
        private final TextView textView_fecha_recogida;

        private final ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_titulo = itemView.findViewById(R.id.textview_titulo_prestamo);
            textView_fecha_prestamo = itemView.findViewById(R.id.textview_fecha_prestamo);
            textView_fecha_recogida = itemView.findViewById(R.id.textview_fecha_recogida);
            imagen = itemView.findViewById(R.id.imagePrestamoRecoger);
        }

        public TextView getTextview_titulo(){return textview_titulo;}
        public TextView getTextView_fecha_prestamo(){return  textView_fecha_prestamo;}
        public TextView getTextView_fecha_recogida(){return  textView_fecha_recogida;}

        public ImageView getImagen(){return imagen;}
        public void setOnItemClickListener(View.OnClickListener itemClickListener){
            mOnItemClickListener = itemClickListener;
        }
    }
}

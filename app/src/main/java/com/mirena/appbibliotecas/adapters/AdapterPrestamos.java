package com.mirena.appbibliotecas.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirena.appbibliotecas.R;
import com.mirena.appbibliotecas.SubgenerosActivity;
import com.mirena.appbibliotecas.objects.LibroPre;
import com.mirena.appbibliotecas.objects.PrestamoUsuario;
import com.mirena.appbibliotecas.ui.Libro.LibroActivity2;
import com.mirena.appbibliotecas.ui.prestamos.EnCurso.prestamoIndividual.PrestamoEnCursoActivity;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterPrestamos extends RecyclerView.Adapter<AdapterPrestamos.ViewHolder> {

    Context context;
    View.OnClickListener mOnItemClickListener;
    List<PrestamoUsuario> lista;

    public AdapterPrestamos(Context c, List<PrestamoUsuario> lista_prestamos){
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

        textViewTitulo.setText(lista.get(position).getTitulo());
        textViewFechaPrestamo.setText(lista.get(position).getFecha_prestamo());
        textViewFechaRecogida.setText(lista.get(position).getFecha_recogida());

        PrestamoUsuario prestamoUsuario = lista.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrestamoEnCursoActivity.class);
                intent.putExtra("id", prestamoUsuario.getId());
                intent.putExtra("titulo", prestamoUsuario.getTitulo());
                intent.putExtra("autor", prestamoUsuario.getNombre());
                intent.putExtra("fechaDevolucion", prestamoUsuario.getFecha_devolucion());
                intent.putExtra("fechaInicio", prestamoUsuario.getFecha_prestamo());
                context.startActivity(intent);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_titulo = itemView.findViewById(R.id.textview_titulo_prestamo);
            textView_fecha_prestamo = itemView.findViewById(R.id.textview_fecha_prestamo);
            textView_fecha_recogida = itemView.findViewById(R.id.textview_fecha_recogida);
        }

        public TextView getTextview_titulo(){return textview_titulo;}
        public TextView getTextView_fecha_prestamo(){return  textView_fecha_prestamo;}
        public TextView getTextView_fecha_recogida(){return  textView_fecha_recogida;}

        public void setOnItemClickListener(View.OnClickListener itemClickListener){
            mOnItemClickListener = itemClickListener;
        }
    }
}

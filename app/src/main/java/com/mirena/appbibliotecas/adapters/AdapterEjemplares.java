package com.mirena.appbibliotecas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirena.appbibliotecas.R;
import com.mirena.appbibliotecas.objects.Biblioteca;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterEjemplares extends RecyclerView.Adapter<AdapterEjemplares.ViewHolder> {
    Context context;
    View.OnClickListener myOnClickListener;
    List<Biblioteca> lista;

    public AdapterEjemplares(Context c, List<Biblioteca> lista_biblioteca){
        context = c;
        lista = lista_biblioteca;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_ejemplar, parent, false);
        view.setOnClickListener(myOnClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEjemplares.ViewHolder holder, int position) {

        TextView textViewBiblioteca = holder.getTextViewbiblioteca();
        TextView textViewCantidad = holder.getTextViewCantidad();

        textViewBiblioteca.setText(lista.get(position).getBiblioteca());


        holder.elegirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewbiblioteca;
        private final TextView textViewCantidad;
        private final Button elegirButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewbiblioteca = itemView.findViewById(R.id.textview_biblioteca);
            textViewCantidad = itemView.findViewById(R.id.textview_ejemplares);
            elegirButton = itemView.findViewById(R.id.boton_elegir);
        }

        public TextView getTextViewbiblioteca(){return textViewbiblioteca;}
        public TextView getTextViewCantidad(){return textViewCantidad;}
    }
}

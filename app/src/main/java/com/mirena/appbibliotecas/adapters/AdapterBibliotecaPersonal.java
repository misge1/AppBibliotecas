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
import com.mirena.appbibliotecas.objects.BibliotecaPersonal;
import com.mirena.appbibliotecas.ui.Géneros.GenerosActivity;
import com.mirena.appbibliotecas.ui.bibliotecaPersonal.BibliotecaPersonalActivity;

import java.util.List;

public class AdapterBibliotecaPersonal extends RecyclerView.Adapter<AdapterBibliotecaPersonal.ViewHolder> {
    Context context;

    List<BibliotecaPersonal> lista_bibliotecas;

    public AdapterBibliotecaPersonal(Context c, List<BibliotecaPersonal> lista){
        this.lista_bibliotecas = lista;
        this.context = c;
    }

    @NonNull
    @Override
    public AdapterBibliotecaPersonal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_library_profile, parent, false);
        return new AdapterBibliotecaPersonal.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBibliotecaPersonal.ViewHolder holder, int position) {
        TextView textViewTitulo = holder.getTextView_titulo();

        BibliotecaPersonal bibliotecaPersonal = lista_bibliotecas.get(position);
        textViewTitulo.setText(bibliotecaPersonal.getNombre());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BibliotecaPersonalActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  lista_bibliotecas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView textView_titulo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_titulo = (TextView) itemView.findViewById(R.id.nombre_biblioteca);
        }

        public TextView getTextView_titulo() {
            return textView_titulo;
        }
    }
}

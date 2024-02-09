package com.mirena.appbibliotecas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirena.appbibliotecas.R;
import com.mirena.appbibliotecas.objects.Biblioteca;
import com.mirena.appbibliotecas.objects.Subgeneros;

import java.util.ArrayList;
import java.util.List;

public class AdapterBibliosLibro extends RecyclerView.Adapter<AdapterBibliosLibro.ViewHolder> implements Filterable {

    Context context;
    View.OnClickListener mOnItemClickListener;
    private List<Biblioteca> lista;
    private List<Biblioteca> lista_filtered = new ArrayList<>();

    public AdapterBibliosLibro(Context c, List<Biblioteca> lista_bibliotecas){
        context = c;
        lista = lista_bibliotecas;
        this.lista_filtered = lista_bibliotecas;
    }
    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public AdapterBibliosLibro.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_biblioteca, parent, false);
        view.setOnClickListener(mOnItemClickListener);
        return new AdapterBibliosLibro.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBibliosLibro.ViewHolder holder, int position) {
        TextView textViewBiblioteca = holder.getTextViewBiblioteca();
        textViewBiblioteca.setText(lista_filtered.get(position).getNombre());
    }




    @Override
    public int getItemCount() {
        return lista_filtered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewBiblioteca;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBiblioteca = itemView.findViewById(R.id.nombreBibliotecaCard);


        }
        public TextView getTextViewBiblioteca() {return  textViewBiblioteca;}
    }
}

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
import com.mirena.appbibliotecas.objects.Subgeneros;

import java.util.ArrayList;
import java.util.List;

public class AdapterSubgenerosLibro extends RecyclerView.Adapter<AdapterSubgenerosLibro.ViewHolder> implements Filterable {

    Context context;
    View.OnClickListener mOnItemClickListener;
    private List<Subgeneros> lista;
    private List<Subgeneros> lista_filtered = new ArrayList<>();

    public AdapterSubgenerosLibro(Context c, List<Subgeneros> lista_subgeneros){
        context = c;
        lista = lista_subgeneros;
        this.lista_filtered = lista_subgeneros;
    }
    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_biblioteca, parent, false);
        view.setOnClickListener(mOnItemClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSubgenerosLibro.ViewHolder holder, int position) {
            TextView textViewSubgenero = holder.getTextViewSubgenero();
            textViewSubgenero.setText(lista_filtered.get(position).getSubgenero());
    }

    @Override
    public int getItemCount() {
        return lista_filtered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewSubgenero;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSubgenero = itemView.findViewById(R.id.nombreBibliotecaCard);


        }
        public TextView getTextViewSubgenero() {return  textViewSubgenero;}
    }
}

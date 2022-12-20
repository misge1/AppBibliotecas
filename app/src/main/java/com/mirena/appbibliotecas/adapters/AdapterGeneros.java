package com.mirena.appbibliotecas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirena.appbibliotecas.R;
import com.mirena.appbibliotecas.objects.Generos;

import java.util.List;

public class AdapterGeneros extends RecyclerView.Adapter<AdapterGeneros.ViewHolder> {

    Context context;
    View.OnClickListener mOnItemClickListener;
    List<Generos> lista;


    public AdapterGeneros(Context c, List<Generos> lista_generos){
        context=c;
        lista = lista_generos;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_genero, parent, false);
        view.setOnClickListener(mOnItemClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TextView textViewCategoria= holder.getTextview_genero();
        textViewCategoria.setText(lista.get(position).getGenero());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textview_genero;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textview_genero = (TextView) itemView.findViewById(R.id.textivew_genero_solo);
            itemView.setTag(this);
        }

        public TextView getTextview_genero(){return  textview_genero;}

    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }


}

package com.mirena.appbibliotecas.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirena.appbibliotecas.R;
import com.mirena.appbibliotecas.objects.Generos;
import com.mirena.appbibliotecas.ui.EleccionFiltros.EleccionFiltrosActivity;

import java.util.List;

public class AdapterFiltroGeneros extends RecyclerView.Adapter<AdapterFiltroGeneros.ViewHolder> {

    Context context;
    View.OnClickListener mOnItemClickListener;
    List<Generos> lista;


    public AdapterFiltroGeneros(Context c, List<Generos> lista_generos){
        context=c;
        lista = lista_generos;
    }
    @NonNull
    @Override
    public AdapterFiltroGeneros.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_filtro_generos, parent, false);
        view.setOnClickListener(mOnItemClickListener);
        return new AdapterFiltroGeneros.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFiltroGeneros.ViewHolder holder, int position) {

        TextView textViewCategoria= holder.getTextview_genero();
        textViewCategoria.setText(lista.get(position).getGenero());

        Generos generos = lista.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EleccionFiltrosActivity.class);
                intent.putExtra("id_genero", generos.getId_genero());
                intent.putExtra("comesfromgenders", 1);
                //intent.putExtra("comesfromlistafiltrada", 1);
                ((Activity)context).finish();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textview_genero;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textview_genero = (TextView) itemView.findViewById(R.id.textviewEleccionGenero);
            itemView.setTag(this);
        }

        public TextView getTextview_genero(){return  textview_genero;}

    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }

}

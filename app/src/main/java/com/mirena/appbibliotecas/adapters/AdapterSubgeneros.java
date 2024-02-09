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
import com.mirena.appbibliotecas.objects.Subgeneros;
import com.mirena.appbibliotecas.ui.Subgeneros.SubgenerosActivity;

import java.util.List;

public class AdapterSubgeneros extends RecyclerView.Adapter<AdapterSubgeneros.ViewHolder> {

    Context context;
    View.OnClickListener mOnItemClickListener;
    List<Subgeneros> lista;

    public AdapterSubgeneros(Context c, List<Subgeneros> lista_subgeneros){
        context = c;
        lista = lista_subgeneros;
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
        TextView textView = holder.getTextView();
        textView.setText(lista.get(position).getSubgenero());

        Subgeneros subgeneros = lista.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SubgenerosActivity.class);
                intent.putExtra("idSubgenero", subgeneros.getId());
                intent.putExtra("nombreSubgenero", subgeneros.getSubgenero());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textivew_genero_solo);
            itemView.setTag(this);
        }

        public TextView getTextView(){return textView;}
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }
}

package com.mirena.appbibliotecas.adapters;

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
import com.mirena.appbibliotecas.objects.BibliotecaPersonal;
import com.mirena.appbibliotecas.objects.LibroPre;
import com.mirena.appbibliotecas.ui.Libro.LibroActivity2;
import com.mirena.appbibliotecas.ui.bibliotecaPersonal.BibliotecaPersonalActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterLibrosBiblioPersonal extends RecyclerView.Adapter<AdapterLibrosBiblioPersonal.ViewHolder>{

    Context context;
    List<LibroPre> lista_libros;

    public AdapterLibrosBiblioPersonal(Context c,  List<LibroPre> l){
        context = c;
        lista_libros = l;
    }


    @NonNull
    @Override
    public AdapterLibrosBiblioPersonal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_librio_bibliopersonal, parent, false);
        return new AdapterLibrosBiblioPersonal.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLibrosBiblioPersonal.ViewHolder holder, int position) {

        TextView textView_titulo = holder.getTextView_titulo();
        ImageView foto_libro = holder.getFoto_libro();

        LibroPre libro = lista_libros.get(position);
        textView_titulo.setText(libro.getTitulo());
        Picasso.get()
                .load(libro.getImage())
                .into(foto_libro);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LibroActivity2.class);
                intent.putExtra("id", libro.getId());
                intent.putExtra("titulo", libro.getTitulo());
                intent.putExtra("autor", libro.getAuthor());
                intent.putExtra("descripcion", libro.getDescription());
                intent.putExtra("idioma", libro.getIdioma());
                intent.putExtra("isbn", libro.getIsbn_issn());
                intent.putExtra("editorial", libro.getEditorial());
                if(libro.getImage()!=null){
                    intent.putExtra("image", libro.getImage());
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista_libros.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView_titulo;
        private final ImageView foto_libro;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_titulo = (TextView) itemView.findViewById(R.id.nombre_libro_bibliopersonal);
            foto_libro = (ImageView) itemView.findViewById(R.id.imagen_libro_bibliopersnal);
        }

        public TextView getTextView_titulo() {
            return textView_titulo;
        }

        public ImageView getFoto_libro() {
            return foto_libro;
        }
    }
}

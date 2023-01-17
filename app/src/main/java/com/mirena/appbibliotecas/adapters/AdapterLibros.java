package com.mirena.appbibliotecas.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirena.appbibliotecas.LibroActivity2;
import com.mirena.appbibliotecas.R;
import com.mirena.appbibliotecas.objects.LibroPre;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterLibros  extends RecyclerView.Adapter<AdapterLibros.ViewHolder>{
    Context context;
    View.OnClickListener mOnItemClickListener;
    List<LibroPre> lista;

    public AdapterLibros(Context c, List<LibroPre> lista_libroPres){
        context = c;
        lista = lista_libroPres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_book, parent, false);
        view.setOnClickListener(mOnItemClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TextView textviewTitulo = holder.getTextview_titulo();
        TextView textViewAutor= holder.getTextView_autor();
        TextView textViewIsbn = holder.getTextView_isbn();
        Button buttonejemplares = holder.getButtonejemplares();

        textviewTitulo.setText(lista.get(position).getTitulo());
        textViewAutor.setText(lista.get(position).getAutor());
        textViewIsbn.setText("isbn: " + lista.get(position).getIsbn_issn());


        LibroPre libro = lista.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LibroActivity2.class);
                intent.putExtra("id", libro.getId());
                intent.putExtra("titulo", libro.getTitulo());
                intent.putExtra("autor", libro.getAutor());
                intent.putExtra("descripcion", libro.getDescription());
                intent.putExtra("idioma", libro.getIdioma());
                intent.putExtra("isbn", libro.getIsbn_issn());
                intent.putExtra("editorial", libro.getEditorial());
                context.startActivity(intent);
            }
        });

        buttonejemplares.setOnClickListener(new View.OnClickListener() {
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

        private final TextView textview_titulo;
        private  final TextView textView_autor;
        private  final TextView textView_isbn;
        private final Button buttonejemplares;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_titulo = (TextView)itemView.findViewById(R.id.textview_titulo);
            textView_autor = itemView.findViewById(R.id.textview_autor);
            textView_isbn = itemView.findViewById(R.id.textview_isbn);
            buttonejemplares = itemView.findViewById(R.id.button_ejemplares);

        }

        public TextView getTextView_autor() {
            return textView_autor;
        }

        public TextView getTextView_isbn() {
            return textView_isbn;
        }

        public TextView getTextview_titulo() {
            return textview_titulo;
        }


        public Button getButtonejemplares(){return buttonejemplares;}
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }

}

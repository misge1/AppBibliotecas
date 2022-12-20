package com.mirena.appbibliotecas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        TextView textViewEjemplares = holder.getTextView_ejemplares();

        textviewTitulo.setText(lista.get(position).getTitulo());
        textViewAutor.setText(lista.get(position).getAutor());
        textViewIsbn.setText("isbn: " + lista.get(position).getIsbn_issn());
        textViewEjemplares.setText("nº de ejemplares: " + String.valueOf(lista.get(position).getNum_ejemplares()));


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textview_titulo;
        private  final TextView textView_autor;
        private  final TextView textView_isbn;
        private  final TextView textView_ejemplares;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_titulo = (TextView)itemView.findViewById(R.id.textview_titulo);
            textView_autor = itemView.findViewById(R.id.textview_autor);
            textView_isbn = itemView.findViewById(R.id.textview_isbn);
            textView_ejemplares = itemView.findViewById(R.id.textview_ejemplares);
        }

        public TextView getTextView_autor() {
            return textView_autor;
        }

        public TextView getTextView_ejemplares() {
            return textView_ejemplares;
        }

        public TextView getTextView_isbn() {
            return textView_isbn;
        }

        public TextView getTextview_titulo() {
            return textview_titulo;
        }
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }

}

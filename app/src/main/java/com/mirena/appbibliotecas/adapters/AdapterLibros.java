package com.mirena.appbibliotecas.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirena.appbibliotecas.ui.Libro.LibroActivity2;
import com.mirena.appbibliotecas.R;
import com.mirena.appbibliotecas.objects.LibroPre;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterLibros  extends RecyclerView.Adapter<AdapterLibros.ViewHolder> implements Filterable {
    Context context;
    View.OnClickListener mOnItemClickListener;
    private List<LibroPre> lista;
    private List<LibroPre> lista_filtered = new ArrayList<>();

    public AdapterLibros(Context c, List<LibroPre> lista_libroPres){
        context = c;
        lista = lista_libroPres;
        this.lista_filtered = lista_libroPres;
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if (charString.isEmpty()){
                    lista_filtered = lista;
                } else {
                    ArrayList<LibroPre> filteredList = new ArrayList<>();
                    for (LibroPre libroPre: lista){
                        if (libroPre.getTitulo().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(libroPre);
                        }

                        lista_filtered = filteredList;
                    }

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = lista_filtered;
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                lista_filtered = (ArrayList<LibroPre>)results.values;
                notifyDataSetChanged();

            }
        };
    }

    public LibroPre getLibroByPosition(int posicion){
        return lista_filtered.get(posicion);
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

        textviewTitulo.setText(lista_filtered.get(position).getTitulo());
        textViewAutor.setText(lista_filtered.get(position).getAutor());
        textViewIsbn.setText("isbn: " + lista_filtered.get(position).getIsbn_issn());


        LibroPre libro = lista_filtered.get(position);

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


    }

    @Override
    public int getItemCount() {
        return lista_filtered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textview_titulo;
        private  final TextView textView_autor;
        private  final TextView textView_isbn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_titulo = (TextView)itemView.findViewById(R.id.textview_titulo);
            textView_autor = itemView.findViewById(R.id.textview_autor);
            textView_isbn = itemView.findViewById(R.id.textview_isbn);


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

    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }

}

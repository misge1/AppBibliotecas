package com.mirena.appbibliotecas.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.mirena.appbibliotecas.objects.ImageBD;
import com.mirena.appbibliotecas.retrofit.RetrofitInstance;
import com.mirena.appbibliotecas.ui.Libro.LibroActivity2;
import com.mirena.appbibliotecas.R;
import com.mirena.appbibliotecas.objects.LibroPre;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
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
        TextView textViewEjemplares = holder.getTextview_ejemplares();
        ImageView imageViewFoto = holder.getImageView_foto();
        MaterialButton botonPedir = holder.getBotonPedir();

        textviewTitulo.setText(lista_filtered.get(position).getTitulo());
        textViewAutor.setText(lista_filtered.get(position).getAuthor());
        textViewIsbn.setText(lista_filtered.get(position).getIdioma());

        if(lista_filtered.get(position).getNum_ejemplar().equals("0")){
            textViewEjemplares.setText("No hay ejemplares disponibles");
            botonPedir.setChecked(false);
        }else {
            textViewEjemplares.setText(lista_filtered.get(position).getNum_ejemplar() + " ejemplares disponibles");
        }

        if (lista_filtered.get(position).getImage() != null && !lista_filtered.get(position).getImage().isEmpty()){

            Picasso.get()
                    .load(lista_filtered.get(position).getImage())
                    .fit()
                    .error(R.mipmap.atenea_penguin)
                    .into(imageViewFoto);
        }

        LibroPre libro = lista_filtered.get(position);

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
        return lista_filtered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textview_titulo;
        private  final TextView textView_autor;
        private  final TextView textView_isbn;
        private final TextView textview_ejemplares;

        private final ImageView imageView_foto;

        private final MaterialButton botonPedir;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_titulo = (TextView)itemView.findViewById(R.id.textview_titulo);
            textView_autor = itemView.findViewById(R.id.textview_autor);
            textView_isbn = itemView.findViewById(R.id.textview_isbn);
            imageView_foto = itemView.findViewById(R.id.imageview_libro);
            textview_ejemplares = itemView.findViewById(R.id.textview_estadoLibro);
            botonPedir = itemView.findViewById(R.id.boton_pedir);

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

        public ImageView getImageView_foto(){return imageView_foto;}
        public TextView getTextview_ejemplares(){return textview_ejemplares;}
        public MaterialButton getBotonPedir(){return botonPedir;}

    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }


}

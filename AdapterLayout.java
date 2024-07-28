package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterLayout extends RecyclerView.Adapter {

    Context context;
    ArrayList<Book> books;

    public AdapterLayout(Context context, ArrayList<Book> arrayList) {

        this.context = context;
        books = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_adapter_layout, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Book book = books.get(position);
        Holder holder1 = (Holder)holder;
        holder1.author.setText(book.getAuthor());
        holder1.title.setText(book.getTitle());
        Picasso.with(context).load(book.getUrl()).into(holder1.cover);

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView cover;
        TextView title;
        TextView author;
        public Holder(@NonNull View itemView) {
            super(itemView);
            cover  = itemView.findViewById(R.id.adaptercover);
            title = itemView.findViewById(R.id.adaptetitle);
            author  = itemView.findViewById(R.id.adapterauthor);
        }
    }






}
package edu.umkc.mobile.assignment4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import edu.umkc.mobile.assignment4.Book;
import edu.umkc.mobile.assignment4.R;
import edu.umkc.mobile.assignment4.ViewHolder;

/**
 * Created by vinuthna on 17-11-2017.
 */

public class ListViewAdapter extends RecyclerView.Adapter<ViewHolder>  {

    List<Book> list = Collections.emptyList();
    Context context;

    public ListViewAdapter(List<Book> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listlayout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bookTitle.setText(list.get(position).getTitle());
        holder.bookSubTitle.setText(list.get(position).getSubtitle());
        holder.authors.setText(list.get(position).getAuthors());
        holder.publishedDate.setText(list.get(position).getPublishedDate());

        Picasso.with(holder.image.getContext()).load(list.get(position).getImage()).error(android.R.drawable.sym_contact_card).placeholder(android.R.drawable.sym_contact_card).into(holder.image);

        final String bookTitle = list.get(position).getTitle();
        final String bookSubTitle = list.get(position).getSubtitle();
        final String authors = list.get(position).getAuthors();
        final String publishedDate = list.get(position).getPublishedDate();
        final String image = list.get(position).getImage();


        holder.image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Toast.makeText(context, "Book Title:"+bookTitle, Toast.LENGTH_SHORT).show();

            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, Book bookdata) {
        list.add(position, bookdata);
        notifyItemInserted(position);
    }

    public void remove(Book bookdata) {
        int positionEle = list.indexOf(bookdata);
        list.remove(positionEle);
        notifyItemRemoved(positionEle);
    }


}

package edu.umkc.mobile.assignment4;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by vinuthna on 17-11-2017.
 */

public class ViewHolder extends RecyclerView.ViewHolder{

    public CardView cv;
    public TextView bookTitle;
    public TextView bookSubTitle;
    public TextView publishedDate;
    public TextView authors;
    public ImageView image;

    public ViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        bookTitle = (TextView) itemView.findViewById(R.id.bookTitle);
        bookSubTitle = (TextView) itemView.findViewById(R.id.bookSubTitle);
        publishedDate = (TextView) itemView.findViewById(R.id.publishedDate);
        authors = (TextView) itemView.findViewById(R.id.authors);
        image = (ImageView) itemView.findViewById(R.id.image);
    }

}

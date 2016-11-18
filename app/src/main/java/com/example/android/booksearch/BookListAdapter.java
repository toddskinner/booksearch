package com.example.android.booksearch;

/**
 * Created by toddskinner on 11/17/16.
 */

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by toddskinner on 11/4/16.
 */

public class BookListAdapter extends ArrayAdapter<Book> {

    private Context mCon;

    public BookListAdapter(Activity context, ArrayList<Book> books){
        super(context, 0, books);
        mCon = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book, parent, false);
        }

        final Book currentBook = getItem(position);

        ImageView bookCoverImageView = (ImageView) convertView.findViewById(R.id.book_cover);

        //found by googling and stackoverflow
        Picasso.with(mCon).load(currentBook.getBookCover()).into(bookCoverImageView);


        TextView locationOffsetTextView = (TextView) convertView.findViewById(R.id.locationOffset);
        String locationOffset = currentBook.getTitle();
        locationOffsetTextView.setText(locationOffset);

        TextView primaryLocationTextView = (TextView) convertView.findViewById(R.id.primaryLocation);
        String primaryLocation = currentBook.getAuthor();
        primaryLocationTextView.setText(primaryLocation);

        return convertView;
    }
}


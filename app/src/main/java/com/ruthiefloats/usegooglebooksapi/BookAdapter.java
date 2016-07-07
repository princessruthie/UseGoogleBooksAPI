package com.ruthiefloats.usegooglebooksapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ruthiefloats.usegooglebooksapi.model.Book;

import java.util.ArrayList;


public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_layout, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.authors);

        titleTextView.setText(currentBook.getTitle());
        authorTextView.setText(currentBook.getAuthor());

        return listItemView;
    }
}

package com.ruthiefloats.useguardianapi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruthiefloats.useguardianapi.model.Article;

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_layout, parent, false);
        }

        final Article currentArticle = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.authors);
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        titleTextView.setText(currentArticle.getWebTitle());
        authorTextView.setText(currentArticle.getContributorWebTitle());
        imageView.setImageBitmap(currentArticle.getBitmap());

        /*Set a click listener and have it open the user's browser */
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String uri = currentArticle.getWebUrl();
                intent.setData(Uri.parse(uri));
                getContext().startActivity(intent);
            }
        });

        return listItemView;
    }
}
package com.ruthiefloats.usegooglebooksapi.Parser;

import com.ruthiefloats.usegooglebooksapi.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fieldsru on 7/5/16.
 */
public class BookJSONParser {
    public static List<Book> parseFeed(String content) {
        /*  This currently doesn't do anything */
        //to implement parseFeed method

        try {
            JSONArray ar = new JSONArray(content);
            List<Book> bookList = new ArrayList<>();

            for (int i = 0; i<ar.length(); i++){
                JSONObject obj = ar.getJSONObject(i);
                Book book = new Book();
                //todo change these to sensible book variables
                book.setAuthor(obj.getString("productId"));
                book.setTitle(obj.getString("name"));

                bookList.add(book);
            }
            return bookList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

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
            JSONObject obj = new JSONObject(content);
            JSONArray ar = obj.getJSONArray("items");
            List<Book> bookList = new ArrayList<>();



            for (int i = 0; i<ar.length(); i++){
                JSONObject innerObj = ar.getJSONObject(i);
                JSONObject volumeInfo = innerObj.getJSONObject("volumeInfo");

                String authors = volumeInfo.optString("authors", "No Author Information");
                String title = volumeInfo.optString("title", "No Title Information");

                Book book = new Book();
                book.setAuthor(authors);
                book.setTitle(title);

                bookList.add(book);
            }
            return bookList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

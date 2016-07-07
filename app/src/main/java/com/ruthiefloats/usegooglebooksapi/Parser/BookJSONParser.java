package com.ruthiefloats.usegooglebooksapi.Parser;

import com.ruthiefloats.usegooglebooksapi.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookJSONParser {
    public static List<Book> parseFeed(String content) {

        try {
            /*
            Construct a Json object out of the passed-in String,
            get a Json array and initialize an empty List of Books.
             */
            JSONObject obj = new JSONObject(content);
            JSONArray ar = obj.getJSONArray("items");
            List<Book> bookList = new ArrayList<>();

            /*
            Iterate over the elements of the JSON array.
            The author and title info are actually inside the
            volumeInfo Json object.
             */
            for (int i = 0; i < ar.length(); i++) {
                JSONObject innerObj = ar.getJSONObject(i);
                JSONObject volumeInfo = innerObj.getJSONObject("volumeInfo");

                String authors = volumeInfo.optString("authors", "No Author Information");
                String title = volumeInfo.optString("title", "No Title Information");

                /*
                Create a new book and set the title and author info.
                Then add that book to the List.
                 */

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
